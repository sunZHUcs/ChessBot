package messages;

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

        if (message.startsWith(EventListener.prefix + "attendance")) {
            if (message.substring(EventListener.prefix.length() + "attendance".length()).isEmpty()) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Attendance")
                        .setDescription("To use this command, input " + EventListener.prefix + "attendance [password]\n Every meeting the club officers will provide a password," +
                                "which you must input here to take attendance and prove you were present at the meeting.")
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.CYAN);
                event.getChannel().sendMessage(embed);
            } else {
                List<String> string = Arrays.asList(message.split(" "));
                String inputpass = string.get(1);

                if (!password.equals("")) {
                    if (inputpass.equals(password)) {
                        File file = new File("src/main/java/messages/resources/attendance/" + ld + ".txt");
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
                                EmbedBuilder embed = new EmbedBuilder()
                                        .setTitle("Attendance")
                                        .setDescription("You have already taken attendance for this meeting.")
                                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                                        .setColor(Color.CYAN);
                                event.getChannel().sendMessage(embed);
                            }

                        } else {
                            try {
                                if (file.createNewFile()) {
                                    System.out.println("File created: " + file.getName());
                                } else {
                                    System.out.println("File already exists.");
                                }
                            } catch (IOException e) {
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                            }

                            try {
                                FileWriter myWriter = new FileWriter(file);
                                myWriter.write(event.getMessageAuthor().getDisplayName());
                                myWriter.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            attendanceTaken(event);
                        }

                    } else {
                        EmbedBuilder embed = new EmbedBuilder()
                                .setTitle("Attendance Failed")
                                .setDescription("Incorrect Password")
                                .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                                .setColor(Color.CYAN);
                        event.getChannel().sendMessage(embed);
                    }
                } else {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Attendance")
                            .setDescription("Attendance is not currently being taken")
                            .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                            .setColor(Color.CYAN);
                    event.getChannel().sendMessage(embed);
                }

            }
        }

        if (event.getMessageAuthor().isServerAdmin()) {
            if (message.startsWith(EventListener.prefix + "setattendance")) {
                List<String> string = Arrays.asList(message.split(" "));
                String inputpass = string.get(1);
                setPassword(inputpass);

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Attendance")
                        .setDescription("Attendance Password set to: " + inputpass)
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.ORANGE);
                event.getChannel().sendMessage(embed);
            } else if (message.startsWith(EventListener.prefix + "viewattendance")) {

                File[] files = new File("src/main/java/messages/resources/attendance/").listFiles();
                StringBuilder dates = new StringBuilder();

                if (message.substring(EventListener.prefix.length() + "viewattendance".length()).isEmpty()) {
                    assert files != null;
                    if (files.length != 0) {
                        for (File file : files) {

                            String current = file.toString().replace("src\\main\\java\\messages\\resources\\attendance\\", "");
                            current = current.replace(".txt", "");
                            System.out.println(current);
                            dates.append(current).append("\n");
                        }
                    } else {
                        dates = new StringBuilder("No dates available (No attendance has been taken)");
                    }
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Attendance")
                            .setDescription("To view attendance, input " + EventListener.prefix + "viewattendance [date]")
                            .addField("List of Dates", dates.toString())
                            .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                            .setColor(Color.ORANGE);
                    event.getChannel().sendMessage(embed);
                } else {
                    List<String> string = Arrays.asList(message.split(" "));
                    String inputpath = string.get(1);

                    File file = new File("src/main/java/messages/resources/attendance/" + inputpath + ".txt");

                    if (file.exists()) {
                        try {
                            Scanner scanner = new Scanner(file);
                            while (scanner.hasNextLine()) {
                                dates.append(scanner.nextLine()).append("\n");
                            }

                            EmbedBuilder embed = new EmbedBuilder()
                                    .setTitle("Attendance: " + inputpath)
                                    .setDescription(dates.toString())
                                    .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                                    .setColor(Color.ORANGE);
                            event.getChannel().sendMessage(embed);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    } else {
                        EmbedBuilder embed = new EmbedBuilder()
                                .setTitle("Attendance")
                                .setDescription("Invalid Input")
                                .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                                .setColor(Color.ORANGE);
                        event.getChannel().sendMessage(embed);
                    }
                }
            } else if (message.startsWith(EventListener.prefix + "endattendance")) {
                password = "";
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Attendance")
                        .setDescription("Attendance Ended")
                        .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                        .setColor(Color.ORANGE);
                event.getChannel().sendMessage(embed);
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
