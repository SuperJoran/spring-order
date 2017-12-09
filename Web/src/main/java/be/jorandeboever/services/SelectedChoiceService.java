package be.jorandeboever.services;

import be.jorandeboever.domain.SelectedChoice;

import java.util.List;

public interface SelectedChoiceService {
    SelectedChoice createOrUpdate(SelectedChoice selectedChoice);

    SelectedChoice createSelectedOption(String eventName, String foodUuid, String username);

    SelectedChoice addExtraOption(String eventName, String foodUuid, String extraUuid, String username);

    List<SelectedChoice> findByEventName(String eventName);

    void deleteAllByPersonUsernameAndEventName(String username, String eventName);

    void chooseSize(String eventName, String configName, String foodName, String sizeName, String username);
}
