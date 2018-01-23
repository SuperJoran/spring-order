package be.jorandeboever.services;

import be.jorandeboever.domain.searchresult.ChosenOption;

import java.util.List;

public interface ChosenOptionService {
    List<ChosenOption> findByEventName(String eventName);
}
