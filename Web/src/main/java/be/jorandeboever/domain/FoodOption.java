package be.jorandeboever.domain;

import be.jorandeboever.utilities.CurrencyFormatUtility;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Entity
@Table(name = "SPR_FOOD_OPTION")
public class FoodOption extends DomainObject {

    private String name;

    @ManyToOne
    @JoinColumn(name = "CONFIGURATION_UUID", nullable = false)
    private FoodOptionConfiguration configuration;

    @Formula("(SELECT COUNT(*) FROM SPR_SELECTED_CHOICE choice" +
            "  INNER JOIN spr_size size on choice.size_uuid = size.uuid\n" +
            "  WHERE size.FOOD_OPTION_UUID = UUID)")
    private int count;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "FOOD_OPTION_UUID", nullable = false)
    @Fetch(FetchMode.SELECT)
    private final Collection<ExtraOption> extraOptions = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "foodOption")
    @Fetch(FetchMode.SELECT)
    @OrderBy("price")
    private final Collection<Size> sizesToChooseFrom = new HashSet<>();

    public FoodOption() {
    }

    public FoodOption(FoodOption otherFoodOption) {
        this.name = otherFoodOption.name;
    }

    public FoodOption(String name, BigDecimal price) {
        this.name = name;
        this.addSize(new Size("Default", price));
    }

    public boolean isMultipleSizes() {
        return this.sizesToChooseFrom.size() > 1;
    }

    public Collection<ExtraOption> getExtraOptions() {
        return this.extraOptions;
    }

    public String getExtraOptionsAsString() {
        return this.extraOptions.stream()
                .map(ExtraOption::toString)
                .collect(Collectors.joining(","));
    }

    public void addExtraOption(ExtraOption extraOption) {
        this.extraOptions.add(extraOption);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        if (this.sizesToChooseFrom.isEmpty()) {
            this.addSize(new Size("Default", null));
        }
        return this.sizesToChooseFrom.stream().findFirst().map(Size::getPrice).orElse(null);
    }

    public void setPrice(BigDecimal price) {
        this.sizesToChooseFrom.stream().findFirst().ifPresent(size -> size.setPrice(price));
    }

    public boolean hasMultipleSizes() {
        return this.sizesToChooseFrom.size() > 1;
    }

    public String getPriceAsString() {
        return CurrencyFormatUtility.formatAmount(this.getPrice());
    }

    public int getCount() {
        return this.count;
    }

    public FoodOptionConfiguration getConfiguration() {
        return this.configuration;
    }

    public Collection<Size> getSizesToChooseFrom() {
        return this.sizesToChooseFrom;
    }

    public final void addSize(Size size) {
        this.sizesToChooseFrom.add(size);
    }

    public void setConfiguration(FoodOptionConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return this.getName() + " " +
                this.sizesToChooseFrom.stream()
                        .map(Size::toString)
                        .collect(Collectors.joining(", "));
    }
}
