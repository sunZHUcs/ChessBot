package tournament;

import commands.Utilities;
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
    int numofdays = 0;

    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();
        if (!event.getMessageAuthor().isBotUser()) {

            if (event.getMessageAuthor().isServerAdmin()) {

                if (message.startsWith(Utilities.prefix + "createtourney")) {
                    tourneyStatus(event);
                    process = true;
                    focus = event.getMessageAuthor();
                } else if (message.startsWith(Utilities.prefix + "setdate")) {

                    if (event.getMessageAuthor().equals(focus) && process) {
                        date = message.substring(Utilities.prefix.length() + "setdate".length() + 1);
                        EmbedBuilder embed = new EmbedBuilder()
                                .setTitle("Date Set To: " + date)
                                .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar());
                        event.getChannel().sendMessage(embed);
                    } else {
                        noSuchTourney(event);
                    }
                } else if (message.startsWith(Utilities.prefix + "setformat")) {
                    if (event.getMessageAuthor().equals(focus) && process) {
                        format = message.substring(Utilities.prefix.length() + "setformat".length() + 1);
                        EmbedBuilder embed = new EmbedBuilder()
                                .setTitle("Format Set To: " + format)
                                .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar());
                        event.getChannel().sendMessage(embed);
                    } else {
                        noSuchTourney(event);
                    }
                } else if (message.startsWith(Utilities.prefix + "setbrackets")) {
                    if (event.getMessageAuthor().equals(focus) && process) {
                        brackets = Integer.parseInt(message.substring(Utilities.prefix.length() + "setbrackets".length() + 1));
                        EmbedBuilder embed = new EmbedBuilder()
                                .setTitle("Brackets Set To: " + brackets)
                                .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar());
                        event.getChannel().sendMessage(embed);
                    } else {
                        noSuchTourney(event);
                    }
                } else if (message.startsWith(Utilities.prefix + "settime")) {
                    if (event.getMessageAuthor().equals(focus) && process) {
                        time = Integer.parseInt(message.substring(Utilities.prefix.length() + "settime".length() + 1));
                        EmbedBuilder embed = new EmbedBuilder()
                                .setTitle("Time Set To: " + time)
                                .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar());
                        event.getChannel().sendMessage(embed);
                    } else {
                        noSuchTourney(event);
                    }
                } else if (message.startsWith(Utilities.prefix + "addplayers")) {
                    if (event.getMessageAuthor().equals(focus) && process) {
                        players++;

                        int thisbracket = Integer.parseInt(message.substring(Utilities.prefix.length() + "addplayers".length() + 1, Utilities.prefix.length() + "addplayers".length() + 2));
                        listofusers = event.getMessage().getMentionedUsers();

                        for (User listofuser : listofusers) {
                            Players.put(listofuser, thisbracket);
                        }

                        event.getChannel().sendMessage(String.valueOf(Players));
                    } else {
                        noSuchTourney(event);
                    }
                } else if (message.startsWith(Utilities.prefix + "numofdays")) {
                    if (event.getMessageAuthor().equals(focus) && process) {

                    } else {
                        noSuchTourney(event);
                    }
                } else if (message.startsWith(Utilities.prefix + "starttourney")) {
                    if (event.getMessageAuthor().equals(focus) && process) {
                        startTourney(event);
                    } else {
                        noSuchTourney(event);
                    }
                } else if (message.startsWith(Utilities.prefix + "tourneystatus")) {
                    if (event.getMessageAuthor().equals(focus) && process) {
                        tourneyStatus(event);
                    } else {
                        noSuchTourney(event);
                    }
                } else if (message.startsWith(Utilities.prefix + "canceltourney")) {

                    if (process) {
                        date = "Not Set";
                        format = "Not Set";
                        brackets = 0;
                        time = 0;
                        players = 0;
                        Players.clear();
                        EmbedBuilder embed = new EmbedBuilder()
                                .setTitle("Tourney Creation Process Cancelled!")
                                .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar());
                        event.getChannel().sendMessage(embed);
                    } else {
                        noSuchTourney(event);
                    }
                }
            }
        }
    }

    public void noSuchTourney(MessageCreateEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Error!")
                .setDescription("No Tourney is being created.")
                .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar());
        event.getChannel().sendMessage(embed);
    }

    public void startTourney(MessageCreateEvent event) {

        if (!date.equals("Not Set") && !format.equals("Not Set") && brackets != 0 && time != 0 && players != 0) {
            Tourney tourney = new Tourney(date, format, brackets, time, Players);
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Tourney successfully created (Started)")
                    .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar());
            event.getChannel().sendMessage(embed);
        } else {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Not all required commands/parameters have been set!")
                    .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar());
            event.getChannel().sendMessage(embed);
            tourneyStatus(event);
        }
    }

    public void tourneyStatus(MessageCreateEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Tourney Creation Process")
                .setDescription("Create a new tourney. Set the date, time format, number of brackets, time between games, and players")
                .addField("Information", "Complete all of the required commands below to create this tourney. To end this process, enter " + Utilities.prefix + "endtourney." +
                        "To see an updated status (resend this message with updated true/false on requirements), enter " + Utilities.prefix + "tourneystatus\n" + "When finished, enter " +
                        Utilities.prefix + "starttourney")
                .setAuthor(event.getMessageAuthor().getDisplayName(), "https://discord.gg/phqkRhfV5h/", event.getMessageAuthor().getAvatar())
                .addInlineField("Required Commands:", Utilities.prefix + "setdate\n" + Utilities.prefix + "setformat\n"
                        + Utilities.prefix + "setbrackets\n" + Utilities.prefix + "settime\n" + Utilities.prefix + "addplayers\n")
                .addInlineField("Status: ", date + "\n" + format + "\n" + brackets + "\n" + time + "\n" + players)
                .setColor(Color.orange)
                .setFooter("Leigh Chess Bot", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png");
        event.getChannel().sendMessage(embed);
    }
}
