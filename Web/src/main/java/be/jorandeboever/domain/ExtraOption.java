package be.jorandeboever.domain;

import be.jorandeboever.utilities.CurrencyFormatUtility;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "SPR_EXTRA_OPTION")
public class ExtraOption extends DomainObject{
    private String name;
    private BigDecimal price;

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

}
