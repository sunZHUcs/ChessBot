package commands;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Utilities {

    public static String prefix = "!";

    public static void embedBuilder(String title, String description, Boolean admin, MessageCreateEvent event) {
        EmbedBuilder embed = new EmbedBuilder();

        if (description.equals("")) {
            embed.setTitle(title)
                    .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar());
        } else {
            embed.setTitle(title)
                    .setDescription(description)
                    .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar());
        }
        if (admin) {
            embed.setColor(Color.orange);
        } else {
            embed.setColor(Color.CYAN);
        }
        event.getChannel().sendMessage(embed);
    }

    public static void fileBuilder(File file) {
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}
