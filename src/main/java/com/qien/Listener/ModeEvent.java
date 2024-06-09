package com.qien.Listener;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import com.qien.ServerConfig.ConfigLoader;
public class ModeEvent {
    public static void registerEvents() {
        ConfigLoader.registerStorage();
        if("true".equals(ConfigLoader.getData(ConfigLoader.ConfigsKeys.EnableTestingFeatures))){
            ServerMessageEvents.CHAT_MESSAGE.register(EventListener::onChatMessage);
        }
    }
}
