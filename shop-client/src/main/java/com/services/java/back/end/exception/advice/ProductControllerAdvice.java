package com.services.java.back.end.exception.advice;

import com.services.java.back.end.clientDto.ErrorDto;
import com.services.java.back.end.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice(basePackages = "com.service.java.back.end.controller")
public class ProductControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorDto handleProductNotFound(ProductNotFoundException productNotFoundException){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setStatus(HttpStatus.NOT_FOUND.value());
        errorDto.setMessage("Product not found!");
        errorDto.setTimeStamp(LocalDateTime.now());

        return errorDto;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto processValidationError(MethodArgumentNotValidException e){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setStatus(HttpStatus.BAD_REQUEST.value());
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrorList = result.getFieldErrors();

        StringBuilder sb = new StringBuilder("Value invalid for the fields");
        for (FieldError fieldError: fieldErrorList){
            sb.append(" ");
            sb.append(fieldError.getField());
        }
        errorDto.setMessage(sb.toString());
        errorDto.setTimeStamp(LocalDateTime.now());

        return errorDto;
    }
}
