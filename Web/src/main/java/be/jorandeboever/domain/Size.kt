package be.jorandeboever.domain

import be.jorandeboever.utilities.CurrencyFormatUtility
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "SPR_SIZE")
class Size : DomainObject {
    var name: String? = null
    var price: BigDecimal? = null

    @ManyToOne
    @JoinColumn(name = "FOOD_OPTION_UUID", nullable = false)
    var foodOption: FoodOption? = null

    val priceAsString: String?
        get() = CurrencyFormatUtility.formatAmount(this.price)

    constructor()

    constructor(name: String, price: BigDecimal?) {
        this.name = name
        this.price = price
    }
}
