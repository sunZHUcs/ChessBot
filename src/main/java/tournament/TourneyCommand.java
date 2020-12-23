package tournament;

import messages.EventListener;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.util.HashMap;
import java.util.List;


public class TourneyCommand implements MessageCreateListener {

    final HashMap<User, Integer> Players = new HashMap<>();
    public List<User> listofusers;
    MessageAuthor focus;
    boolean process = false;
    String date = "Not Set";
    String format = "Not Set";
    int brackets = 0;
    int time = 0;
    int players = 0;

    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();
        if (!event.getMessageAuthor().isBotUser()) {

            if (event.getMessageAuthor().isServerAdmin()) {

                if (message.startsWith(EventListener.prefix + "createtourney")) {
                    tourneyStatus(event);
                    process = true;
                    focus = event.getMessageAuthor();
                } else if (message.startsWith(EventListener.prefix + "setdate")) {

                    if (event.getMessageAuthor().equals(focus) && process) {
                        date = message.substring(EventListener.prefix.length() + "setdate".length() + 1);
                        event.getChannel().sendMessage("Date set to: " + date);
                    } else {
                        event.getChannel().sendMessage("No tourney is being created.");
                    }
                } else if (message.startsWith(EventListener.prefix + "setformat")) {
                    if (event.getMessageAuthor().equals(focus) && process) {
                        format = message.substring(EventListener.prefix.length() + "setformat".length() + 1);
                        event.getChannel().sendMessage("Format set to: " + format);
                    } else {
                        event.getChannel().sendMessage("No tourney is being created.");
                    }
                } else if (message.startsWith(EventListener.prefix + "setbrackets")) {
                    if (event.getMessageAuthor().equals(focus) && process) {
                        brackets = Integer.parseInt(message.substring(EventListener.prefix.length() + "setbrackets".length() + 1));
                        event.getChannel().sendMessage("Brackets set to: " + brackets);
                    } else {
                        event.getChannel().sendMessage("No tourney is being created.");
                    }
                } else if (message.startsWith(EventListener.prefix + "settime")) {
                    if (event.getMessageAuthor().equals(focus) && process) {
                        time = Integer.parseInt(message.substring(EventListener.prefix.length() + "settime".length() + 1));
                        event.getChannel().sendMessage("Time set to: " + time);
                    } else {
                        event.getChannel().sendMessage("No tourney is being created.");
                    }
                } else if (message.startsWith(EventListener.prefix + "addplayers")) {
                    if (event.getMessageAuthor().equals(focus) && process) {
                        players++;

                        int thisbracket = Integer.parseInt(message.substring(EventListener.prefix.length() + "addplayers".length() + 1, EventListener.prefix.length() + "addplayers".length() + 2));
                        listofusers = event.getMessage().getMentionedUsers();

                        for (User listofuser : listofusers) {
                            Players.put(listofuser, thisbracket);
                        }

                        event.getChannel().sendMessage(String.valueOf(Players));
                    } else {
                        event.getChannel().sendMessage("No tourney is being created.");
                    }
                } else if (message.startsWith(EventListener.prefix + "starttourney")) {
                    if (event.getMessageAuthor().equals(focus) && process) {
                        startTourney(event);
                    } else {
                        event.getChannel().sendMessage("No tourney is being created.");
                    }
                } else if (message.startsWith(EventListener.prefix + "tourneystatus")) {
                    if (event.getMessageAuthor().equals(focus) && process) {
                        tourneyStatus(event);
                    } else {
                        event.getChannel().sendMessage("No tourney is being created.");
                    }
                } else if (message.startsWith(EventListener.prefix + "canceltourney")) {

                    if (process) {
                        date = "Not Set";
                        format = "Not Set";
                        brackets = 0;
                        time = 0;
                        players = 0;
                        Players.clear();
                        event.getChannel().sendMessage("Tourney creation process cancelled.");
                    } else {
                        event.getChannel().sendMessage("No tourney is being created.");
                    }
                }
            }
        }
    }

    public void startTourney(MessageCreateEvent event) {

        if (!date.equals("Not Set") && !format.equals("Not Set") && brackets != 0 && time != 0 && players != 0) {
            Tourney tourney = new Tourney(date, format, brackets, time, Players);
            event.getMessage().getChannel().sendMessage("Tourney Successfully Created");
        } else {
            event.getMessage().getChannel().sendMessage("Not all required commands/parameters have been set!");
            tourneyStatus(event);
        }
    }

    public void tourneyStatus(MessageCreateEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Tourney Creation Process")
                .setDescription("Create a new tourney. Set the date, time format, number of brackets, time between games, and players")
                .addField("Information", "Complete all of the required commands below to create this tourney. To end this process, enter " + EventListener.prefix + "endtourney." +
                        "To see an updated status (resend this message with updated true/false on requirements), enter " + EventListener.prefix + "tourneystatus\n" + "When finished, enter " +
                        EventListener.prefix + "starttourney")
                .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                .addInlineField("Required Commands:", EventListener.prefix + "setdate\n" + EventListener.prefix + "setformat\n"
                        + EventListener.prefix + "setbrackets\n" + EventListener.prefix + "settime\n" + EventListener.prefix + "addplayers\n")
                .addInlineField("Status: ", date + "\n" + format + "\n" + brackets + "\n" + time + "\n" + players)
                .setColor(Color.orange)
                .setFooter("Leigh Chess Bot", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png");
        event.getChannel().sendMessage(embed);
    }
}
