package be.jorandeboever.domain.searchresult;

import be.jorandeboever.domain.SelectedChoice;
import be.jorandeboever.utilities.CurrencyFormatUtility;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;

public class PersonChoicesSearchResult {

    private final String username;
    private final Collection<SelectedChoice> selectedFoodOptions;

    public PersonChoicesSearchResult(String username, Collection<SelectedChoice> selectedFoodOptions) {
        this.username = username;
        this.selectedFoodOptions = selectedFoodOptions;
    }

    public String getUsername() {
        return this.username;
    }

    public Collection<SelectedChoice> getSelectedChoices() {
        return this.selectedFoodOptions;
    }

    public String getSelectedChoicesAsString() {
        return this.selectedFoodOptions.stream()
                .map(SelectedChoice::toString)
                .collect(Collectors.joining(", "));
    }

    public String getTotalPrice() {
        return CurrencyFormatUtility.formatAmount(this.selectedFoodOptions.stream()
                .map(SelectedChoice::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
