package com.payroll.service.util;

import com.payroll.data.dto.EmployeeDto;
import com.payroll.data.model.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring") //specify that this is to be injected
public interface EmployeeMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployeeFromDto(EmployeeDto employeeDto,
                               @MappingTarget Employee employee);

}
