package Messages;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class EventListener implements MessageCreateListener {

    public static TextChannel adminchannel = null;
    public static String prefix = "!";
    boolean announceconfig = false;
    MessageAuthor focus = null;
    int x = 0;

    public void onMessageCreate(MessageCreateEvent event) {

        //Checking if message starts with bot prefix
        if (!event.getMessageAuthor().isBotUser()) {
            if (event.getMessageContent().startsWith(prefix)) {

                //All Admin Only Commands
                if (event.getMessageAuthor().isServerAdmin()) {

                    if (event.getMessageContent().contains("admin")) {
                        adminchannel = event.getChannel();
                    } else if (event.getMessageContent().contains("setprefix")) {
                        prefix = event.getMessageContent().substring(prefix.length() + 10);
                        event.getChannel().sendMessage("Prefix is now: " + prefix);

                    } else if (event.getMessageContent().contains("setstatus")) {
                        event.getApi().updateActivity(event.getMessageContent().substring(prefix.length() + 10));
                    } else if (event.getMessageContent().contains("sudospeak")) {
                        event.getChannel().sendMessage(event.getMessageContent().substring(prefix.length() + 10));
                    }
                }

            }
        }

    }
}


