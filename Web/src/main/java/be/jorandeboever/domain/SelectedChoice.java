package be.jorandeboever.domain;

import be.jorandeboever.utilities.BigDecimalUtility;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "SPR_SELECTED_CHOICE")
public class SelectedChoice extends DomainObject {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "PERSON_UUID")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "SIZE_UUID")
    private Size size;

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

    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public FoodOption getFoodOption() {
        return this.getSize().getFoodOption();
    }

    public void setFoodOption(FoodOption foodOption) {
        this.setSize(foodOption.getSizesToChooseFrom().stream().findFirst().orElse(null));
    }

    public List<ExtraOption> getExtraOptions() {
        return this.extraOptions;
    }

    public void addExtraOption(ExtraOption extraOption) {
        this.extraOptions.add(extraOption);
    }

    public String getExtraOptionsAsString() {
        return this.extraOptions.stream()
                .map(ExtraOption::toString)
                .collect(Collectors.joining(","));
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.getFoodOption().toString(), this.getExtraOptionsAsString());
    }

    public BigDecimal getPrice() {
        return BigDecimalUtility.add(this.getFoodOption().getPrice(),
                this.extraOptions.stream()
                        .map(ExtraOption::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }
}
