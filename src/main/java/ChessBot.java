import commands.admin.AdminListener;
import commands.admin.Announce;
import commands.essentials.EssentialsListener;
import commands.essentials.attendance.Attendance;
import commands.essentials.resources.Resources;
import commands.essentials.zoom.ZoomListener;
import commands.games.GamesListener;
import commands.games.ReactionTime;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import tournament.TourneyCommand;

public class ChessBot {
    public static void main(String[] args) {
        DiscordApi api = new DiscordApiBuilder()
                .setToken("Nzg1NzE4MTI5NzcwMzY0OTQ5.X877CA.PAL4wYS1Xo67rVJpxG0L5yX27Ms")
                .setAllIntents().login().join();
        api.addMessageCreateListener(new AdminListener());
        api.addMessageCreateListener(new EssentialsListener());
        api.addMessageCreateListener(new GamesListener());
        api.addMessageCreateListener(new Announce());
        api.addMessageCreateListener(new TourneyCommand());
        api.addMessageCreateListener(new ZoomListener());
        api.addMessageCreateListener(new ReactionTime());
        api.addMessageCreateListener(new Resources());
        api.addMessageCreateListener(new Attendance());

        api.updateActivity("Leigh Chess Club");

    }
}
