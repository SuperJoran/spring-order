package be.jorandeboever.utilities;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Optional;

public class CurrencyFormatUtility {

    private CurrencyFormatUtility() {
    }

    public static String formatAmount(BigDecimal amount) {
        //TODO why is this an american format
        return Optional.ofNullable(amount).map(a -> NumberFormat.getCurrencyInstance().format(a)).orElse(null);
    }
}
