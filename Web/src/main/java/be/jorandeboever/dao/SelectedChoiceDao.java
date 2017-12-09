package be.jorandeboever.dao;

import be.jorandeboever.domain.SelectedChoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectedChoiceDao extends JpaRepository<SelectedChoice, String> {

    List<SelectedChoice> findAllBySize_FoodOption_Configuration_Event_Name(String eventName);

    void deleteAllByPerson_UsernameAndSize_FoodOption_Configuration_Name(String username, String configName);
}
