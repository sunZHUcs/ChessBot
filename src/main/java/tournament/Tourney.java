package tournament;

import org.javacord.api.entity.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Tourney {

    private ArrayList<User> Beginners;
    private ArrayList<User> Intermediate;
    private ArrayList<User> Advanced;
    private ArrayList<User> Bye;
    private HashMap<User, User> begpairings;
    private HashMap<User, User> interpairings;
    private HashMap<User, User> advpairings;

    public Tourney(ArrayList<User> beg, ArrayList<User> inter, ArrayList<User> adv) throws FileNotFoundException {
        Beginners = beg;
        Intermediate = inter;
        Advanced = adv;

        PrintWriter advancedprinter = new PrintWriter("java/tournament/resources/latest/advanced");
        for(User line : Advanced){
            advancedprinter.println(line);
        }

        PrintWriter intermediateprinter = new PrintWriter("java/tournament/resources/latest/advanced");
        for(User line : Intermediate){
            intermediateprinter.println(line);
        }

        PrintWriter beginnerprinter = new PrintWriter("java/tournament/resources/latest/advanced");
        for(User line : Beginners){
            beginnerprinter.println(line);
        }
    }

    public void roundRobinPairing(){

        User one = null;
        User two = null;
        int numbeg = Beginners.size() ;
        int numint = Intermediate.size();
        int nmumadv = Advanced.size();

        while(numbeg % 2 != 0){
            int rand = (int) (Math.random() * Beginners.size());
            if(!Bye.contains(Beginners.get(rand))){
                Beginners.remove(rand);
            }
        }
        Collections.shuffle(Beginners);
        for (int i=0; i< Beginners.size(); i+=2) {
            one = Beginners.get(i);
            two = Beginners.get(i+1);
        }
        begpairings.put(one, two);

        while(numint % 2 != 0){
            int rand = (int) (Math.random() * Intermediate.size());
            if(!Bye.contains(Intermediate.get(rand))){
                Intermediate.remove(rand);
            }
        }
        Collections.shuffle(Intermediate);
        for (int i=0; i< Intermediate.size(); i+=2) {
            one = Intermediate.get(i);
            two = Intermediate.get(i+1);
        }
        interpairings.put(one, two);

        while(nmumadv % 2 != 0){
            int rand = (int) (Math.random() * Advanced.size());
            if(!Bye.contains(Advanced.get(rand))){
                Advanced.remove(rand);
            }
        }
        Collections.shuffle(Advanced);
        for (int i=0; i< Advanced.size(); i+=2) {
            one = Advanced.get(i);
            two = Advanced.get(i+1);
        }
        advpairings.put(one, two);


    }


}
