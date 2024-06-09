package com.qien.ServerConfig;

import com.google.gson.JsonObject;

public class ConfigLoader {
    public static String storageName = "configs";
    public enum ConfigsKeys {
        EnableTestingFeatures("dosEnable");
        private final String text;
        ConfigsKeys(final String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
    }

    private static Storage.StorageObject ConfigsStorage;
    public static void registerStorage() {
        ConfigsStorage = Storage.addStorageInstance(storageName);
        initStorage();
    }
    private static void initStorage(){
        initData(ConfigsKeys.EnableTestingFeatures, "false");
        
    }
    public static void initData(ConfigsKeys key, String defaultValue){
        JsonObject data = ConfigsStorage.getKey("configs");
        if(!data.has(key.toString())){
            addData(key, defaultValue);
        }
    }
    public static void addData(ConfigsKeys key, String value) {
        JsonObject data = ConfigsStorage.getKey("configs");
        data.addProperty(key.toString(), value);
        ConfigsStorage.putKey("configs", data);
    }
    public static String getData(ConfigsKeys key){
        JsonObject data = ConfigsStorage.getKey("configs");
        if(data.has(key.toString())){
            return data.get(key.toString()).getAsString();
        }
        return null;
    }
}
