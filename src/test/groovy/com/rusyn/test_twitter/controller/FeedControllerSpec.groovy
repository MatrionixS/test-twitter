package com.rusyn.test_twitter.controller

import com.rusyn.test_twitter.dto.PostDto
import com.rusyn.test_twitter.service.FeedService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class FeedControllerSpec extends Specification {

    FeedService feedService = Mock()
    FeedController feedController = new FeedController(feedService)

    def "should return a list of posts when calling getFeed()"() {
        given: "the feed service returns a list of posts"
        def posts = [new PostDto("post1"), new PostDto("post2")]
        feedService.getFeed() >> posts

        when: "the controller's getFeed method is called"
        ResponseEntity<List<PostDto>> response = feedController.getFeed()

        then: "the service is called once and the response contains the correct posts"
        1 * feedService.getFeed() >> posts
        response.getBody() == posts
        response.getStatusCode() == HttpStatus.OK
    }

    def "should return a list of posts for a specific user when calling getFeedById()"() {
        given: "the feed service returns a list of posts for a user"
        def posts = [new PostDto("userPost1"), new PostDto("userPost2")]
        feedService.getFeed("12345") >> posts

        when: "the controller's getFeedById method is called"
        ResponseEntity<List<PostDto>> response = feedController.getFeedById("12345")

        then: "the service is called once with the userId and the response contains the correct posts"
        1 * feedService.getFeed("12345") >> posts
        response.getBody() == posts
        response.getStatusCode() == HttpStatus.OK
    }
}
