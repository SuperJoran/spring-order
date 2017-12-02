package be.jorandeboever.services;

import be.jorandeboever.domain.searchresult.PersonChoicesSearchResult;

import java.util.List;

public interface PersonChoicesSearchResultService {

    List<PersonChoicesSearchResult> findParticipantsByEventName(String eventName);

}
