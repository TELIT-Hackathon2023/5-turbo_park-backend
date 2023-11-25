package sk.telekom.bctparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.telekom.bctparking.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmailOrLicencePlateNumberOrPersonalId(String email, String licencePlateNumber, Long personalId);
}
