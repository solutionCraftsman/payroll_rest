package com.payroll.data.repository;

import com.payroll.data.dto.EmployeeDto;
import com.payroll.data.model.Employee;
import com.payroll.service.util.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"classpath:db/insert.sql"})
@Slf4j
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    EmployeeMapper employeeMapper;

    @BeforeEach
    void setUp() {
        employeeMapper = Mappers.getMapper(EmployeeMapper.class);
    }

    @Test
    void updateEmployeeRecordTest()
    {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("John");
        employeeDto.setLastName(null);
        employeeDto.setRole(null);

        Employee employee = new Employee();
        employee.setFirstName("Dan");
        employee.setLastName("Bob");
        employee.setRole("Mister");

        log.info("Employee before save --> {}", employee);

        employeeMapper.updateEmployeeFromDto(employeeDto, employee);

        assertThat(employee.getFirstName()).isEqualTo("John");
        assertThat(employee.getLastName()).isEqualTo("Bob");
        assertThat(employee.getRole()).isEqualTo("Mister");

        log.info("Employee after save --> {}", employee);
    }
}