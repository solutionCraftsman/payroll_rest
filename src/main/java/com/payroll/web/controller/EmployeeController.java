package com.payroll.web.controller;

import com.payroll.data.dto.EmployeeDto;
import com.payroll.data.model.Employee;
import com.payroll.service.employee.EmployeeService;
import com.payroll.web.exceptions.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> findAll()
    {
        List<Employee> employees = employeeService
                .findAll()
                .stream()
                .map(employee -> employee.add(
                        linkTo(methodOn(EmployeeController.class)
                                .findById(employee.getId())).withSelfRel(),

                        linkTo(methodOn(EmployeeController.class)
                                .findAll()).withRel("employees"))).collect(Collectors.toList());

        return ResponseEntity.ok().body(employees);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EmployeeDto employeeDto)
    {
        Employee employee = employeeService.save(employeeDto);
        /*EntityModel<Employee> entityModel =
                EntityModel.of(employee,*/
                               employee.add(
                        linkTo(methodOn(
                                EmployeeController.class)
                                .findById(employee.getId())).withSelfRel(),
                        linkTo(methodOn(
                                EmployeeController.class)
                                .findAll()).withRel("employees"));

        return ResponseEntity.created(
                employee.getRequiredLink(
                        IanaLinkRelations.SELF).toUri()).body(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id)
    {
        Employee employee = employeeService.findById(id);

        /*EntityModel<Employee> entityModel =
                EntityModel.of(employee,*/
                        employee.add(
                        linkTo(methodOn(
                                EmployeeController.class)
                                .findById(employee.getId())).withSelfRel(),
                        linkTo(methodOn(
                                EmployeeController.class)
                                .findAll()).withRel("employees"));

        return ResponseEntity.ok().body(employee);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer id,
                                   @RequestBody EmployeeDto employeeDto) {

        Employee employee = null;
        //EntityModel<Employee> entityModel = null;

        try {
            employee = employeeService.update(employeeDto, id);

            /*entityModel =
                    EntityModel.of(employee,*/
                            employee.add(
                            linkTo(methodOn(
                                    EmployeeController.class)
                                    .findById(employee.getId())).withSelfRel(),
                            linkTo(methodOn(
                                    EmployeeController.class)
                                    .findAll()).withRel("employees"));

        }
        catch(EmployeeNotFoundException exception) {
            ResponseEntity.badRequest().body(exception.getMessage());
        }

        return ResponseEntity.ok().body(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id)
    {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
