package be.jorandeboever.utilities;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class CurrencyFormatUtility {

    private CurrencyFormatUtility() {
    }

    public static String formatAmount(BigDecimal amount) {
        //TODO why is this an american format
        return NumberFormat.getCurrencyInstance().format(amount);
    }
}
