package be.jorandeboever.batch.event.remove;

import be.jorandeboever.domain.Event;
import be.jorandeboever.services.EventService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RemoveEventWriter  implements ItemWriter<Event>{

    private final EventService eventService;

    public RemoveEventWriter(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void write(List<? extends Event> items) throws Exception {
        this.eventService.removeAll(items.stream().map(i -> (Event) i).collect(Collectors.toList()));
    }
}
