package com.example.registrationsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Response {

    private boolean success;
    private String message;
    private Object dataList;
    private Map<Object, Object> map = new HashMap<>();
    private HttpStatus status;


    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
        if(success)
            this.status = HttpStatus.OK;
    }

    public Response(boolean success, String message, Object dataList) {
        this.success = success;
        this.message = message;
        this.dataList = dataList;
        if(success)
            this.status = HttpStatus.OK;
    }

    public Response(boolean success, String message, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }
}
