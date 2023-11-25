package sk.telekom.bctparking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sk.telekom.bctparking.service.EmployeeService;
import sk.telekom.openapi.api.EmployeeApi;
import sk.telekom.openapi.model.*;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeApi {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<EmployeeResponseDTO> createEmployee(EmployeeCreateDTO employeeCreateDTO) {
        return new ResponseEntity<>(employeeService.register(employeeCreateDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LoginEmployee200ResponseDTO> loginEmployee(EmployeeLoginDTO employeeLoginDTO) {
        return EmployeeApi.super.loginEmployee(employeeLoginDTO);
    }

    @Override
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(Long employeeId, EmployeeUpdateDTO employeeUpdateDTO) {
        return new ResponseEntity<>(employeeService.update(employeeId, employeeUpdateDTO), HttpStatus.OK);
    }
}
