package com.payroll.service.employee;

import com.payroll.data.dto.EmployeeDto;
import com.payroll.data.model.Employee;
import com.payroll.data.repository.EmployeeRepository;
import com.payroll.service.util.EmployeeMapper;
import com.payroll.web.exceptions.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ModelMapper modelMapper;

    EmployeeMapper employeeMapper;

    EmployeeServiceImpl() {
        employeeMapper = Mappers.getMapper(EmployeeMapper.class);
    }

    @Override
    public Employee save(EmployeeDto employeeDto)
    {
        /*if(employee == null) {
            throw new Exception("Employee cannot be null");
        }*/

        //Employee employee = modelMapper.map(employeeDto, Employee.class);

        Employee employee = new Employee();
        modelMapper.map(employeeDto, employee);

        log.info("Employee after mapping --> {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findById(Integer id)
    {

        /*if(id == null || id <= 0) {
            throw new Exception("Id cannot be " + id);
        }*/

        /*Optional<Employee> foundEmployee = employeeRepository.findById(id);

        if(foundEmployee.isPresent()) {
            return foundEmployee.get();
        }

        throw new Exception(("Employee with id " + id + " not found"));*/

        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> findAll()
    {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteById(Integer id)
    {
        /*if(id == null || id <= 0) {
            throw new Exception("Id cannot be " + id);
        }*/

        if(findById(id) != null) {
            employeeRepository.deleteById(id);
        }
    }

    @Override
    public Employee update(EmployeeDto employeeDto, Integer id) throws EmployeeNotFoundException {

        Employee employee = employeeRepository.findById(id).orElse(null);

        if(employee == null) {
            throw new EmployeeNotFoundException("Employee not found!");
        }

        employeeMapper.updateEmployeeFromDto(employeeDto, employee);
        log.info("Employee after mapping --> {}", employee);

        return employeeRepository.save(employee);
    }
}
