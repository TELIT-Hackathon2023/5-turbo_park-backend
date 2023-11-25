package sk.telekom.bctparking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.telekom.bctparking.exception.ParameterNotUniqueException;
import sk.telekom.bctparking.mapper.EmployeeMapper;
import sk.telekom.bctparking.model.Employee;
import sk.telekom.bctparking.repository.EmployeeRepository;
import sk.telekom.openapi.model.EmployeeCreateDTO;
import sk.telekom.openapi.model.EmployeeResponseDTO;
import sk.telekom.openapi.model.EmployeeUpdateDTO;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeMapper employeeMapper;

    private final EmployeeRepository employeeRepository;

    public EmployeeResponseDTO register(EmployeeCreateDTO employeeCreateDTO) {
        Employee employee = employeeMapper.mapCreateDTOToEntity(employeeCreateDTO);

        if (employeeRepository.existsByEmailOrLicencePlateNumberOrPersonalId(employee.getEmail(), employee.getLicencePlateNumber(), employee.getPersonalId())) {
            throw new ParameterNotUniqueException("Parameters you provided were not unique");
        }

        employee = employeeRepository.save(employee);
        return employeeMapper.mapEntityToResponseDTO(employee);
    }

//    public EmployeeResponseDTO login(EmployeeLoginDTO employeeLoginDTO) {
//    }

    public EmployeeResponseDTO update(Long employeeId, EmployeeUpdateDTO employeeUpdateDTO) {

    }
}
