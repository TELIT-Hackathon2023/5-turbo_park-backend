package sk.telekom.bctparking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.telekom.bctparking.exception.ParameterNotUniqueException;
import sk.telekom.bctparking.exception.ResourceNotFoundException;
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
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        employee.setLicencePlateNumber(employeeUpdateDTO.getLicencePlateNumber())
                .setPhoneNumber(employeeUpdateDTO.getPhoneNumber());
        Employee updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.mapEntityToResponseDTO(updatedEmployee);
    }

    public EmployeeResponseDTO getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return employeeMapper.mapEntityToResponseDTO(employee);
    }
}
