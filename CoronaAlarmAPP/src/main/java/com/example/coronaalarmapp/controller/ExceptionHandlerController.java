package com.example.coronaalarmapp.controller;

import com.example.coronaalarmapp.dto.ErrorResponseDTO;
import com.example.coronaalarmapp.dto.ValidationResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResponseStatusException.class)
    public ErrorResponseDTO handleErrorResponse(ResponseStatusException ex) {

        return ErrorResponseDTO.builder()
                .status(ex.getStatus())
                .message(ex.getReason())
                .build();
    }

    @ExceptionHandler(ParseException.class)
    public ErrorResponseDTO handleErrorParse(ParseException ex) {

        return ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponseDTO> handleInputError(MethodArgumentNotValidException ex) {


        // Map<field, list<errors>>
        var responseBody = ex.getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                DefaultMessageSourceResolvable::getDefaultMessage,
                                Collectors.toList()
                        )
                ));

        ValidationResponseDTO response = ValidationResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errors(responseBody)
                .build();


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);

    }
}