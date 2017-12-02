package be.jorandeboever.domain.searchresult;

import be.jorandeboever.domain.FoodOption;
import be.jorandeboever.utilities.CurrencyFormatUtility;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;

public class PersonChoicesSearchResult {

    private final String username;
    private final Collection<FoodOption> selectedFoodOptions;

    public PersonChoicesSearchResult(String username, Collection<FoodOption> selectedFoodOptions) {
        this.username = username;
        this.selectedFoodOptions = selectedFoodOptions;
    }

    public String getUsername() {
        return this.username;
    }

    public Collection<FoodOption> getSelectedChoices() {
        return this.selectedFoodOptions;
    }

    public String getSelectedChoicesAsString() {
        return this.selectedFoodOptions.stream()
                .map(FoodOption::toString)
                .collect(Collectors.joining(", "));
    }

    public String getTotalPrice() {
        return CurrencyFormatUtility.formatAmount(this.selectedFoodOptions.stream()
                .map(FoodOption::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
