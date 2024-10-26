package com.rusyn.test_twitter.controller

import com.rusyn.test_twitter.dto.UserDto
import com.rusyn.test_twitter.dto.UserRequest
import com.rusyn.test_twitter.model.User
import com.rusyn.test_twitter.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController {

    private final UserService userService

    UserController(UserService userService) {
        this.userService = userService
    }

    @PostMapping("/login")
    ResponseEntity<UserDto> login(@RequestBody UserRequest user) {
        def authenticatedUser = userService.authenticate(user)
        ResponseEntity.ok(authenticatedUser)
    }

    @PostMapping("/logout")
    ResponseEntity<UserDto> logout() {
        userService.logout()
        ResponseEntity.ok().build()
    }

    @PostMapping("/register")
    ResponseEntity<UserDto> register(@RequestBody UserRequest user) {
        def result = userService.register(user)
        ResponseEntity.ok(result)
    }

    @PatchMapping("/update")
    ResponseEntity<UserDto> updateUser(@RequestBody User user) {
        def result = userService.updateUser(user.username, user.password)
        ResponseEntity.ok(result)
    }

    @PatchMapping("/{userId}/follow")
    ResponseEntity<Void> follow(@PathVariable("userId") String userId) {
        userService.followUser(userId)
        ResponseEntity.ok().build()
    }

    @PatchMapping("/{userId}/unfollow")
    ResponseEntity<Void> unfollow(@PathVariable("userId") String userId) {
        userService.unfollowUser(userId)
        ResponseEntity.ok().build()
    }

    @DeleteMapping("/delete")
    ResponseEntity<UserDto> deleteUser() {
        def userDto = userService.deleteUser()
        ResponseEntity.ok(userDto)
    }
}