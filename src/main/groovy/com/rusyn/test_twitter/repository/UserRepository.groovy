package com.rusyn.test_twitter.repository

import com.rusyn.test_twitter.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username)
}