package com.qien;

import static net.minecraft.server.command.CommandManager.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.qien.Listener.ModeEvent;

public class DisplayQien implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("display-qien");
	public static Text createItemText(ServerPlayerEntity player) {
		ItemStack itemInHand = player.getMainHandStack();
		Rarity rarity = itemInHand.getItem().getRarity(itemInHand);
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
	public static int sendItemText(ServerPlayerEntity player){
				ItemStack itemInHand = player.getMainHandStack();
				if (itemInHand.isEmpty()) {
					return 0; 
				}
				MutableText itemText;
				itemText = (MutableText) createItemText(player);
				// player.sendMessage(itemText,false);
				player.getServer().getPlayerManager().broadcast(itemText,false);
				return 1;
	}

	@Override
	public void onInitialize() {
		ModeEvent.registerEvents();
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("displayItem")
			.executes(context -> {
				sendItemText(context.getSource().getPlayer());
				return 1;
			})));
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("displayReload")
			.executes(context -> {
				ModeEvent.registerEvents();
				return 1;
			})));
		LOGGER.info("Display-Qien is on!");
	}

}