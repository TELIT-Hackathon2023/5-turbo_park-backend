package sk.telekom.bctparking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String surname;

    private String password;

    private String number;

    private String email;

    private Long personalId;

    private String licencePlateNumber;
}
