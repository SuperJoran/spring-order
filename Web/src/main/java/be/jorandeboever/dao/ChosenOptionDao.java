package be.jorandeboever.dao;

import be.jorandeboever.domain.searchresult.ChosenOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChosenOptionDao extends JpaRepository<ChosenOption, String> {
    List<ChosenOption> findByEventName(String eventName);
}
