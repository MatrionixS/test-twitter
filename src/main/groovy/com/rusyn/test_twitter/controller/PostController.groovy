package com.rusyn.test_twitter.controller

import com.rusyn.test_twitter.dto.CommentDto
import com.rusyn.test_twitter.dto.PostDto
import com.rusyn.test_twitter.dto.PostRequest
import com.rusyn.test_twitter.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController {

    private final PostService postService

    PostController(PostService postService) {
        this.postService = postService
    }

    @PostMapping
    ResponseEntity<PostDto> createPost(@RequestBody PostRequest postDto) {
        def post = postService.createPost(postDto)
        ResponseEntity.ok(post)
    }

    @PatchMapping("/{postId}/update")
    ResponseEntity<PostDto> updatePost(@PathVariable("postId") String postId, @RequestBody PostRequest postDto) {
        def updatedPost = postService.updatePost(postId, postDto)
        ResponseEntity.ok().body(updatedPost)
    }

    @DeleteMapping("/{postId}")
    ResponseEntity<PostDto> deletePost(@PathVariable("postId") String postId) {
        def updatedPost = postService.deletePost(postId)
        ResponseEntity.ok().body(updatedPost)
    }

    @PatchMapping("/{postId}/comment")
    ResponseEntity<PostDto> addComment(@PathVariable("postId") String postId, @RequestBody CommentDto commentDto) {
        def postDto = postService.addComment(postId, commentDto)
        ResponseEntity.ok().body(postDto)
    }

    @PatchMapping("/{postId}/like")
    ResponseEntity<Void> likePost(@PathVariable("postId") String postId) {
        postService.likePost(postId)
        ResponseEntity.ok().build()
    }

    @PatchMapping("/{postId}/unlike")
    ResponseEntity<Void> unlikePost(@PathVariable("postId") String postId) {
        postService.unlikePost(postId)
        ResponseEntity.ok().build()
    }

    @GetMapping("/{postId}/comments")
    ResponseEntity<List<CommentDto>> getComments(@PathVariable("postId") String postId) {
        def comments = postService.getComments(postId)
        ResponseEntity.ok().body(comments)
    }
}
