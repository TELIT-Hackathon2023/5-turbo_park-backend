package sk.telekom.bctparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.telekom.bctparking.model.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmailOrLicencePlateNumberOrPersonalId(String email, String licencePlateNumber, Long personalId);

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String mail);
}
