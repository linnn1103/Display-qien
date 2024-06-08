package com.qien;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import static net.minecraft.server.command.CommandManager.*;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisplayQien implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("display-qien");
	private Text createItemText(ItemStack itemInHand, ServerPlayerEntity player, Rarity rarity) {
		MutableText baseText = itemInHand.hasCustomName() ? 
			Text.literal(itemInHand.getName().getString()) : 
			Text.translatable(itemInHand.getItem().getTranslationKey());
	
		MutableText itemText = Text.literal(player.getDisplayName().getString()).append(": [")
			.append(baseText.setStyle(Style.EMPTY.withColor(rarity.formatting)
				.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(itemInHand)))));
	
		if (itemInHand.getCount() > 1) {
			itemText = itemText.append(Text.literal("x" + itemInHand.getCount()));
		}
	

		return itemText.append("]");
	}

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("displayitem")
			.executes(context -> {
				ServerPlayerEntity player = context.getSource().getPlayer();
				ItemStack itemInHand = player.getMainHandStack();
				if (itemInHand.isEmpty()) {
					return 0; 
				}
				Rarity rarity = itemInHand.getItem().getRarity(itemInHand);
				MutableText itemText;
				itemText = (MutableText) createItemText(itemInHand, player, rarity);
				//發送物品資訊給玩家
				player.sendMessage(itemText,false);
				return 1;
			})));
		LOGGER.info("Display-Qien is on!");
	}
}