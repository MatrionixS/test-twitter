package com.rusyn.test_twitter.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
class User {

    @Id
    String id
    String username
    String password
    Set<String> following = new HashSet<>()
}