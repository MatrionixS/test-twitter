package com.rusyn.test_twitter.service

import com.rusyn.test_twitter.model.Post
import com.rusyn.test_twitter.repository.PostRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class PostService {
    @Autowired
    private final PostRepository postRepository
    @Autowired
    UserService userService

    Post createPost(String userId, String content) {
        def post = new Post(userId: userId, content: content)
        postRepository.save(post)
    }

    List<Post> getFeed(String userId) {
        def user = userService.findById(userId)
        postRepository.findAllByUserIdIn(user.following)
    }

    void likePost(String postId, String userId) {
        def post = postRepository.findById(postId).get()
        post.likes.add(userId)
        postRepository.save(post)
    }

    void unlikePost(String postId, String userId) {
        def post = postRepository.findById(postId).get()
        post.likes.remove(userId)
        postRepository.save(post)
    }

    void addComment(String postId, String comment) {
        def post = postRepository.findById(postId).get()
        post.comments.add(comment)
        postRepository.save(post)
    }
}