package com.rusyn.test_twitter.dto

import java.time.LocalDateTime

class PostDto {
    String id
    String userId
    String content
    List<CommentDto> comments = new ArrayList<>()
    Long likes
    LocalDateTime createdAt

    PostDto() {
    }

    PostDto(String content) {
        this.content = content
    }

    void setComments(List<CommentDto> comments) {
        this.comments = List.copyOf(comments)
    }
}
