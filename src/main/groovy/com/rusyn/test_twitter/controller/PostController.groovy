package com.rusyn.test_twitter.controller

import com.rusyn.test_twitter.model.Post
import com.rusyn.test_twitter.service.PostService
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
class PostController {
    @Autowired
    private final PostService postService

    @PostMapping
    ResponseEntity<Post> createPost(@RequestBody Map<String, String> payload) {
        def post = postService.createPost(payload.userId, payload.content)
        ResponseEntity.ok(post)
    }

    @GetMapping("/feed/{userId}")
    ResponseEntity<List<Post>> getFeed(@PathVariable String userId) {
        def posts = postService.getFeed(userId)
        ResponseEntity.ok(posts)
    }

    @PostMapping("/{postId}/like")
    ResponseEntity<Void> likePost(@PathVariable String postId, @RequestBody Map<String, String> payload) {
        postService.likePost(postId, payload.userId)
        ResponseEntity.ok().build()
    }

    @PostMapping("/{postId}/unlike")
    ResponseEntity<Void> unlikePost(@PathVariable String postId, @RequestBody Map<String, String> payload) {
        postService.unlikePost(postId, payload.userId)
        ResponseEntity.ok().build()
    }

    @PostMapping("/{postId}/comment")
    ResponseEntity<Void> addComment(@PathVariable String postId, @RequestBody Map<String, String> payload) {
        postService.addComment(postId, payload.comment)
        ResponseEntity.ok().build()
    }
}
