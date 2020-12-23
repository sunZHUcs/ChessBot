package messages;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.IOException;

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
                }
            }

        }
        if (message.startsWith(prefix + "suckmydick")) {
            event.getChannel().sendMessage("slurp slurp daddy");
        } else if (message.startsWith(prefix + "funfact")) {
            try {
                FunFacts.outputFact(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}


