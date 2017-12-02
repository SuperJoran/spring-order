package be.jorandeboever.services;

import be.jorandeboever.domain.Event;
import be.jorandeboever.domain.FoodOption;
import be.jorandeboever.domain.SelectedChoice;
import be.jorandeboever.domain.searchresult.PersonChoicesSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonChoicesSearchResultServiceImpl implements PersonChoicesSearchResultService {
    private final SelectedChoiceService selectedChoiceService;
    private final EventService eventService;

    @Autowired
    public PersonChoicesSearchResultServiceImpl(SelectedChoiceService selectedChoiceService, EventService eventService) {
        this.selectedChoiceService = selectedChoiceService;
        this.eventService = eventService;
    }

    @Override
    public List<PersonChoicesSearchResult> findParticipantsByEventName(String eventName) {
        List<PersonChoicesSearchResult> searchResults = new ArrayList<>();
        searchResults.addAll(this.getAuthenticatedParticipants(eventName));
        searchResults.addAll(this.getSimpleParticipants(eventName));
        return searchResults;
    }

    private Collection<PersonChoicesSearchResult> getSimpleParticipants(String eventName) {
        //TODO restructure this method
        Event event = this.eventService.findByName(eventName);

        Map<String, List<FoodOption>> foodOptionMap = new HashMap<>();

        event.getFoodOptionConfiguration().getFoodOptions()
                .forEach(o -> o.getSimpleUsers().forEach(su -> {
                    List<FoodOption> list = Optional.ofNullable(foodOptionMap.get(su)).orElseGet(ArrayList::new);
                    list.add(o);
                    foodOptionMap.put(su, list);
                }));

        return foodOptionMap.entrySet()
                .stream()
                .map(stringFoodOptionEntry -> new PersonChoicesSearchResult(stringFoodOptionEntry.getKey(), stringFoodOptionEntry.getValue()))
                .collect(Collectors.toList());
    }

    private Collection<PersonChoicesSearchResult> getAuthenticatedParticipants(String eventName) {
        return this.selectedChoiceService.findByEventName(eventName).stream()
                .collect(Collectors.groupingBy(SelectedChoice::getPerson, Collectors.toList()))
                .entrySet()
                .stream()
                .map(personListEntry -> new PersonChoicesSearchResult(
                        personListEntry.getKey().getUsername(),
                        personListEntry.getValue().stream()
                                .map(SelectedChoice::getFoodOption)
                                .collect(Collectors.toList())))
                .sorted(Comparator.comparing(PersonChoicesSearchResult::getUsername))
                .collect(Collectors.toList());
    }
}
