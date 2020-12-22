package tournament;

import messages.EventListener;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.util.HashMap;
import java.util.List;


public class TourneyCommand implements MessageCreateListener {

    public List<User> listofusers;
    final HashMap<User, Integer> Players = new HashMap<>();
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
                } else if (message.startsWith(EventListener.prefix + "setdate")) {
                    date = message.substring(EventListener.prefix.length() + "setdate".length());
                } else if (message.startsWith(EventListener.prefix + "setformat")) {
                    format = message.substring(EventListener.prefix.length() + "setformat".length());
                } else if (message.startsWith(EventListener.prefix + "setbrackets")) {
                    brackets = Integer.parseInt(message.substring(EventListener.prefix.length() + "setbrackets".length()));
                } else if (message.startsWith(EventListener.prefix + "settime")) {
                    time = Integer.parseInt(message.substring(EventListener.prefix.length() + "settime".length()));
                } else if (message.startsWith(EventListener.prefix + "addplayers")) {
                    players++;

                    int thisbracket = Integer.parseInt(message.substring(EventListener.prefix.length() + "addplayers".length(), EventListener.prefix.length() + "addplayers".length() + 2));
                    listofusers = event.getMessage().getMentionedUsers();

                    for (User listofuser : listofusers) {
                        Players.put(listofuser, thisbracket);
                    }
                } else if (message.startsWith(EventListener.prefix + "starttourney")) {
                    startTourney(event);
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
                .setTitle("Tourney Creation Process Started")
                .setDescription("Create a new tourney. Set the date, time format, number of brackets, time between games, and players")
                .addField("Information", "Complete all of the required commands below to create this tourney. To end this process, enter " + EventListener.prefix + "endtourney." +
                        "To see an updated status (resend this message with updated true/false on requirements), enter " + EventListener.prefix + "tourneystatus\n" + "When finished, enter " +
                        EventListener.prefix + "starttourney")
                .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                .addInlineField("Required Commands:", EventListener.prefix + "setdate\n" + EventListener.prefix + "setformat\n"
                        + EventListener.prefix + "setbrackets\n" + EventListener.prefix + "settime\n" + EventListener.prefix + "addplayers\n")
                .addInlineField("Status: ", date + "\n" + format + "\n" + brackets + "\n" + time + "\n" + players)
                .setColor(Color.RED)
                .setFooter("Leigh Chess Bot", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png");
        event.getChannel().sendMessage(embed);
    }
}
