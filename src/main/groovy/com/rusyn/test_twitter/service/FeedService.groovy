package com.rusyn.test_twitter.service


import com.rusyn.test_twitter.dto.PostDto
import com.rusyn.test_twitter.mapper.PostMapper
import com.rusyn.test_twitter.model.User
import com.rusyn.test_twitter.repository.PostRepository
import com.rusyn.test_twitter.repository.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Service

@Service
class FeedService {

    private final HttpSession session
    private final UserRepository userRepository
    private final PostRepository postRepository

    FeedService(UserRepository userRepository, HttpSession session, PostRepository postRepository) {
        this.userRepository = userRepository
        this.session = session
        this.postRepository = postRepository
    }


    List<PostDto> getFeed() {
        def sessionUser = (User) session.getAttribute("user")
        getFeed(sessionUser.getId())
    }

    List<PostDto> getFeed(String userId) {
        def user = userRepository.findById(userId).orElseThrow()
        postRepository.findAllByUserIdIn(user.getFollowing()).stream()
                .map { PostMapper.toPostDto(it) }
                .toList()
    }


}