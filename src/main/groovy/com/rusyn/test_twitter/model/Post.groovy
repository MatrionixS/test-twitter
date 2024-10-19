package com.rusyn.test_twitter.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import java.time.LocalDateTime

@Document(collection = "posts")
class Post {
    @Id
    String id
    String userId
    String content
    List<String> comments = new ArrayList<>()
    Set<String> likes = new HashSet<>()
    LocalDateTime createdAt = LocalDateTime.now()
}