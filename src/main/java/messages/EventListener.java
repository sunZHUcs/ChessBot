package messages;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EventListener implements MessageCreateListener {

    public static String prefix = "!";

    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();
        if (!event.getMessageAuthor().isBotUser()) {

            //All Admin Only Commands
            if (event.getMessageAuthor().isServerAdmin()) {
                if (message.startsWith(prefix + "setprefix")) {
                    prefix = message.substring(prefix.length() + 10);
                    event.getChannel().sendMessage("Prefix is now: " + prefix);

                } else if (message.startsWith(prefix + "setstatus")) {
                    event.getApi().updateActivity(event.getMessageContent().substring(prefix.length() + 10));
                    event.getChannel().sendMessage("Status has been set to: " + event.getMessageContent().substring(prefix.length() + 10));
                } else if (message.startsWith(prefix + "sudospeak")) {
                    event.getChannel().sendMessage(event.getMessageContent().substring(prefix.length() + 10));
                } else if (message.startsWith(prefix + "meetingremind")) {
                    List<ServerTextChannel> channels = event.getMessage().getMentionedChannels();

                    for (ServerTextChannel channel : channels) {
                        MeetingReminder.remindMeeting(channel);
                    }
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Meeting Reminders Enabled")
                            .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                            .setColor(Color.ORANGE);
                    event.getChannel().sendMessage(embed);
                }
            }

        }
        if (message.startsWith(prefix + "help")) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("All Current Commands")
                    .setDescription("A List of all current commands and how to use them")
                    .addInlineField("Command", EventListener.prefix + "help\n" + EventListener.prefix + "zoom\n" + EventListener.prefix + "nextmeeting\n"
                            + EventListener.prefix + "funfact\n" + EventListener.prefix + "reactiontime\n" + EventListener.prefix + "randomnumber\n" + EventListener.prefix + "coinflip\n"
                            + EventListener.prefix + "attendance")
                    .addInlineField("Use", "List of all Cmds\n" + "Zoom Info\n" + "Next Meeting\n" + "Random Fun Fact\n" + "Test Reaction Time\n" + "Random Number Generator\n"
                            + "Coinflip\n" + "Take Attendance")
                    .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                    .setColor(Color.CYAN);
            event.getChannel().sendMessage(embed);
        } else if (message.startsWith(prefix + "funfact")) {
            try {
                FunFacts.outputFact(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (message.startsWith(prefix + "nextmeeting")) {
            MeetingReminder.getNextMeeting(event);
        } else if (message.startsWith(prefix + "randomnumber")) {

            if (message.substring(prefix.length() + "randomnumber".length()).isEmpty()) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Random Number Generator")
                        .setDescription("To use this command, input " + prefix + "randomnumber [lower bound] [upper bound]\n For example, " + prefix + "randomnumber 1 5 would give you a random number between 1 and 5")
                        .addField("Warning!", "This only works with whole numbers.")
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.CYAN);
                event.getChannel().sendMessage(embed);
            } else {
                Random r = new Random();
                message = message.replaceAll("[^-?0-9]+", " ");
                List<String> numbers = Arrays.asList(message.trim().split(" "));

                int firstbound = Integer.parseInt(numbers.get(0));
                int secondbound = Integer.parseInt(numbers.get(1));
                int random = r.nextInt(secondbound - firstbound) + firstbound;

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Your random number is: " + random)
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.CYAN);
                event.getChannel().sendMessage(embed);
            }
        } else if (message.startsWith(prefix + "coinflip")) {

            Random r = new Random();
            int random = r.nextInt(3 - 1) + 1;

            if (random == 1) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("You flipped heads!")
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.CYAN);
                event.getChannel().sendMessage(embed);
            } else {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("You flipped tails!")
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.CYAN);
                event.getChannel().sendMessage(embed);
            }
        }
    }
}


