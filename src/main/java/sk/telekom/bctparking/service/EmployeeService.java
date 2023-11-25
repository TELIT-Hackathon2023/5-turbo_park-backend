package sk.telekom.bctparking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.telekom.bctparking.mapper.EmployeeMapper;
import sk.telekom.bctparking.model.Employee;
import sk.telekom.bctparking.repository.EmployeeRepository;
import sk.telekom.openapi.model.EmployeeCreateDTO;
import sk.telekom.openapi.model.EmployeeResponseDTO;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeMapper employeeMapper;

    private final EmployeeRepository employeeRepository;

    public EmployeeResponseDTO register(EmployeeCreateDTO employeeCreateDTO) {
        Employee employee = employeeRepository.save(employeeMapper.mapCreateDTOToEntity(employeeCreateDTO));
        return employeeMapper.mapEntityToResponseDTO(employee);
    }
}
