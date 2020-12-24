package commands.essentials;

import commands.Utilities;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

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
                            + Utilities.prefix + "attendance")
                    .addInlineField("Use", "List of all Cmds\n" + "Zoom Info\n" + "Next Meeting\n" + "Random Fun Fact\n" + "Test Reaction Time\n" + "Random Number Generator\n"
                            + "Coinflip\n" + "Take Attendance")
                    .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                    .setColor(Color.CYAN);
            event.getChannel().sendMessage(embed);
        } else if (message.startsWith(Utilities.prefix + "nextmeeting")) {
            MeetingReminder.getNextMeeting(event);
        }
    }
}
