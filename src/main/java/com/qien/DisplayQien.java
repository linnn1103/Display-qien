package com.qien;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import static net.minecraft.server.command.CommandManager.*;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisplayQien implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("display-qien");

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("displayitem")
			.executes(context -> {
				// 獲取玩家實體
				ServerPlayerEntity player = context.getSource().getPlayer();
				// 獲取玩家主手上的物品
				ItemStack itemInHand = player.getMainHandStack();
				if (itemInHand.isEmpty()) {
					return 0; // 如果玩家手上沒有物品，直接返回
				}
				// 構建物品資訊字串
				Rarity rarity = itemInHand.getItem().getRarity(itemInHand);
				MutableText itemText;
				// 使用TranslationTextComponent來獲取本地化的物品名稱
				if (itemInHand.hasCustomName()) {
					if (itemInHand.getCount() > 1){
						itemText = Text.literal(player.getDisplayName().getString()).append(": [").append(Text.literal(itemInHand.getName().getString())
							.styled(style -> style
								.withColor(rarity.formatting) 
								.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(itemInHand))
						))).append("x").append(String.valueOf(itemInHand.getCount())).append("]");
					} else{
						itemText = Text.literal(player.getDisplayName().getString()).append(": [").append(Text.literal(itemInHand.getName().getString())
							.styled(style -> style
								.withColor(rarity.formatting) 
								.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(itemInHand))
							))).append("]");
					}
				}else {
					if (itemInHand.getCount() > 1){
						itemText = Text.literal(player.getDisplayName().getString()).append(": [").append(Text.translatable(itemInHand.getItem().getTranslationKey())
						.styled(style -> style
							.withColor(rarity.formatting) 
							.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(itemInHand))
						))).append("x").append(String.valueOf(itemInHand.getCount())).append("]");
					} else{
						itemText = Text.literal(player.getDisplayName().getString()).append(": [").append(Text.translatable(itemInHand.getItem().getTranslationKey())
						.styled(style -> style
							.withColor(rarity.formatting) 
							.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(itemInHand))
						))).append("]");
					}
				}
				//發送物品資訊給玩家
				player.sendMessage(itemText,false);
				return 1;
			})));
		LOGGER.info("Display-Qien is on!");
	}
}