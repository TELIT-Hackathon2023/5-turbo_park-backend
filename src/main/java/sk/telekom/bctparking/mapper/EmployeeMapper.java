package sk.telekom.bctparking.mapper;

import org.mapstruct.Mapper;
import sk.telekom.bctparking.model.Employee;
import sk.telekom.openapi.model.EmployeeCreateDTO;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee mapCreateDTOToEntity(EmployeeCreateDTO employeeCreateDTO);
}
