package com.production_ready_features.Post.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ApiError {
    private String error;
    private LocalDateTime localDateTime;
    private HttpStatus httpStatus;

    public ApiError() {
       localDateTime = LocalDateTime.now();
    }

    public ApiError(String error, HttpStatus httpStatus) {
        this();
        this.error = error;
        this.httpStatus = httpStatus;
    }
}
