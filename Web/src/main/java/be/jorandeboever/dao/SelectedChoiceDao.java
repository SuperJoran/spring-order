package be.jorandeboever.dao;

import be.jorandeboever.domain.SelectedChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectedChoiceDao extends JpaRepository<SelectedChoice, String> {

    List<SelectedChoice> findAllByFoodOption_Configuration_Event_Name(String eventName);

    void deleteAllByPerson_UsernameAndFoodOption_Configuration_Event_Name(String username, String eventName);
}
