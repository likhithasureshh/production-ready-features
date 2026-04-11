package com.production_ready_features.Post.client;

import com.production_ready_features.Post.dtos.EmployeeDTO;

import java.util.List;

public interface EmployeeClient {
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);
    public EmployeeDTO getEmployeeById(Long empId);
    public List<EmployeeDTO> getAllEmployees();
}
