package commands.essentials.attendance;

import commands.Utilities;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Attendance implements MessageCreateListener {

    final LocalDate ld = LocalDate.now();
    private String password = "";

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessageContent();

        if (message.startsWith(Utilities.prefix + "attendance")) {

            //If the input command has no parameters, output help message
            if (message.substring(Utilities.prefix.length() + "attendance".length()).isEmpty()) {
                Utilities.embedBuilder("Attendance", "To use this command, input " + Utilities.prefix + "attendance [password]\n Every meeting the club officers will provide a password," +
                        "which you must input here to take attendance and prove you were present at the meeting.", false, event);
                //Else do this:
            } else {
                List<String> string = Arrays.asList(message.split(" "));
                String inputpass = string.get(1);

                //Checking if attendance is being taken.
                if (!password.equals("")) {

                    //If input password is correct, do this:
                    if (inputpass.equals(password)) {
                        File file = new File("src/main/java/commands/essentials/attendance/logs" + ld + ".txt");

                        //If Attendance File exists, do this:
                        if (file.exists()) {
                            StringBuilder all = new StringBuilder();
                            try {
                                Scanner scanner = new Scanner(file);
                                while (scanner.hasNextLine()) {
                                    all.append(scanner.nextLine());
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            if (!all.toString().contains(event.getMessageAuthor().getDisplayName())) {
                                try {
                                    FileWriter myWriter = new FileWriter(file, true);
                                    myWriter.write("\n" + event.getMessageAuthor().getDisplayName());
                                    myWriter.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                attendanceTaken(event);
                            } else {
                                Utilities.embedBuilder("Attendance", "You have already taken attendance for this meeting.", false, event);
                            }
                        }
                        //If Attendance File doesn't exist, do this:
                        else {
                            Utilities.fileBuilder(file);
                            try {
                                FileWriter myWriter = new FileWriter(file);
                                myWriter.write(event.getMessageAuthor().getDisplayName());
                                myWriter.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            attendanceTaken(event);
                        }
                    }
                    //If input password is incorrect, do this:
                    else {
                        Utilities.embedBuilder("Attendance", "Incorrect Password.", false, event);
                    }
                }
                //If attendance is not being taken, output this.
                else {
                    Utilities.embedBuilder("Attendance", "Attendance is not currently being taken.", false, event);
                }
            }
        }

        //Admin only commands
        if (event.getMessageAuthor().isServerAdmin()) {
            if (message.startsWith(Utilities.prefix + "setattendance")) {

                //If no parameters are entered, do this:
                if (message.substring(Utilities.prefix.length() + "setattendance".length()).isEmpty()) {
                    Utilities.embedBuilder("Attendance", "To set the attendance password, input " + Utilities.prefix + "setattendance [password]", true, event);
                    //Else do this:
                } else {
                    List<String> string = Arrays.asList(message.split(" "));
                    String inputpass = string.get(1);
                    setPassword(inputpass);
                    Utilities.embedBuilder("Attendance", "Attendance Password set to: " + inputpass, true, event);
                }
            } else if (message.startsWith(Utilities.prefix + "viewattendance")) {

                File[] files = new File("src/main/java/commands/essentials/attendance/logs/").listFiles();
                StringBuilder dates = new StringBuilder();

                //If no parameters are entered, do this:
                if (message.substring(Utilities.prefix.length() + "viewattendance".length()).isEmpty()) {
                    assert files != null;
                    //If attendance file(s) exist, do this:
                    if (files.length != 0) {
                        for (File file : files) {

                            String current = file.toString().replace("src\\main\\java\\commands\\essentials\\attendance\\logs\\", "");
                            current = current.replace(".txt", "");
                            System.out.println(current);
                            dates.append(current).append("\n");
                        }
                        //If no attendance file(s) exist, do this:
                    } else {
                        dates = new StringBuilder("No dates available (No attendance has been taken)");
                    }

                    //Output attendance help message
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Attendance")
                            .setDescription("To view attendance, input " + Utilities.prefix + "viewattendance [date]")
                            .addField("List of Dates", dates.toString())
                            .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                            .setColor(Color.ORANGE);
                    event.getChannel().sendMessage(embed);

                    //If parameter (date) is entered, do this:
                } else {
                    List<String> string = Arrays.asList(message.split(" "));
                    String inputpath = string.get(1);

                    File file = new File("src/main/java/commands/essentials/attendance/logs/" + inputpath + ".txt");

                    //If Attendance File for that date exists, do this:
                    if (file.exists()) {
                        try {
                            Scanner scanner = new Scanner(file);
                            while (scanner.hasNextLine()) {
                                dates.append(scanner.nextLine()).append("\n");
                            }
                            Utilities.embedBuilder("Attendance", dates.toString(), true, event);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        //Else do this:
                    } else {
                        Utilities.embedBuilder("Attendance", "Invalid Input", true, event);
                    }
                }
                //Ends Attendance
            } else if (message.startsWith(Utilities.prefix + "endattendance")) {
                password = "";
                Utilities.embedBuilder("Attendance", "Attendance Ended", true, event);
            }
        }
    }

    public void setPassword(String input) {
        password = input;
    }

    public void attendanceTaken(MessageCreateEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Attendance Taken")
                .setDescription("You have been marked as present for the meeting on: " + ld)
                .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                .setColor(Color.CYAN);
        event.getChannel().sendMessage(embed);
    }
}
