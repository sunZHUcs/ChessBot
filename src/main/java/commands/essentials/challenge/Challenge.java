package commands.essentials.challenge;

import commands.Utilities;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.net.NetworkInterface;

public class Challenge{

    private String challengeLink;
    private User challenger;
    private User challenged;
    private boolean tourney;

    public Challenge(User chal, User chald, boolean tourney){
        challenged = chald;
        challenger = chal;
        this.tourney = tourney;
    }

    public Challenge(User chal, User chald, boolean tourney, String link){
        challenged = chald;
        challenger = chal;
        challengeLink = link;
        this.tourney = tourney;
    }

    public  void sendChallenge(){
        if(!tourney){
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Challenge")
                    .setDescription("You have been challenged by " + challenger.getName() + "!\nBe sure to message them accepting/denying this challenge!")
                    .setAuthor("Leigh Chess Club", "www.google.com", "https://cdn.discordapp.com/attachments/750904863994675311/769736563638272060/chessclub.png")
                    .setColor(Color.CYAN);
            challenged.sendMessage(embed);
        }

    }

}
