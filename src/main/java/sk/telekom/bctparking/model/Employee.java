package sk.telekom.bctparking.model;

import jakarta.persistence.Column;
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

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private Long personalId;

    @Column(unique = true)
    private String licencePlateNumber;
}
