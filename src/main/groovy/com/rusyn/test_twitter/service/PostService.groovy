package com.rusyn.test_twitter.service

import com.rusyn.test_twitter.dto.CommentDto
import com.rusyn.test_twitter.dto.PostDto
import com.rusyn.test_twitter.dto.PostRequest
import com.rusyn.test_twitter.mapper.PostMapper
import com.rusyn.test_twitter.model.Comment
import com.rusyn.test_twitter.model.Post
import com.rusyn.test_twitter.model.User
import com.rusyn.test_twitter.repository.PostRepository
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService {

    private final PostRepository postRepository
    private final UserService userService
    private final HttpSession session

    PostService(PostRepository postRepository, UserService userService, HttpSession session) {
        this.postRepository = postRepository
        this.userService = userService
        this.session = session
    }

    PostDto createPost(PostRequest postRequest) {
        def sessionUser = (User) session.getAttribute("user")
        def post = new Post(userId: sessionUser.getId(), content: postRequest.getContent())
        def savedPost = postRepository.save(post)
        PostMapper.toPostDto(savedPost)
    }



    void likePost(String postId) {
        postRepository.findById(postId).map {
            it.like()
            postRepository.save(it)
        }orElseThrow()
    }

    void unlikePost(String postId) {
        postRepository.findById(postId).map {
            it.unlike()
            postRepository.save(it)
        }.orElseThrow()
    }

    PostDto addComment(String postId, CommentDto commentDto) {
        postRepository.findById(postId).map {
            def comment = toComment(commentDto)
            it.addComment(comment)
            postRepository.save(it)
            PostMapper.toPostDto(it)
        }.orElseThrow()
    }

    private Comment toComment(CommentDto commentDto) {
        return new Comment(commentDto.getContent())
    }

    PostDto updatePost(String postId, PostRequest postRequest) {
        postRepository.findById(postId).map {
            it.setContent(postRequest.getContent())
            postRepository.save(it)
            PostMapper.toPostDto(it)
        }.orElseThrow()
    }

    PostDto deletePost(String postId) {
        postRepository.findById(postId).map {
            postRepository.deleteById(it.getId())
            PostMapper.toPostDto(it)
        }.orElseThrow()
    }

     List<CommentDto> getComments(String postId) {
         postRepository.findById(postId).map {
             it.getComments().stream().map {PostMapper.toCommentDto(it)}.toList()
         }.orElseThrow()
    }
}