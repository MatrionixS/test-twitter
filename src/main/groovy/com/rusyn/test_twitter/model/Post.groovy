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
    List<Comment> comments = new ArrayList<>()
    long likes
    LocalDateTime createdAt = LocalDateTime.now()

    void addComment(Comment comment) {
        this.comments.add(comment)
    }

    void like() {
        likes = ++likes
    }

    void unlike() {
        likes = --likes
    }
}