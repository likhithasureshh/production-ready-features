package com.production_ready_features.Post.exceptions;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T> {

//    @JsonFormat(pattern = "hh:mm:ss dd-MM-yy")
    private LocalDateTime timeStamp;
    private T data;
    private ApiError apiError;

    public ApiResponse()
    {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError apiError) {
        this();
        this.apiError = apiError;
    }
}
