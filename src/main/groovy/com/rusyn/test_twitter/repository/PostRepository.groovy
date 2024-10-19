package com.rusyn.test_twitter.repository

import com.rusyn.test_twitter.model.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findAllByUserIdIn(Set<String> userIds)
}