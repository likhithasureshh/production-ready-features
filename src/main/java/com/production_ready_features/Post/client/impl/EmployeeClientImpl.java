package com.production_ready_features.Post.client.impl;

import com.production_ready_features.Post.client.EmployeeClient;
import com.production_ready_features.Post.dtos.EmployeeDTO;
import com.production_ready_features.Post.exceptions.ApiResponse;
import com.production_ready_features.Post.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {


    Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);

    private final RestClient restClient;
    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        log.trace("started executing the method createNewEmployee with employee :{}",employeeDTO);
        try
        {
            ApiResponse<EmployeeDTO> employeeDTOApiResponse = restClient.post()
                    .uri("employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res)->
                    {

                        log.info(new String(res.getBody().readAllBytes()));
                        throw new RuntimeException("client error occured");
                    })
                    .body(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>() {
                    });
            log.trace("created the employee:{}",employeeDTOApiResponse.getData());
            return employeeDTOApiResponse.getData();
        } catch (RuntimeException e) {
            log.error("error occurred in createNewEmployee");
            throw new RuntimeException(e);
        }
    }


    public EmployeeDTO getEmployeeById(Long empId)

    {
        log.trace("started executing the method getEmployeeById with employeeId :{}",empId);
        try
        {
            ApiResponse<EmployeeDTO> response = restClient.get()
                    .uri("employees/{empId}",empId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res)->
                    {
                        log.info(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("client error occured");
                    })
                    .body(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>() {
                    });
            log.trace("fetched the employee with id:{}",response.getData());
            return response.getData();
        }
        catch (RuntimeException e)
        {
            log.error("error occurred in getEmployeeById");
            throw new RuntimeException(e);
        }
    }

    public List<EmployeeDTO> getAllEmployees()
    {
        log.trace("started executing the method getAllEmployees");
        try
        {

            ApiResponse<List<EmployeeDTO>> listApiResponse = restClient.get()
                    .uri("employees")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res)->
                    {
                        log.info(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("client error occured");
                    })
                    .body(new ParameterizedTypeReference<ApiResponse<List<EmployeeDTO>>>() {
                    });
            log.trace("fetched all the employees:{} ",listApiResponse.getData());
            return listApiResponse.getData();
        }
        catch (RuntimeException e)
        {
            log.error("error occurred in getAllEmployees");
            throw new RuntimeException(e);
        }
    }
}
