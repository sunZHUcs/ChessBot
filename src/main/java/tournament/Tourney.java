package tournament;

import org.javacord.api.entity.user.User;

import java.util.HashMap;

public class Tourney {

    private String date;
    private String format;
    private int brackets;
    private int time;
    private HashMap<User, Integer> Players;

    public Tourney(String date, String format, int brackets, int time, HashMap<User, Integer> Players) {

        this.date = date;
        this.format = format;
        this.brackets = brackets;
        this.time = time;
        this.Players = Players;
    }
}
