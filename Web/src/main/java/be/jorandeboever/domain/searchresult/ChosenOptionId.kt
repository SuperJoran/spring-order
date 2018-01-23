package be.jorandeboever.domain.searchresult

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class ChosenOptionId : Serializable {

    @Column(insertable = false, updatable = false)
    var food_option_name: String? = null
    @Column(insertable = false, updatable = false)
    var size_name: String? = null
    @Column(insertable = false, updatable = false)
    var extras_combination_name: String? = null
}