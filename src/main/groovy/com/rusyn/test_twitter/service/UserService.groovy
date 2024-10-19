package com.rusyn.test_twitter.service

import com.rusyn.test_twitter.model.User
import com.rusyn.test_twitter.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserService {

    @Autowired
    private final UserRepository userRepository

    User register(String username, String password) {
        def user = new User(username: username, password: password)
        userRepository.save(user)
    }

    User findByUsername(String username) {
        userRepository.findByUsername(username)
    }

    void followUser(String currentUserId, String userIdToFollow) {
        def currentUser = userRepository.findById(currentUserId).get()
        currentUser.following.add(userIdToFollow)
        userRepository.save(currentUser)
    }
}