package be.jorandeboever.batch.event.remove;

import be.jorandeboever.domain.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class RemoveEventItemProcessor implements ItemProcessor<Event, Event> {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public Event process(Event item) throws Exception {
        LOG.debug(() -> String.format("Event %s is marked for removal", item.getName()));
        return item;

    }
}
