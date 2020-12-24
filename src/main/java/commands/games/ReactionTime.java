package commands.games;

import commands.Utilities;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class ReactionTime implements MessageCreateListener {

    MessageAuthor focus;
    long millis;
    long millis2;
    boolean started = true;


    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();
        if (!event.getMessageAuthor().isBotUser()) {

            if (message.startsWith(Utilities.prefix + "reactiontime")) {

                focus = event.getMessageAuthor();

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Test Your Reaction Time")
                        .setDescription("Type anything as fast as you can after the next message!")
                        .setColor(Color.CYAN)
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar());
                event.getChannel().sendMessage(embed);

                started = false;
                long time = (long) (Math.random() * 5000);
                long currentTime = System.currentTimeMillis();
                long targetTime = currentTime + time;

                while (currentTime < targetTime) {
                    currentTime = System.currentTimeMillis();
                }
                EmbedBuilder embed2 = new EmbedBuilder()
                        .setTitle("REACT")
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.red);
                event.getChannel().sendMessage(embed2);
                started = true;
                millis = System.currentTimeMillis();

            } else if (event.getMessageAuthor().equals(focus)) {

                if (started) {
                    millis2 = System.currentTimeMillis();
                    long z = millis2 - millis;
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Reaction Test Results")
                            .setDescription("Your reaction time was: " + z + " milliseconds.")
                            .setColor(Color.CYAN)
                            .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar());
                    event.getChannel().sendMessage(embed);
                } else {
                    event.getChannel().sendMessage("this works im just bad");
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Error!")
                            .setDescription("You typed before the reaction trigger! The test has been cancelled.")
                            .setColor(Color.CYAN)
                            .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar());
                    event.getChannel().sendMessage(embed);
                }
                focus = null;
                started = false;

            }
        }
    }
}