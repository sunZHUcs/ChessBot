import messages.Announce;
import messages.EventListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import tournament.TourneyCommand;

public class ChessBot {
    public static void main(String[] args) {
        DiscordApi api = new DiscordApiBuilder()
                .setToken("Nzg1NzE4MTI5NzcwMzY0OTQ5.X877CA.PAL4wYS1Xo67rVJpxG0L5yX27Ms")
                .setAllIntents().login().join();
        api.addMessageCreateListener(new EventListener());
        api.addMessageCreateListener(new Announce());
        api.addMessageCreateListener(new TourneyCommand());

        api.updateActivity("Leigh Chess Club");

    }
}
