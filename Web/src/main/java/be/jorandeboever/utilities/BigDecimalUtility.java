package be.jorandeboever.utilities;

import java.math.BigDecimal;

public class BigDecimalUtility {
    public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
        BigDecimal one = value1 == null ? BigDecimal.ZERO : value1;
        BigDecimal two = value2 == null ? BigDecimal.ZERO : value2;

        return one.add(two);
    }
}
