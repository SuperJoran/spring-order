package be.jorandeboever.domain;

import be.jorandeboever.utilities.CurrencyFormatUtility;
import org.hibernate.annotations.Formula;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "SPR_FOOD_OPTION")
public class FoodOption extends DomainObject {

    private String name;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "CONFIGURATION_UUID", nullable = false)
    private FoodOptionConfiguration configuration;

    @Formula("(SELECT COUNT(*) FROM SPR_SELECTED_CHOICE choice WHERE choice.FOOD_OPTION_UUID = UUID)")
    private int count;

    @ElementCollection
    @CollectionTable(name = "SPR_SIMPLE_USER", joinColumns = @JoinColumn(name = "food_option_uuid"))
    @Column(name = "username")
    private final Collection<String> simpleUsers = new HashSet<>();

    public FoodOption() {
    }

    public FoodOption(FoodOption otherFoodOption) {
        this.name = otherFoodOption.name;
        this.price = otherFoodOption.price;
    }

    public FoodOption(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPriceAsString() {
        return CurrencyFormatUtility.formatAmount(this.price);
    }

    public int getCount() {
        return this.count + this.simpleUsers.size();
    }

    public Collection<String> getSimpleUsers() {
        return this.simpleUsers;
    }

    public void addSimpleUser(String username) {
        this.simpleUsers.add(username);
    }

    public void removeSimpleUser(String username) {
        this.simpleUsers.remove(username);
    }

    public FoodOptionConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(FoodOptionConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.getName(), this.getPriceAsString());
    }
}
