package sk.telekom.bctparking.mapper;

import org.mapstruct.Mapper;
import sk.telekom.bctparking.model.Employee;
import sk.telekom.openapi.model.EmployeeCreateDTO;
import sk.telekom.openapi.model.EmployeeResponseDTO;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee mapCreateDTOToEntity(EmployeeCreateDTO employeeCreateDTO);

    EmployeeResponseDTO mapEntityToResponseDTO(Employee employee);

}
