package be.jorandeboever.services;

import be.jorandeboever.domain.SelectedChoice;

import java.util.List;

public interface SelectedChoiceService {
    SelectedChoice createOrUpdate(SelectedChoice selectedChoice);

    SelectedChoice createOrUpdate(String eventName, String foodUuid, String username);

    List<SelectedChoice> findByEventName(String eventName);

    void deleteAllByPersonUsernameAndEventName(String username, String eventName);
}
