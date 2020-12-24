package commands.admin;

import commands.Utilities;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.util.List;

public class Announce implements MessageCreateListener {

    public boolean dostuff = true;
    public String title = "";
    public String description = "";
    public List<ServerTextChannel> listofchannels;
    public MessageAuthor focus = null;

    public void onMessageCreate(MessageCreateEvent event) {

        if (!event.getMessageAuthor().isBotUser() && event.getMessageAuthor().isServerAdmin()) {

            if (event.getMessageContent().startsWith(Utilities.prefix + "announce")) {
                if (focus != null) {
                    event.getChannel().sendMessage("Announcement already in process");
                } else {
                    focus = event.getMessageAuthor();

                    EmbedBuilder infomsg = new EmbedBuilder()
                            .setTitle("Announcement Setup")
                            .setDescription("This command creates a new Announcement with a specified title, description, and target channel. " +
                                    "\n\n The Focused User is " + focus.getDisplayName() + ", ID: " + focus.getIdAsString())
                            .addField("Instructions", "The required commands are: \n`settitle`, `setdescription`, and `setchannel`" +
                                    "\n Only the focused user can modify these fields. When all requirements have been met, enter `sendannouncement` to finish the announcement process.")
                            .setAuthor("Leigh Chess Club", "http://google.com/", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png")
                            .setColor(Color.ORANGE);
                    event.getChannel().sendMessage(infomsg);
                    dostuff = false;
                }
            }

            if (event.getMessageAuthor().equals(focus) & !dostuff) {
                if (event.getMessageContent().startsWith(Utilities.prefix + "settitle")) {
                    title = event.getMessageContent().substring(Utilities.prefix.length() + 9);
                    Utilities.embedBuilder("Title set to: " + title, "", true, event);
                } else if (event.getMessageContent().startsWith(Utilities.prefix + "setdescription")) {
                    description = event.getMessageContent().substring(Utilities.prefix.length() + 15);
                    Utilities.embedBuilder("Description set to: " + description, "", true, event);
                } else if (event.getMessageContent().startsWith(Utilities.prefix + "setchannel")) {
                    listofchannels = event.getMessage().getMentionedChannels();
                    Utilities.embedBuilder("Channel set to: " + listofchannels, "", true, event);
                } else if (event.getMessageContent().startsWith(Utilities.prefix + "sendannouncement")) {
                    Utilities.embedBuilder("Sending announcement", "", true, event);
                    Icon icon = focus.getAvatar();
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle(title)
                            .setDescription(description)
                            .setAuthor(focus.getDisplayName(), "http://google.com/", icon);
                    listofchannels.get(0).sendMessage(embed);

                    focus = null;
                } else if (event.getMessageContent().contains("end")) {
                    focus = null;
                    title = "";
                    description = "";
                    dostuff = true;
                    Utilities.embedBuilder("Announcement Cancelled", "", true, event);
                }
            }
        }
    }
}
