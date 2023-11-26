package sk.telekom.bctparking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import sk.telekom.bctparking.service.EmployeeService;
import sk.telekom.openapi.api.EmployeeApi;
import sk.telekom.openapi.model.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController implements EmployeeApi {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<EmployeeResponseDTO> createEmployee(EmployeeCreateDTO employeeCreateDTO) {
        return new ResponseEntity<>(employeeService.register(employeeCreateDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<EmployeeLoginResponseDTO> loginEmployee(EmployeeLoginDTO employeeLoginDTO) {
        return new ResponseEntity<>(employeeService.login(employeeLoginDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(Long employeeId, EmployeeUpdateDTO employeeUpdateDTO) {
        return new ResponseEntity<>(employeeService.update(employeeId, employeeUpdateDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EmployeeResponseDTO> getEmployee(Long employeeId) {
        return new ResponseEntity<>(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
    }
}
