package cf.sqlskid.motivation;

import net.runelite.api.AnimationID;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.PlayerDespawned;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@PluginDescriptor(
        name = "Motivation",
        description = "Get a little bit of motivation after death.",
        tags = {"motivation", "death", "die", "pvp"},
)
public class MotivationPlugin extends Plugin {

    private final String[] messages = new String[]{"Don't give up!","Never give up!","You can do it!", "Stay determined!", "Follow your dreams!", "Better luck next time!"};

    @Inject
    public Client client;

    @Inject
    public ChatMessageManager chatMessageManager;


    @Subscribe
    public void onAnimationChanged(AnimationChanged event){
        if(!(event.getActor() instanceof Player)) return;
        if(event.getActor().getHealthRatio() != 0) return;
        if(event.getActor().getAnimation() != AnimationID.DEATH) return;
        if(event.getActor() != client.getLocalPlayer()) return;

        int messageNumber = ThreadLocalRandom.current().nextInt(1, messages.length) - 1;

        sendChatMessage(messages[messageNumber]);
    }

    private void sendChatMessage(String chatMessage)
    {
        final String message = new ChatMessageBuilder()
                .append(ChatColorType.HIGHLIGHT)
                .append(chatMessage)
                .build();

        chatMessageManager.queue(
                QueuedMessage.builder()
                        .type(ChatMessageType.CONSOLE)
                        .runeLiteFormattedMessage(message)
                        .build());
    }
}
