package com.rusyn.test_twitter.exceptions

class UserAlreadyExistException extends RuntimeException{
    UserAlreadyExistException(String message) {
        super(message)
    }
}
