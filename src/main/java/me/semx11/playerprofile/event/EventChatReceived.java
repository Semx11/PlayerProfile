package me.semx11.playerprofile.event;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.semx11.playerprofile.PlayerProfile;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventChatReceived {

    private static final EventChatReceived INSTANCE = new EventChatReceived();
    private static final Pattern WAI_PATTERN = Pattern
            .compile("^You are currently (connected to server|in) (?<server>\\w+)$");

    public static EventChatReceived getInstance() {
        return INSTANCE;
    }

    private EventChatReceived() {
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {

        if (!PlayerProfile.onHypixel) {
            return;
        }

        String msg = event.message.getUnformattedText();

        Matcher m = WAI_PATTERN.matcher(msg);
        if (m.matches()) {
            event.setCanceled(true);
            PlayerProfile.inLobby = m.group("server").contains("lobby");
        }
    }

}
