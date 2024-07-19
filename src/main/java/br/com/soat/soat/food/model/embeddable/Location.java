package br.com.soat.soat.food.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {

    private String streetNumber;
    private String streetName;
    private String cityName;
    private String stateName;
    private double latitude;
    private double longitude;
    private String reference;

}
