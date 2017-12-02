package be.jorandeboever.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SPR_SELECTED_CHOICE")
public class SelectedChoice extends DomainObject {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "PERSON_UUID")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "FOOD_OPTION_UUID")
    private FoodOption foodOption;

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
}
