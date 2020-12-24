package commands.essentials;

import commands.Utilities;
import commands.essentials.challenge.Challenge;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class EssentialsListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();
        if (message.startsWith(Utilities.prefix + "help")) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("All Current Commands")
                    .setDescription("A List of all current commands and how to use them")
                    .addInlineField("Command", Utilities.prefix + "help\n" + Utilities.prefix + "zoom\n" + Utilities.prefix + "nextmeeting\n"
                            + Utilities.prefix + "funfact\n" + Utilities.prefix + "reactiontime\n" + Utilities.prefix + "randomnumber\n" + Utilities.prefix + "coinflip\n"
                            + Utilities.prefix + "attendance\n" + Utilities.prefix + "challenge\n" + Utilities.prefix + "ct")
                    .addInlineField("Use", "List of all Cmds\n" + "Zoom Info\n" + "Next Meeting\n" + "Random Fun Fact\n" + "Test Reaction Time\n" + "Random Number Generator\n"
                            + "Coinflip\n" + "Take Attendance\n" + "Challenge a user\n" + "Challenge a user to a tournament game")
                    .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar())
                    .setColor(Color.CYAN);
            event.getChannel().sendMessage(embed);
        } else if (message.startsWith(Utilities.prefix + "nextmeeting")) {
            MeetingReminder.getNextMeeting(event);
        } else if (message.startsWith(Utilities.prefix + "challenge")) {

            if (message.substring(Utilities.prefix.length() + "challenge".length()).isEmpty()) {
                Utilities.embedBuilder("Challenge", "To Challenge someone, input " + Utilities.prefix + "challenge @[user] or " + Utilities.prefix + "challenge @[user] [game link]", false, event);
            } else {
                List<User> challeneged = event.getMessage().getMentionedUsers();
                Optional<User> challenger = event.getMessageAuthor().asUser();

                List<String> strings = Arrays.asList(message.split(" "));

                if (strings.size() > 2) {
                    Challenge challenge = new Challenge(challenger.get(), challeneged, strings.get(2));
                    challenge.sendChallenge();
                } else {
                    Challenge challenge = new Challenge(challenger.get(), challeneged);
                    challenge.sendChallenge();
                }
            }
        } else if (message.startsWith(Utilities.prefix + "ct")) {

            System.out.println("works");
            if (message.substring(Utilities.prefix.length() + "ct".length()).isEmpty()) {
                Utilities.embedBuilder("Tourney Challenge", "To Challenge someone to a tourney game, input:\n " + Utilities.prefix + "ct @[user] [game link] [time of match]", false, event);
            } else {
                List<User> challeneged = event.getMessage().getMentionedUsers();
                Optional<User> challenger = event.getMessageAuthor().asUser();

                List<String> strings = Arrays.asList(message.split(" "));

                if (strings.size() > 2) {

                    StringBuilder x = new StringBuilder();
                    for (int i = 3; i < strings.size(); i++) {

                        if (i + 1 >= strings.size()) {
                            x.append(strings.get(i));
                        } else {
                            x.append(strings.get(i)).append(" ");
                        }
                    }

                    Challenge challenge = new Challenge(challenger.get(), challeneged, strings.get(2), x.toString(), true);
                    challenge.sendChallenge();
                }
            }
        }
    }
}
