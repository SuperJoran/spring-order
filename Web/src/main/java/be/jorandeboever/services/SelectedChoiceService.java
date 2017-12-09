package be.jorandeboever.services;

import be.jorandeboever.domain.SelectedChoice;

import java.util.List;

public interface SelectedChoiceService {
    SelectedChoice createOrUpdate(SelectedChoice selectedChoice);

    SelectedChoice addExtraOption(String eventName, String foodUuid, String extraUuid, String username);

    List<SelectedChoice> findByEventName(String eventName);

    void chooseSize(String eventName, String configName, String foodName, String sizeName, String username);

    void chooseFood(String eventName, String configName, String foodName, String username);

}