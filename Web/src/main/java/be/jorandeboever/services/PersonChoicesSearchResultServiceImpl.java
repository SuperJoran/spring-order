package be.jorandeboever.services;

import be.jorandeboever.domain.SelectedChoice;
import be.jorandeboever.domain.searchresult.PersonChoicesSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class PersonChoicesSearchResultServiceImpl implements PersonChoicesSearchResultService {
    private final SelectedChoiceService selectedChoiceService;

    @Autowired
    public PersonChoicesSearchResultServiceImpl(SelectedChoiceService selectedChoiceService) {
        this.selectedChoiceService = selectedChoiceService;
    }

    @Override
    public Collection<PersonChoicesSearchResult> findParticipantsByEventName(String eventName) {
        return this.getAuthenticatedParticipants(eventName);
    }

    private Collection<PersonChoicesSearchResult> getAuthenticatedParticipants(String eventName) {
        return this.selectedChoiceService.findByEventName(eventName).stream()
                .collect(Collectors.groupingBy(SelectedChoice::getPerson, Collectors.toList()))
                .entrySet()
                .stream()
                .map(personListEntry -> new PersonChoicesSearchResult(
                        personListEntry.getKey().getUsername(),
                        new ArrayList<SelectedChoice>(personListEntry.getValue())))
                .sorted(Comparator.comparing(PersonChoicesSearchResult::getUsername))
                .collect(Collectors.toList());
    }
}
