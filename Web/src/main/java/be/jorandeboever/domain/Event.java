package be.jorandeboever.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SPR_EVENT")
public class Event extends DomainObject {
    @Column(name = "DATETIME")
    private LocalDateTime dateTime;
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "OWNER_UUID")
    private Person owner;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER,  mappedBy = "event")
    @Fetch(FetchMode.SELECT)
    private List<FoodOptionConfiguration> foodOptionConfigurations = new ArrayList<>();

    @Formula(value = "(SELECT COUNT(DISTINCT c.person_uuid)\n" +
            "   FROM spr_selected_choice c\n" +
            "     INNER JOIN SPR_FOOD_OPTION foodOption ON c.FOOD_OPTION_UUID = foodOption.UUID\n" +
            "     INNER JOIN SPR_FOOD_OPTION_CONFIG config ON foodOption.CONFIGURATION_UUID = config.UUID\n" +
            "    WHERE config.EVENT_UUID = UUID\n" +
            "  )")
    private int numberOfAuthenticatedParticipants;

    @Formula(value = "(SELECT COUNT(DISTINCT simpleUser.username)\n" +
            "   FROM SPR_SIMPLE_USER simpleUser\n" +
            "     INNER JOIN SPR_FOOD_OPTION foodOption ON simpleUser.FOOD_OPTION_UUID = foodOption.UUID\n" +
            "     INNER JOIN SPR_FOOD_OPTION_CONFIG config ON foodOption.CONFIGURATION_UUID = config.UUID\n" +
            "   WHERE config.EVENT_UUID = UUID)")
    private int numberOfSimpleParticipants;

    public int getNumberOfParticipants() {
        return this.numberOfAuthenticatedParticipants + this.numberOfSimpleParticipants;
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
        return this.foodOptionConfigurations.stream().findFirst().orElse(null);
    }

    public void addFoodOptionConfiguration(FoodOptionConfiguration foodOptionConfiguration) {
        this.foodOptionConfigurations.add(foodOptionConfiguration);
    }

    public List<FoodOptionConfiguration> getFoodOptionConfigurations() {
        return this.foodOptionConfigurations;
    }

    public void setFoodOptionConfigurations(List<FoodOptionConfiguration> foodOptionConfigurations) {
        this.foodOptionConfigurations = foodOptionConfigurations;
    }
}
