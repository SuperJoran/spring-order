package be.jorandeboever.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SPR_FOOD_OPTION_CONFIG")
public class FoodOptionConfiguration extends DomainObject {

    @ManyToOne
    @JoinColumn(name = "EVENT_UUID", nullable = false)
    private Event event;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "configuration")
    @Fetch(FetchMode.SELECT)
    private final List<FoodOption> foodOptions = new ArrayList<>();

    public FoodOptionConfiguration() {
    }

    public FoodOptionConfiguration(FoodOptionConfiguration otherFoodOptionConfiguration) {
        otherFoodOptionConfiguration.getFoodOptions()
                .forEach(foodOption -> {
                    FoodOption newFoodOption = new FoodOption(foodOption);
                    newFoodOption.setConfiguration(this);
                    this.foodOptions.add(newFoodOption);
                });

    }

    public FoodOptionConfiguration(Event event) {
        this.event = event;
    }

    public List<FoodOption> getFoodOptions() {
        return this.foodOptions;
    }

    public void addFoodOption(FoodOption foodOption) {
        this.getFoodOptions().add(foodOption);
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getNumberOfFoodOptions() {
        return this.foodOptions.size();
    }
}
