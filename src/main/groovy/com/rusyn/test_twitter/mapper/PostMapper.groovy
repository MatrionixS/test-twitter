package com.rusyn.test_twitter.mapper

import com.rusyn.test_twitter.dto.CommentDto
import com.rusyn.test_twitter.dto.PostDto
import com.rusyn.test_twitter.model.Comment
import com.rusyn.test_twitter.model.Post

class PostMapper {

     static PostDto toPostDto(Post post) {
        def comments = post.getComments().stream().map { toCommentDto(it) }.toList()
        def dto = new PostDto()
        dto.setId(post.getId())
        dto.setContent(post.getContent())
        dto.setComments(comments)
        dto.setLikes(post.getLikes())
        dto.setUserId(post.getUserId())
        dto.setCreatedAt(post.getCreatedAt())
        return dto
    }

      static CommentDto toCommentDto(Comment comment) {
        def commentDto = new CommentDto()
        commentDto.setContent(comment.getContent())
        commentDto.setId(comment.getId())
        return commentDto
    }
}
