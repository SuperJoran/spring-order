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
    @JoinColumn(name = "OWNER_UUID")
    private Person owner;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "CONFIGURATION_UUID", nullable = false)
    private final List<FoodOption> foodOptions = new ArrayList<>();

    public Person getOwner() {
        return this.owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public List<FoodOption> getFoodOptions() {
        return this.foodOptions;
    }

    public void addFoodOption(FoodOption foodOption) {
        this.getFoodOptions().add(foodOption);
    }
}
