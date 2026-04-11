package com.production_ready_features.Post;

import com.production_ready_features.Post.client.EmployeeClient;
import com.production_ready_features.Post.dtos.EmployeeDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostApplicationTests {


	@Autowired
	private EmployeeClient employeeClient;
	@Test
	@Order(1)
	void createNewEmployee()
	{
		EmployeeDTO employeeDTO = new EmployeeDTO(null,"likitha","liki@gmail.com","USER",33,5000.00, LocalDate.of(2025,07,23),true);
	    EmployeeDTO employeeDTO1 = employeeClient.createNewEmployee(employeeDTO);
		System.out.println(employeeDTO1);
	}

	@Test
	@Order(2)
	void getEmployeeById()
	{
		EmployeeDTO employeeDTO = employeeClient.getEmployeeById(100L);
		System.out.println(employeeDTO);
	}

	@Test
	@Order(3)
	void getAllEmployees()
	{
		List<EmployeeDTO> employeeDTOS = employeeClient.getAllEmployees();
		System.out.println(employeeDTOS);
	}

}
