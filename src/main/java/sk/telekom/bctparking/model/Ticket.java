package sk.telekom.bctparking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotBlank
    private Employee employee;

    @ManyToOne
    @NotBlank
    private ParkingSlot parkingSpot;

    @NotBlank
    private OffsetDateTime startDate;

    @NotBlank
    private OffsetDateTime endDate;


}
