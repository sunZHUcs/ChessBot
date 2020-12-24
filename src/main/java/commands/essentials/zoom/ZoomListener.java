package commands.essentials.zoom;

import commands.Utilities;
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
        if (message.startsWith(Utilities.prefix + "zoom")) {
            System.out.println("zoom");
            zoom.outputZoom(event);
        } else if (event.getMessageAuthor().isServerAdmin()) {
            if (message.startsWith(Utilities.prefix + "setzoom")) {
                focus = event.getMessageAuthor();
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Zoom Update Process")
                        .setDescription("Update/Set New Zoom Information. Set the ID, Password, and Link")
                        .addField("Information", "Complete all of the required commands below to create this tourney. To end this process, enter " + Utilities.prefix + "endzoom" +
                                "To see an updated status (resend this message with updated true/false on requirements), enter " + Utilities.prefix + "zoom\n")
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                        .addInlineField("Required Commands:", Utilities.prefix + "setid\n" + Utilities.prefix + "setpass\n"
                                + Utilities.prefix + "setlink")
                        .addInlineField("Status: ", zoom.getID() + "\n" + zoom.getPass() + "\n" + zoom.getLink())
                        .setColor(Color.orange)
                        .setFooter("Leigh Chess Bot", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png");
                event.getChannel().sendMessage(embed);
            } else if (message.startsWith(Utilities.prefix + "setid")) {
                if (event.getMessageAuthor().equals(focus)) {
                    String id = message.substring(Utilities.prefix.length() + "setid".length() + 1);
                    zoom.setID(id);
                    Utilities.embedBuilder("Zoom ID Updated To:" + id, "", true, event);
                } else {
                    noSuchZoom(event);
                }
            } else if (message.startsWith(Utilities.prefix + "setpass")) {
                if (event.getMessageAuthor().equals(focus)) {
                    String pass = message.substring(Utilities.prefix.length() + "setpass".length() + 1);
                    zoom.setPass(pass);
                    Utilities.embedBuilder("Zoom Password Updated To:" + pass, "", true, event);
                } else {
                    noSuchZoom(event);
                }
            } else if (message.startsWith(Utilities.prefix + "setlink")) {
                if (event.getMessageAuthor().equals(focus)) {
                    String zoomlink = message.substring(Utilities.prefix.length() + "setlink".length() + 1);
                    zoom.setLink(zoomlink);
                    Utilities.embedBuilder("Zoom Link Updated To: " + zoomlink, "", true, event);
                } else {
                    noSuchZoom(event);
                }
            } else if (message.startsWith(Utilities.prefix + "endzoom")) {
                focus = null;
                Utilities.embedBuilder("Zoom Update Process Cancelled", "", true, event);
            }
        }
    }

    public void noSuchZoom(MessageCreateEvent event) {
        Utilities.embedBuilder("You are not the focused user! Either another zoom update is in progress or you haven't started the update process by typing "
                + Utilities.prefix + "setzoom", "", true, event);
    }

}
