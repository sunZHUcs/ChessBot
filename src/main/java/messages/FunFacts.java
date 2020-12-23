package messages;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

public class FunFacts {

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static void outputFact(MessageCreateEvent event) throws IOException {

        Random y = new Random();
        int multiplier = y.nextInt(297 / 3);
        int index = multiplier * 3;
        int titl = index + 1;
        int desc = index + 2;
        String title;
        String description;

        try (Stream<String> lines = Files.lines(Paths.get("src/main/java/messages/resources/funfacts.txt"))) {
            title = lines.skip(titl - 1).findFirst().get();
        }
        try (Stream<String> lines = Files.lines(Paths.get("src/main/java/messages/resources/funfacts.txt"))) {
            description = lines.skip(desc - 1).findFirst().get();
        }

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(title)
                .setDescription(description);

        event.getChannel().sendMessage(embed);
    }
}

