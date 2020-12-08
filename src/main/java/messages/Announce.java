package messages;

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
    public String author = "";
    public List<ServerTextChannel> listofchannels;
    public MessageAuthor focus = null;

    public void onMessageCreate(MessageCreateEvent event) {

        if (!event.getMessageAuthor().isBotUser() && event.getMessageAuthor().isServerAdmin()) {

            if (event.getMessageContent().startsWith(EventListener.prefix + "announce")) {
                if (focus != null) {
                    event.getChannel().sendMessage("Announcement already in process");
                } else {
                    focus = event.getMessageAuthor();

                    EmbedBuilder infomsg = new EmbedBuilder()
                            .setTitle("Announcement Setup")
                            .setDescription("This command creates a new Announcement with a specified title, description, author, and target channel. " +
                                    "\n\n The Focused User is " + focus)
                            .addField("Instructions", "The required commands are: \n`settitle`, `setdescription`, `setauthor`, and `setchannel`" +
                                    "\n Only the focused user can modify these fields. When all requirements have been met, enter `sendannouncement` to finish the announcement process.")
                            .setAuthor("Leigh Chess Club", "http://google.com/", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png")
                            .setColor(Color.CYAN);
                    event.getChannel().sendMessage(infomsg);
                    dostuff = false;
                }
            }

            if (event.getMessageAuthor().equals(focus) & !dostuff) {
                if (event.getMessageContent().startsWith(EventListener.prefix + "settitle")) {
                    title = event.getMessageContent().substring(EventListener.prefix.length() + 9);
                    event.getChannel().sendMessage("Title set to: " + title);
                } else if (event.getMessageContent().startsWith(EventListener.prefix + "setdescription")) {
                    description = event.getMessageContent().substring(EventListener.prefix.length() + 15);
                    event.getChannel().sendMessage("Description set to: " + description);
                } else if (event.getMessageContent().startsWith(EventListener.prefix + "setauthor")) {
                    author = event.getMessageContent().substring(EventListener.prefix.length() + 10);
                    event.getChannel().sendMessage("Author set to: " + author);
                } else if (event.getMessageContent().startsWith(EventListener.prefix + "setchannel")) {
                    listofchannels = event.getMessage().getMentionedChannels();
                    event.getChannel().sendMessage("Channel set to: " + listofchannels);
                } else if (event.getMessageContent().startsWith(EventListener.prefix + "sendannouncement")) {
                    event.getChannel().sendMessage("Sending announcement");

                    Icon icon = focus.getAvatar();
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle(title)
                            .setDescription(description)
                            .setAuthor(author, "http://google.com/", icon);
                    listofchannels.get(0).sendMessage(embed);

                    focus = null;
                } else if (event.getMessageContent().contains("end")) {
                    focus = null;
                    title = "";
                    description = "";
                    author = "";
                    dostuff = true;
                    event.getChannel().sendMessage("Announcement Cancelled");
                }
            }
        }
    }
}
