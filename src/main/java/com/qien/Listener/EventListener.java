package com.qien.Listener;

import net.minecraft.item.ItemStack;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import com.qien.DisplayQien;

public class EventListener {
    public static void onChatMessage(SignedMessage message, ServerPlayerEntity sender, MessageType.Parameters params) {
        String rawMessage = message.getContent().getString();
        if (rawMessage.contains("[item]")) {
            ItemStack itemInHand = sender.getMainHandStack();
            if (!itemInHand.isEmpty()) {
                DisplayQien.sendItemText(sender);
            }
        }
    }
}
