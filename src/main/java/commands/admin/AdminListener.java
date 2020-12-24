package commands.admin;

import commands.Utilities;
import commands.essentials.MeetingReminder;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.List;

public class AdminListener implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();
        if (event.getMessageAuthor().isServerAdmin()) {
            if (message.startsWith(Utilities.prefix + "setprefix")) {
                Utilities.prefix = message.substring(Utilities.prefix.length() + 10);
                event.getChannel().sendMessage("Prefix is now: " + Utilities.prefix);

            } else if (message.startsWith(Utilities.prefix + "setstatus")) {
                event.getApi().updateActivity(event.getMessageContent().substring(Utilities.prefix.length() + 10));
                event.getChannel().sendMessage("Status has been set to: " + event.getMessageContent().substring(Utilities.prefix.length() + 10));
            } else if (message.startsWith(Utilities.prefix + "sudospeak")) {
                event.getChannel().sendMessage(event.getMessageContent().substring(Utilities.prefix.length() + 10));
            } else if (message.startsWith(Utilities.prefix + "meetingremind")) {
                List<ServerTextChannel> channels = event.getMessage().getMentionedChannels();

                for (ServerTextChannel channel : channels) {
                    MeetingReminder.remindMeeting(channel);
                }
                Utilities.embedBuilder("Meeting Reminders Enabled", "", true, event);
            }
        }
    }
}
