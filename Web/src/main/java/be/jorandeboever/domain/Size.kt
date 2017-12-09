package be.jorandeboever.domain

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "SPR_SIZE")
class Size : DomainObject {
    var name: String? = null
    var price: BigDecimal? = null

    constructor() : super()

    constructor(name: String, price: BigDecimal?) : super() {
        this.name = name
        this.price = price
    }
}