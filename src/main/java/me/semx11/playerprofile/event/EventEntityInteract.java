package me.semx11.playerprofile.event;

import me.semx11.playerprofile.PlayerProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventEntityInteract {

    private static final EventEntityInteract INSTANCE = new EventEntityInteract();

    public static EventEntityInteract getInstance() {
        return INSTANCE;
    }

    private EventEntityInteract() {
    }

    @SubscribeEvent
    public void onEntityRightClick(EntityInteractEvent event) {

        // Check if:
        // - you are on Hypixel;
        // - you are in a lobby;
        // - you are clicking on a player;
        // - you are sneaking
        if (!PlayerProfile.onHypixel
                || !PlayerProfile.inLobby
                || !(event.target instanceof EntityOtherPlayerMP)
                || !event.entityPlayer.isSneaking()) {
            return;
        }

        // Get clicked entity and cast it to an EntityOtherPlayerMP
        EntityOtherPlayerMP target = (EntityOtherPlayerMP) event.target;

        // Get UUID of clicked player
        String uuid = target.getUniqueID().toString();

        // Remove guild tag if present
        String displayName = target.getDisplayName().getFormattedText()
                .replaceAll("(\\s\u00A77\\[\\w+])?\u00A7r$", "");

        // Check if clicked player is an NPC (e.g. The Delivery Man)
        if (uuid.charAt(14) != '4') {
            return;
        }

        // Send message to player, and proper grammar ('s vs ')
        Minecraft.getMinecraft().thePlayer.addChatMessage(
                new ChatComponentText("\u00A7eYou are now viewing " + displayName + "\u00A7e"
                        + (displayName.endsWith("s") ? "'" : "'s") + " profile"));

        // Open Hypixel profile
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/viewprofile " + uuid);

    }

}
