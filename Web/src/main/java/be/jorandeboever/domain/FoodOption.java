package be.jorandeboever.domain;

import be.jorandeboever.utilities.CurrencyFormatUtility;
import org.hibernate.annotations.Formula;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SPR_FOOD_OPTION")
public class FoodOption extends DomainObject {

    private String name;
    private BigDecimal price;

    @Formula("(SELECT COUNT(*) FROM SPR_SELECTED_CHOICE choice WHERE choice.FOOD_OPTION_UUID = UUID)")
    private int count;

    @ElementCollection
    @CollectionTable(name = "SPR_SIMPLE_USER", joinColumns = @JoinColumn(name = "food_option_uuid"))
    @Column(name = "username")
    private final List<String> simpleUsers = new ArrayList<>();

    public FoodOption() {
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
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getSimpleUsers() {
        return this.simpleUsers;
    }

    public void addSimpleUser(String username) {
        this.simpleUsers.add(username);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.getName(), this.getPriceAsString());
    }
}
