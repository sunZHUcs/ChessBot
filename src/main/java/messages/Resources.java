package messages;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Resources implements MessageCreateListener {

    MessageAuthor focus;
    int y = 0;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();
        if (message.startsWith(EventListener.prefix + "resources")) {
            try {
                Scanner scanner = new Scanner(new File("src/main/java/messages/resources/resources.txt"));

                String name = scanner.nextLine();
                String link = scanner.nextLine();
                String[] n = name.split(";");
                String[] l = link.split(";");

                StringBuilder firstinline = new StringBuilder();
                for (String value : n) {

                    if (firstinline.toString().equals("")) {
                        firstinline.append(value);
                    } else {
                        firstinline.append("\n").append(value);
                    }

                }
                StringBuilder secondinline = new StringBuilder();
                for (String s : l) {

                    if (secondinline.toString().equals("")) {
                        secondinline.append(s);
                    } else {
                        secondinline.append("\n").append(s);
                    }

                }

                EmbedBuilder infomsg = new EmbedBuilder()
                        .setTitle("Resources")
                        .setDescription("List of useful chess learning resources")
                        .addInlineField("Name", firstinline.toString())
                        .addInlineField("Link", secondinline.toString())
                        .setAuthor("Leigh Chess Club", "http://google.com/", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png")
                        .setColor(Color.CYAN);
                event.getChannel().sendMessage(infomsg);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (message.startsWith(EventListener.prefix + "addresource")) {
            EmbedBuilder infomsg = new EmbedBuilder()
                    .setTitle("Add Resource")
                    .setDescription("To add a resource, enter both " + EventListener.prefix + "resourcename and " + EventListener.prefix + "resourcelink")
                    .setAuthor("Leigh Chess Club", "http://google.com/", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png")
                    .setColor(Color.ORANGE);
            event.getChannel().sendMessage(infomsg);

            focus = event.getMessageAuthor();
        } else if (message.startsWith(EventListener.prefix + "resourcename")) {
            if (event.getMessageAuthor().equals(focus)) {
                String newname = message.substring(EventListener.prefix.length() + "resourcename".length() + 1);
                try {
                    Scanner scanner = new Scanner(new File("src/main/java/messages/resources/resources.txt"));

                    String name = scanner.nextLine();
                    String link = scanner.nextLine();

                    name = name + ";" + newname;

                    FileWriter myWriter = new FileWriter("src/main/java/messages/resources/resources.txt");
                    myWriter.write(name + "\n" + link);
                    myWriter.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("New Resource Link Added: " + newname)
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.orange);
                event.getChannel().sendMessage(embed);
            } else {
                notFocus(event);

            }

            y++;
            if (y == 2) {
                focus = null;
            }

        } else if (message.startsWith(EventListener.prefix + "resourcelink")) {
            if (event.getMessageAuthor().equals(focus)) {
                String newlink = message.substring(EventListener.prefix.length() + "resourcelink".length() + 1);
                try {
                    Scanner scanner = new Scanner(new File("src/main/java/messages/resources/resources.txt"));

                    String name = scanner.nextLine();
                    String link = scanner.nextLine();

                    link = link + ";" + newlink;

                    FileWriter myWriter = new FileWriter("src/main/java/messages/resources/resources.txt");
                    myWriter.write(name + "\n" + link);
                    myWriter.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("New Resource Link Added: " + newlink)
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.orange);
                event.getChannel().sendMessage(embed);
            } else {
                notFocus(event);
            }
            y++;
            if (y == 2) {
                focus = null;
            }
        }
    }

    public void notFocus(MessageCreateEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("You are not the focused user! Either another resource update is in progress or you haven't started the update process by typing " + EventListener.prefix + "addresource")
                .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                .setColor(Color.orange);
        event.getChannel().sendMessage(embed);
    }
}
