package commands.essentials;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MeetingReminder {

    public static void getNextMeeting(MessageCreateEvent event) {
        LocalDate ld = LocalDate.now();
        ld = ld.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Next Club Meeting")
                .setDescription(ld.toString() + " at 2:15-2:45pm")
                .setAuthor(event.getMessageAuthor().getDisplayName(), "http://google.com/", event.getMessageAuthor().getAvatar())
                .setColor(Color.CYAN);
        event.getChannel().sendMessage(embed);
    }

    @SuppressWarnings("OctalInteger")
    public static void remindMeeting(TextChannel channel) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        boolean thursday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;

        if (thursday) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 12);
            c.set(Calendar.MINUTE, 00);
            c.set(Calendar.SECOND, 00);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    LocalDate ld = LocalDate.now();
                    ld = ld.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));

                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle("Reminder! Next Club Meeting is Today:")
                            .setDescription(ld.toString() + " at 2:15-2:45pm")
                            .setColor(Color.CYAN);
                    channel.sendMessage(embed);
                }
            }, c.getTime(), 86400000);
        }

    }
}
