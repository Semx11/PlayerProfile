package me.semx11.playerprofile;

import java.util.Arrays;
import me.semx11.playerprofile.event.EventChatReceived;
import me.semx11.playerprofile.event.EventClientConnection;
import me.semx11.playerprofile.event.EventEntityInteract;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = PlayerProfile.MODID, version = PlayerProfile.VERSION, acceptedMinecraftVersions = "[1.8, 1.8.9]")
public class PlayerProfile {

    public static final String MODID = "hypixelplayerprofile";
    public static final String VERSION = "1.0";

    public static boolean onHypixel;
    public static boolean inLobby;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        this.registerEvents(
                EventClientConnection.getInstance(),
                EventChatReceived.getInstance(),
                EventEntityInteract.getInstance()
        );
    }

    private void registerEvents(Object... events) {
        Arrays.asList(events).forEach((event) -> {
            MinecraftForge.EVENT_BUS.register(event);
            FMLCommonHandler.instance().bus().register(event);
        });
    }

}
