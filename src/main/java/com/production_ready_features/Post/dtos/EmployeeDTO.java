package com.production_ready_features.Post.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long empId;

    private String empName;

    private String empEmail;

    private String role;

    private Integer empAge;

    private Double salary;

    private LocalDate dateOfJoining;

    private Boolean isActive;

}
