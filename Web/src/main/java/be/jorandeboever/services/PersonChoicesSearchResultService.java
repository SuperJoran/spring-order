package be.jorandeboever.services;

import be.jorandeboever.domain.searchresult.PersonChoicesSearchResult;

import java.util.Collection;

public interface PersonChoicesSearchResultService {

    Collection<PersonChoicesSearchResult> findParticipantsByEventName(String eventName);

}
