package be.jorandeboever.batch.event;

import be.jorandeboever.domain.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RenameEventNameItemProcessor implements ItemProcessor<Event, Event> {
    private static final Pattern ARCHIVED_NAME_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}-.*");
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public Event process(Event event) {
        LOG.debug(() -> String.format("Processing event with name %s", event.getName()));


        if (eventIsEmpty(event)) {
            //TODO_JORAN delete event
        }

        if (isOlderThan2Weeks(event.getDateTime()) && !eventIsAlreadyArchived(event.getName())) {
            String updatedName = String.format("%s-%s", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(event.getDateTime()), event.getName());
            LOG.debug(() -> String.format("Archiving event with name %s to %s", event.getName(), updatedName));
            event.setName(updatedName);
        }
        return event;
    }

    private static boolean eventIsEmpty(Event event) {
        return !event.getFoodOptionConfigurations()
                .stream()
                .flatMap(conf -> conf.getFoodOptions().stream())
                .findAny()
                .isPresent();
    }

    private static boolean isOlderThan2Weeks(ChronoLocalDateTime<LocalDate> dateTime) {
        return dateTime.isBefore(LocalDateTime.now().minusWeeks(2));
    }

    private static boolean eventIsAlreadyArchived(CharSequence eventName) {
        Matcher matcher = ARCHIVED_NAME_PATTERN.matcher(eventName);
        return matcher.find();
    }
}
