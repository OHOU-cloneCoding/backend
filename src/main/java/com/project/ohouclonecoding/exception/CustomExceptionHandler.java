package com.project.ohouclonecoding.exception;

import com.project.ohouclonecoding.dto.MessageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException(IllegalArgumentException e){
        log.error("IllegalArgumentException", e);
        return new ResponseEntity<>(new MessageResponseDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
