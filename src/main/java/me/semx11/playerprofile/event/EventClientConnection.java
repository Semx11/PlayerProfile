package me.semx11.playerprofile.event;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.semx11.playerprofile.PlayerProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class EventClientConnection {

    private static final EventClientConnection INSTANCE = new EventClientConnection();

    private static final Pattern IP_PATTERN = Pattern
            .compile("(^([\\w-]+[.\\u2024])?hypixel[.\\u2024]net|209\\.222\\.115\\.\\d{1,3})");

    public static String lastIp;

    private EventClientConnection() {
    }

    public static EventClientConnection getInstance() {
        return INSTANCE;
    }

    @SubscribeEvent
    public void playerLoggedIn(ClientConnectedToServerEvent event) {
        lastIp = event.manager.getRemoteAddress().toString().toLowerCase();
        PlayerProfile.onHypixel = IP_PATTERN.matcher(lastIp).find();
    }

    @SubscribeEvent
    public void playerJoinedWorld(EntityJoinWorldEvent event) {

        if (!(event.entity instanceof EntityPlayerSP)) {
            return;
        }

        Minecraft.getMinecraft().thePlayer.sendChatMessage("/whereami");

    }

    @SubscribeEvent
    public void playerLoggedOut(ClientDisconnectionFromServerEvent event) {
        PlayerProfile.onHypixel = false;
    }

}