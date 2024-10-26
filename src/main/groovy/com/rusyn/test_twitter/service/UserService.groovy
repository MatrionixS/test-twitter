package com.rusyn.test_twitter.service

import com.rusyn.test_twitter.dto.UserDto
import com.rusyn.test_twitter.dto.UserRequest
import com.rusyn.test_twitter.exceptions.NotAuthenticatedException
import com.rusyn.test_twitter.exceptions.UserAlreadyExistException
import com.rusyn.test_twitter.model.User
import com.rusyn.test_twitter.repository.PostRepository
import com.rusyn.test_twitter.repository.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Service

@Service
class UserService {

    private final HttpSession session
    private final UserRepository userRepository
    private final PostRepository postRepository

    UserService(UserRepository userRepository, HttpSession session, PostRepository postRepository) {
        this.userRepository = userRepository
        this.session = session
        this.postRepository = postRepository
    }


    void logout() {
        session.setAttribute("user", null)
    }

    UserDto register(UserRequest user) {
        def existingUser = userRepository.findByUsername(user.getUsername())
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistException("User with this username already exists")
        }

        def newUser = new User(username: user.getUsername(), password: user.getPassword())
        userRepository.save(newUser)
        toUserDto(newUser)
    }

    UserDto authenticate(UserRequest userRequest) {
        def notAuthenticatedUser = userRepository.findByUsername(userRequest.getUsername())
        notAuthenticatedUser
                .filter { checkUsernameAndPassword(it, userRequest) }
                .map {
                    session.setAttribute("user", it)
                    return toUserDto(it)
                }
                .orElseThrow { new NotAuthenticatedException("Incorrect username or password") }
    }

    UserDto updateUser(String username, String password) {
        def sessionUser = (User) session.getAttribute("user")
        userRepository.findByUsername(sessionUser.getUsername())
                .map {
                    it.setPassword(password)
                    it.setUsername(username)
                    userRepository.save(it)
                    return toUserDto(it)
                }.orElseThrow()
    }

    UserDto followUser(String userIdToFollow) {
        def sessionUser = (User) session.getAttribute("user")
        userRepository.findByUsername(sessionUser.getUsername())
                .map { user ->
                    userRepository.findById(userIdToFollow)
                            .map {followUserById(user, it)}.orElseThrow()
                }.orElseThrow()

    }

    private UserDto followUserById(User user, User userToFollow) {
        user.follow(userToFollow.getId())
        userRepository.save(user)
        toUserDto(user)
    }

    UserDto unfollowUser(String userId) {
        def sessionUser = (User) session.getAttribute("user")
        userRepository.findByUsername(sessionUser.getUsername())
                .map {
                    it.getFollowing().remove(userId)
                    userRepository.save(it)
                    toUserDto(it)
                }.orElseThrow()
    }

    UserDto deleteUser() {
        def sessionUser = (User) session.getAttribute("user")
        userRepository.findById(sessionUser.getId())
                .map {
                    userRepository.delete(it)
                    postRepository.deleteAllByUserId(it.getId())
                    toUserDto(it)
                }
                .orElseThrow()
    }

    User findById(String userId) {
        return userRepository.findById(userId).orElseThrow()

    }

    private static boolean checkUsernameAndPassword(User user, UserRequest userRequest) {
        return (user.getPassword() == userRequest.getPassword()) && (user.getUsername() == userRequest.getUsername())
    }

    private static UserDto toUserDto(User user) {
        def dto = new UserDto()
        dto.setId(user.getId())
        dto.setUsername(user.getUsername())
        dto.setFollowing(user.getFollowing())
        dto.setPosts(user.getPosts())
        return dto
    }


}