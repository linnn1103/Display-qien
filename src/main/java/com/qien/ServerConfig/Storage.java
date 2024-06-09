package com.qien.ServerConfig;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Storage {
    private static final HashMap<String, StorageObject> StorageMap = new HashMap<String, StorageObject>();
    public static final Logger LOGGER = LoggerFactory.getLogger("display-qien");
    public static StorageObject addStorageInstance(String storageName){
        StorageObject storage = new StorageObject(storageName);
        StorageMap.put(storageName, storage);
        return storage;
    }
    public static StorageObject getStorageInstance(String storageName) {
        if(!StorageMap.containsKey(storageName)) return null;
        return StorageMap.get(storageName);
    }

    public static class StorageObject {
        public final String storageName;
        private final File file;
        public HashMap<String, JsonObject> storage = new HashMap<>();
        private StorageObject(String storageName) {
            this.storageName = storageName;
            this.file = getStorageFile(storageName);
            this.storage = loadStorage(this.file);
        }
        public JsonObject getKey( String key ) {
            return storage.getOrDefault(key, new JsonObject());
        }
        public void putKey( String key, JsonObject data ) {
            storage.put(key, data);
            updateStorage(this.file, storage);
        }
        private Boolean createStorage(File file){
            try{
                file.getParentFile().mkdirs();
                Files.createFile( file.toPath() );
                PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
                writer.close();
                return true;
            }catch (IOException e){
                LOGGER.warn("Failed to create database!");
                LOGGER.trace(String.valueOf(e));
            }
            return false;
        }
        private void updateStorage(File file, HashMap<String, JsonObject> data) {
            if(obtainStorageExistence(file)){
                AtomicReference<String> outputStr = new AtomicReference<>("");
                data.forEach((key, value) -> {
                    outputStr.set(String.format("%s%s=%s\n", outputStr.toString(), key, value.toString()));;
                });
                try{
                    PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
                    writer.println(outputStr.get());
                    writer.close();
                }catch (IOException e){
                    LOGGER.warn("An error occurred while writing to the database!");
                }

            }
        }
        private Pair<String, JsonObject> parseConfigEntry(String entry, int line) {
            if( !entry.isEmpty() ) {
                String[] parts = entry.split("=");
                if(parts.length != 2) return null;
                return new Pair<>(parts[0], JsonParser.parseString(parts[1]).getAsJsonObject());
            }
            return null;
        }
        private File getStorageFile(String storageName){
            Path path = FabricLoader.getInstance().getConfigDir();
            return path.resolve( String.format("%s-%s.storage", "display-qien", storageName) ).toFile();
        }
        private Boolean obtainStorageExistence(File file) {
            if (!file.exists() || file.isDirectory()) {
                LOGGER.warn("Data Setting", file.getPath());
                return createStorage(file);
            }
            return true;
        }
        private HashMap<String, JsonObject> loadStorage (File file) {
            HashMap<String, JsonObject> data = new HashMap<String, JsonObject>();
            if(obtainStorageExistence(file)){
                LOGGER.info(file.toString());
                try(Scanner reader = new Scanner( file )) {
                    for( int line = 1; reader.hasNextLine(); line ++ ) {
                        Pair<String, JsonObject> res = parseConfigEntry( reader.nextLine(), line );
                        if(res != null){
                            data.put(res.getLeft(), res.getRight());
                        }
                    }
                }catch (FileNotFoundException e){
                    LOGGER.warn("Database file not found");
                    LOGGER.trace(String.valueOf(e));
                }
        
            }
            return data;
        }
    }
}
