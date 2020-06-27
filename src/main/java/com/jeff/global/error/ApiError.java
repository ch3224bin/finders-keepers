package com.jeff.global.error;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiError {
	
	private HttpStatus status;
    private String message;
    private String code;
    private List<String> errors;
 
    public ApiError(HttpStatus status, String message, String code, List<String> errors) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.errors = errors;
    }
 
    public ApiError(HttpStatus status, String message, String code, String error) {
        this.status = status;
        this.message = message;
        this.code = code;
        errors = Arrays.asList(error);
    }
}
