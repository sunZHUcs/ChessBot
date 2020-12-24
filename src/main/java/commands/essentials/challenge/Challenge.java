package commands.essentials.challenge;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.util.logging.ExceptionLogger;

import java.awt.*;
import java.util.List;

public class Challenge {

    private String challengeLink = "";
    private final User challenger;
    private final List<User> challenged;
    private boolean tourney;
    private String time;


    public Challenge(User chal, List<User> chald) {
        challenged = chald;
        challenger = chal;
    }

    public Challenge(User chal, List<User> chald, String link) {
        challenged = chald;
        challenger = chal;
        challengeLink = link;
    }

    public Challenge(User chal, List<User> chald, String link, String t, boolean tourney) {
        challenged = chald;
        challenger = chal;
        challengeLink = link;
        time = t;
        this.tourney = tourney;
    }

    public void sendChallenge() {
        if (!tourney) {
            EmbedBuilder challenge = new EmbedBuilder()
                    .setTitle("Challenge")
                    .setDescription("You have been challenged by " + challenger.getName() + "!\nBe sure to message them accepting/denying this challenge!")
                    .setAuthor("Leigh Chess Club", "https://discord.gg/phqkRhfV5h", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png")
                    .setColor(Color.CYAN);

            if (!challengeLink.equals("")) {
                challenge.addField("Game Link:", challengeLink);
            }
            for (User x : challenged) {
                x.sendMessage(challenge).exceptionally(ExceptionLogger.get());

                EmbedBuilder confirmation = new EmbedBuilder()
                        .setTitle("Challenge")
                        .setDescription("Challenge sent to: " + x.getName())
                        .setAuthor("Leigh Chess Club", "https://discord.gg/phqkRhfV5h", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png")
                        .setColor(Color.CYAN);

                challenger.sendMessage(confirmation).exceptionally(ExceptionLogger.get());
            }

        } else {
            EmbedBuilder challenge = new EmbedBuilder()
                    .setTitle("Tourney Challenge")
                    .setDescription("You have been challenged by " + challenger.getName() + " to play at " + time + "!\nBe sure to message them accepting/denying this challenge!")
                    .addField("Game Link:", challengeLink)
                    .setAuthor("Leigh Chess Club", "https://discord.gg/phqkRhfV5h", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png")
                    .setColor(Color.CYAN);

            for (User x : challenged) {
                x.sendMessage(challenge).exceptionally(ExceptionLogger.get());

                EmbedBuilder confirmation = new EmbedBuilder()
                        .setTitle("Challenge")
                        .setDescription("Challenge sent to: " + x.getName())
                        .setAuthor("Leigh Chess Club", "https://discord.gg/phqkRhfV5h", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png")
                        .setColor(Color.CYAN);

                challenger.sendMessage(confirmation).exceptionally(ExceptionLogger.get());
            }
        }

    }
}
