package com.services.java.back.end.exception.advice;

import com.services.java.back.end.clientDto.ErrorDto;
import com.services.java.back.end.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.time.LocalDateTime;

@ControllerAdvice(basePackages = "com.service.java.back.end.controller")
public class UserControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorDto handleUserNotFound(UserNotFoundException userNotFoundException){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setStatus(HttpStatus.NOT_FOUND.value());
        errorDto.setMessage("Usuário não encontrado!");
        errorDto.setTimeStamp(LocalDateTime.now());

        return errorDto;
    }
}
