import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.UserStatus;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class EventListener implements MessageCreateListener {

    public static TextChannel adminchannel = null;

    public void onMessageCreate(MessageCreateEvent event) {
        //All Admin Only Commands
        if (event.getMessageAuthor().isServerAdmin()) {

            if (event.getMessageContent().contentEquals("!admin")) {
                adminchannel = event.getChannel();
            }
            if (event.getMessageContent().contains("!setstatus")) {
                String newstatus = event.getMessageContent().substring(11);

                System.out.println(newstatus);
                event.getApi().updateStatus(UserStatus.fromString(newstatus));
                event.getChannel().sendMessage(newstatus);
            }
        }


    }

}
