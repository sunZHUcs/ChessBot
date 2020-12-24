package messages.zoom;

import messages.EventListener;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class ZoomListener implements MessageCreateListener {

    final Zoom zoom = new Zoom("976 6752 8895", "lhschess", "https://cuhsd.zoom.us/j/97667528895?pwd=TDFBeHBXMGZxbXMyanpJRlVIWDRaUT0");
    MessageAuthor focus;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();
        if (message.startsWith(EventListener.prefix + "zoom")) {
            System.out.println("zoom");
            zoom.outputZoom(event);
        } else if (event.getMessageAuthor().isServerAdmin()) {
            if (message.startsWith(EventListener.prefix + "setzoom")) {
                focus = event.getMessageAuthor();
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Zoom Update Process")
                        .setDescription("Update/Set New Zoom Information. Set the ID, Password, and Link")
                        .addField("Information", "Complete all of the required commands below to create this tourney. To end this process, enter " + EventListener.prefix + "endzoom" +
                                "To see an updated status (resend this message with updated true/false on requirements), enter " + EventListener.prefix + "zoom\n")
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                        .addInlineField("Required Commands:", EventListener.prefix + "setid\n" + EventListener.prefix + "setpass\n"
                                + EventListener.prefix + "setlink")
                        .addInlineField("Status: ", zoom.getID() + "\n" + zoom.getPass() + "\n" + zoom.getLink())
                        .setColor(Color.orange)
                        .setFooter("Leigh Chess Bot", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png");
                event.getChannel().sendMessage(embed);
            } else if (message.startsWith(EventListener.prefix + "setid")) {
                if (event.getMessageAuthor().equals(focus)) {
                    String id = message.substring(EventListener.prefix.length() + "setid".length() + 1);
                    zoom.setID(id);
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Zoom ID Updated To: " + id)
                            .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                            .setColor(Color.orange);
                    event.getChannel().sendMessage(embed);
                } else {
                    noSuchZoom(event);
                }
            } else if (message.startsWith(EventListener.prefix + "setpass")) {
                if (event.getMessageAuthor().equals(focus)) {
                    String pass = message.substring(EventListener.prefix.length() + "setpass".length() + 1);
                    zoom.setPass(pass);
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Zoom Password Updated To: " + pass)
                            .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                            .setColor(Color.orange);
                    event.getChannel().sendMessage(embed);
                } else {
                    noSuchZoom(event);
                }
            } else if (message.startsWith(EventListener.prefix + "setlink")) {
                if (event.getMessageAuthor().equals(focus)) {
                    String zoomlink = message.substring(EventListener.prefix.length() + "setlink".length() + 1);
                    zoom.setLink(zoomlink);
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Zoom Link Updated To: " + zoomlink)
                            .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                            .setColor(Color.orange);
                    event.getChannel().sendMessage(embed);
                } else {
                    noSuchZoom(event);
                }
            } else if (message.startsWith(EventListener.prefix + "endzoom")) {
                focus = null;
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Zoom Update Process Cancelled")
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.orange);
                event.getChannel().sendMessage(embed);
            }
        }
    }

    public void noSuchZoom(MessageCreateEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("You are not the focused user! Either another zoom update is in progress or you haven't started the update process by typing " + EventListener.prefix + "setzoom")
                .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                .setColor(Color.orange);
        event.getChannel().sendMessage(embed);
    }

}
