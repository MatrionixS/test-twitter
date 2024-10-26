package com.rusyn.test_twitter.controller

import com.rusyn.test_twitter.dto.CommentDto
import com.rusyn.test_twitter.dto.PostDto
import com.rusyn.test_twitter.dto.PostRequest
import com.rusyn.test_twitter.service.PostService
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Subject

class PostControllerSpec extends Specification{


    def postService = Mock(PostService)

    @Subject
    def postController = new PostController(postService)

    def "createPost should call createPost on postService and return PostDto"() {
        given:
        def postRequest = new PostRequest(content: "Sample post")
        def postDto = new PostDto(id: "1", content: "Sample post")
        postService.createPost(postRequest) >> postDto

        when:
        ResponseEntity<PostDto> response = postController.createPost(postRequest)

        then:
        response.statusCode.value() == 200
        response.body == postDto
    }

    def "updatePost should call updatePost on postService and return updated PostDto"() {
        given:
        def postRequest = new PostRequest(content: "Updated content")
        def postDto = new PostDto(id: "1", content: "Updated content")
        postService.updatePost("1", postRequest) >> postDto

        when:
        ResponseEntity<PostDto> response = postController.updatePost("1", postRequest)

        then:
        response.statusCode.value() == 200
        response.body == postDto
    }

    def "deletePost should call deletePost on postService and return PostDto"() {
        given:
        def postDto = new PostDto(id: "1", content: "Deleted post")
        postService.deletePost("1") >> postDto

        when:
        ResponseEntity<PostDto> response = postController.deletePost("1")

        then:
        response.statusCode.value() == 200
        response.body == postDto
    }

    def "addComment should call addComment on postService and return updated PostDto"() {
        given:
        def commentDto = new CommentDto(content: "Nice post!")
        def postDto = new PostDto(id: "1", content: "Sample post with comment")
        postService.addComment("1", commentDto) >> postDto

        when:
        ResponseEntity<PostDto> response = postController.addComment("1", commentDto)

        then:
        response.statusCode.value() == 200
        response.body == postDto
    }

    def "likePost should call likePost on postService and return 200 OK"() {
        when:
        ResponseEntity<Void> response = postController.likePost("1")

        then:
        1 * postService.likePost("1")
        response.statusCode.value() == 200
    }

    def "unlikePost should call unlikePost on postService and return 200 OK"() {
        when:
        ResponseEntity<Void> response = postController.unlikePost("1")

        then:
        1 * postService.unlikePost("1")
        response.statusCode.value() == 200
    }

    def "getComments should call getComments on postService and return list of CommentDto"() {
        given:
        def comments = [new CommentDto(content: "First comment"), new CommentDto(content: "Second comment")]
        postService.getComments("1") >> comments

        when:
        ResponseEntity<List<CommentDto>> response = postController.getComments("1")

        then:
        response.statusCode.value() == 200
        response.body == comments
    }
}
