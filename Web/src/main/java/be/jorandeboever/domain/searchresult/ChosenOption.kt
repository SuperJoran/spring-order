package be.jorandeboever.domain.searchresult


import be.jorandeboever.utilities.BigDecimalUtility
import org.hibernate.annotations.Immutable
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "V_CHOSEN_OPTION")
@Immutable
class ChosenOption  {
    @Id
    @Embedded
    var id: ChosenOptionId? = null

    @Column(name = "EVENT_NAME")
    var eventName: String? = null
    @Column(name = "FOOD_OPTION_NAME", insertable = false, updatable = false)
    var foodOptionName: String? = null
    @Column(name = "SIZE_NAME", insertable = false, updatable = false)
    var sizeName: String? = null
    @Column(name = "SIZE_PRICE")
    var sizePrice: BigDecimal? = null
    @Column(name = "EXTRAS_COMBINATION_NAME", insertable = false, updatable = false)
    var extraOptionName: String? = null
    @Column(name = "EXTRAS_COMBINATION_PRICE")
    var extraOptionPrice: BigDecimal? = null
    @Column(name = "COUNT")
    var count: Int = 0

    val totalPrice: BigDecimal?
        get() = BigDecimalUtility.add(sizePrice, extraOptionPrice)
}
