package com.rusyn.test_twitter.controller

import com.rusyn.test_twitter.dto.PostDto
import com.rusyn.test_twitter.service.FeedService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/feed")
class FeedController {

    private final FeedService feedService

    FeedController(FeedService feedService) {
        this.feedService = feedService
    }

    @GetMapping
    ResponseEntity<List<PostDto>> getFeed() {
        def posts = feedService.getFeed()
        ResponseEntity.ok(posts)
    }

    @GetMapping("/{userId}")
    ResponseEntity<List<PostDto>> getFeedById(@PathVariable("userId") String userId) {
        def posts = feedService.getFeed(userId)
        ResponseEntity.ok(posts)
    }
}