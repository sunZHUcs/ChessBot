package commands.games;

import commands.Utilities;
import commands.games.funfacts.FunFacts;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GamesListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();

        if (message.startsWith(Utilities.prefix + "funfact")) {
            try {
                FunFacts.outputFact(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (message.startsWith(Utilities.prefix + "randomnumber")) {

            if (message.substring(Utilities.prefix.length() + "randomnumber".length()).isEmpty()) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Random Number Generator")
                        .setDescription("To use this command, input " + Utilities.prefix + "randomnumber [lower bound] [upper bound]\n For example, " + Utilities.prefix + "randomnumber 1 5 would give you a random number between 1 and 5")
                        .addField("Warning!", "This only works with whole numbers.")
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar())
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
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.CYAN);
                event.getChannel().sendMessage(embed);
            }
        } else if (message.startsWith(Utilities.prefix + "coinflip")) {

            Random r = new Random();
            int random = r.nextInt(3 - 1) + 1;

            if (random == 1) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("You flipped heads!")
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.CYAN);
                event.getChannel().sendMessage(embed);
            } else {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("You flipped tails!")
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.CYAN);
                event.getChannel().sendMessage(embed);
            }
        }
    }
}
