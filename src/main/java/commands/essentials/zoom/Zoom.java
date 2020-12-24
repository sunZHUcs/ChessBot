package commands.essentials.zoom;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class Zoom {


    public static int numofzooms = 0;

    private String ID;
    private String Pass;
    private String Link;

    public Zoom(String id, String pass, String link) {
        ID = id;
        Pass = pass;
        Link = link;
        numofzooms++;
    }

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        ID = id;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public void outputZoom(MessageCreateEvent event) {

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Zoom ID:")
                .setDescription(ID)
                .addField("Zoom Password", Pass)
                .addField("Zoom Link", Link)
                .setColor(Color.white);

        event.getChannel().sendMessage(embed);
    }
}
