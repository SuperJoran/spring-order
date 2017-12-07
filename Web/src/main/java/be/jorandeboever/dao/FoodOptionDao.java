package be.jorandeboever.dao;

import be.jorandeboever.domain.FoodOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOptionDao extends JpaRepository<FoodOption, String> {
}
