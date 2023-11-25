package sk.telekom.bctparking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ParkingSlot {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private double[] coordinate1;

    @NotBlank
    private double[] coordinate2;

    @NotBlank
    private double[] coordinate3;

    @NotBlank
    private double[] coordinate4;
}
