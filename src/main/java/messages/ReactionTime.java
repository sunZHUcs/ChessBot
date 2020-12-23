package messages;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class ReactionTime implements MessageCreateListener {

    MessageAuthor focus;
    long millis;
    long millis2;
    boolean started = false;


    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();
        if (!event.getMessageAuthor().isBotUser()) {

            if (message.startsWith(EventListener.prefix + "reactiontime")) {

                focus = event.getMessageAuthor();

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Test Your Reaction Time")
                        .setDescription("Type anything as fast as you can after the next message!")
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar());
                event.getChannel().sendMessage(embed);

                if (!started) {
                    try {
                        Thread.sleep((long) (Math.random() * 5000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    EmbedBuilder embed2 = new EmbedBuilder()
                            .setTitle("REACT")
                            .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                            .setColor(Color.red);
                    event.getChannel().sendMessage(embed2);
                    started = true;
                    millis = System.currentTimeMillis();

                }
            } else if (event.getMessageAuthor().equals(focus)) {

                if (started) {
                    millis2 = System.currentTimeMillis();
                    int i = (int) millis2;
                    int z = (int) millis;
                    int x = i - z;
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Reaction Test Results")
                            .setDescription("Your reaction time was: " + x + "milliseconds.")
                            .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar());
                    event.getChannel().sendMessage(embed);
                } else {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Error!")
                            .setDescription("You typed before the reaction trigger! The test has been cancelled.")
                            .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar());
                    event.getChannel().sendMessage(embed);
                }
                focus = null;
            }
        }
    }
}
