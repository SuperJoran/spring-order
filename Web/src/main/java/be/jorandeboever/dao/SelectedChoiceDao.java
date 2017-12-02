package be.jorandeboever.dao;

import be.jorandeboever.domain.SelectedChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectedChoiceDao extends JpaRepository<SelectedChoice, String> {

    @Query(value = "SELECT *\n" +
            "FROM SPR_SELECTED_CHOICE choice\n" +
            "  INNER JOIN SPR_FOOD_OPTION opt ON choice.FOOD_OPTION_UUID = opt.UUID\n" +
            "  INNER JOIN SPR_FOOD_OPTION_CONFIG config ON opt.CONFIGURATION_UUID = config.UUID\n" +
            "  INNER JOIN SPR_EVENT event ON config.UUID = event.CONFIGURATION_UUID\n" +
            "WHERE event.NAME = ?1", nativeQuery = true)
    List<SelectedChoice> findByEventName(String eventName);
}
