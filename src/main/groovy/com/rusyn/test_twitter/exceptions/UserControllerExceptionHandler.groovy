package com.rusyn.test_twitter.exceptions

import com.rusyn.test_twitter.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UserControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    ResponseEntity<ErrorResponse> handleError(UserAlreadyExistException exception) {
        def response = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value())
        ResponseEntity.badRequest().body(response)
    }
}
