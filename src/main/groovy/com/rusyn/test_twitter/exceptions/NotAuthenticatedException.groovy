package com.rusyn.test_twitter.exceptions

class NotAuthenticatedException extends RuntimeException{
    NotAuthenticatedException(String message) {
        super(message)
    }
}
