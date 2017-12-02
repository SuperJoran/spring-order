package be.jorandeboever.domain;

import org.hibernate.annotations.Formula;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "SPR_EVENT")
public class Event extends DomainObject {
    @Column(name = "DATETIME")
    private LocalDateTime dateTime;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNER_UUID")
    private Person owner;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CONFIGURATION_UUID")
    @Access(AccessType.PROPERTY)
    private FoodOptionConfiguration foodOptionConfiguration;

    @Formula("(SELECT COUNT(0)\n" +
            "   FROM\n" +
            "     (SELECT DISTINCT selectedChoice.PERSON_UUID\n" +
            "      FROM SPR_SELECTED_CHOICE selectedChoice\n" +
            "        INNER JOIN SPR_FOOD_OPTION foodOption ON selectedChoice.FOOD_OPTION_UUID = foodOption.UUID\n" +
            "        INNER JOIN SPR_FOOD_OPTION_CONFIG config ON foodOption.CONFIGURATION_UUID = config.UUID\n" +
            "      WHERE config.UUID = CONFIGURATION_UUID))")
    private int numberOfParticipants;

    public int getNumberOfParticipants() {
        return this.numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public String getDateTimeAsString() {
        return DateTimeFormatter.ofPattern("d MMMM yy").format(this.dateTime);
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getOwner() {
        return this.owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public FoodOptionConfiguration getFoodOptionConfiguration() {
        if (this.foodOptionConfiguration == null) {
            this.setFoodOptionConfiguration(new FoodOptionConfiguration());
        }
        return this.foodOptionConfiguration;
    }

    public void setFoodOptionConfiguration(FoodOptionConfiguration foodOptionConfiguration) {
        this.foodOptionConfiguration = foodOptionConfiguration;
    }
}
