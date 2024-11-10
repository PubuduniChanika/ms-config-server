package com.microservices.employee_service.service.employeeImpl;

import com.microservices.employee_service.dto.ApiResponseDto;
import com.microservices.employee_service.dto.DepartmentDto;
import com.microservices.employee_service.dto.EmployeeDto;
import com.microservices.employee_service.entity.Employee;
import com.microservices.employee_service.repository.EmployeeRepository;
import com.microservices.employee_service.service.APIClient;
import com.microservices.employee_service.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private APIClient apiClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee(
                employeeDto.getId(),
                employeeDto.getFirstname(),
                employeeDto.getLastname(),
                employeeDto.getEmail(),
                employeeDto.getDepartmentCode()
        );
        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeDto savedEmployeeDto = new EmployeeDto(
                savedEmployee.getId(),
                savedEmployee.getFirstname(),
                savedEmployee.getLastname(),
                savedEmployee.getEmail(),
                savedEmployee.getDepartmentCode()
        );
        return savedEmployeeDto;
    }

    @Override
    public ApiResponseDto getEmployeeId(Long id) {
        Employee employee = employeeRepository.findById(id).get();
        DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());

        EmployeeDto employeeDto = new EmployeeDto(
                employee.getId(),
                employee.getFirstname(),
                employee.getLastname(),
                employee.getEmail(),
                employee.getDepartmentCode()
        );
        ApiResponseDto apiResponseDto  = new ApiResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);
        return apiResponseDto;
    }
}
