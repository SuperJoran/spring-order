package be.jorandeboever.dao;

import be.jorandeboever.domain.FoodOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodOptionDao extends JpaRepository<FoodOption, String> {
}
