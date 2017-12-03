package be.jorandeboever.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SPR_SELECTED_CHOICE")
public class SelectedChoice extends DomainObject {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "PERSON_UUID")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "FOOD_OPTION_UUID")
    private FoodOption foodOption;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SPR_SELECTED_EXTRA",
            joinColumns = @JoinColumn(name = "SELECTED_CHOICE_UUID"),
            inverseJoinColumns = @JoinColumn(name = "EXTRA_OPTION_UUID")
    )
    @Fetch(FetchMode.SELECT)
    private final List<ExtraOption> extraOptions = new ArrayList<>();

    public SelectedChoice() {
    }

    public SelectedChoice(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public FoodOption getFoodOption() {
        return this.foodOption;
    }

    public void setFoodOption(FoodOption foodOption) {
        this.foodOption = foodOption;
    }

    public List<ExtraOption> getExtraOptions() {
        return this.extraOptions;
    }

    public void addExtraOption(ExtraOption extraOption) {
        this.extraOptions.add(extraOption);
    }
}
