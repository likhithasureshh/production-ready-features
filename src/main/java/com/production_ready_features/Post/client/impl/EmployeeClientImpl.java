package com.production_ready_features.Post.client.impl;

import com.production_ready_features.Post.client.EmployeeClient;
import com.production_ready_features.Post.dtos.EmployeeDTO;
import com.production_ready_features.Post.exceptions.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {


    private final RestClient restClient;
    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        try
        {
            ApiResponse<EmployeeDTO> employeeDTOApiResponse = restClient.post()
                    .uri("employees")
                    .body(employeeDTO)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>() {
                    });
            return employeeDTOApiResponse.getData();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    public EmployeeDTO getEmployeeById(Long empId)
    {
        try
        {
            ApiResponse<EmployeeDTO> response = restClient.get()
                    .uri("employees/{empId}",empId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res)->
                    {
                        System.out.println(new String(res.getBody().readAllBytes()));
                        throw new RuntimeException("client error occured");
                    })
                    .body(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>() {
                    });
            return response.getData();
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException(e);
        }
    }

    public List<EmployeeDTO> getAllEmployees()
    {
        try
        {
            ApiResponse<List<EmployeeDTO>> listApiResponse = restClient.get()
                    .uri("employees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<List<EmployeeDTO>>>() {
                    });
            return listApiResponse.getData();
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException(e);
        }
    }
}
