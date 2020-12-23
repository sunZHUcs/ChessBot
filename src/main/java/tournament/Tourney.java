package tournament;

import org.javacord.api.entity.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tourney {

    private int pairs;
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
        this.pairs = 0;
    }

    public void Pair(Tourney tourney) {
        tourney.pairs++;
        ArrayList<User> pairings = new ArrayList<>();
        HashMap<User, User> paired = new HashMap<>();
        Random randomgenerator = new Random();
        boolean pairingdone = false;

        for (int i = 0; i < tourney.brackets; i++) {
            for (Map.Entry<User, Integer> entry : tourney.Players.entrySet()) {
                User key = entry.getKey();
                int value = entry.getValue();

                if (value == i) {
                    pairings.add(key);
                }
            }

            int index = randomgenerator.nextInt(pairings.size());

            User user1 = pairings.get(index);
            User user2 = pairings.get(index);

            while (pairingdone = false) {
                if (!user1.equals(user2)) {
                    paired.put(user1, user2);
                    pairingdone = true;
                } else {
                    user2 = pairings.get(index);
                }
            }

            try {
                File myObj = new File("tournament/resources/" + tourney.date.substring(0, 2) + "/" + i + "-" + tourney.pairs + ".txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}
