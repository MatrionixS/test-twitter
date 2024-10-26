package com.rusyn.test_twitter.dto

class ErrorResponse {

    String message
    Integer statusCode

    ErrorResponse(String message, Integer statusCode) {
        this.message = message
        this.statusCode = statusCode
    }
}
