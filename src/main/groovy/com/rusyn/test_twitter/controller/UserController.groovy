package com.rusyn.test_twitter.controller

import com.rusyn.test_twitter.model.User
import com.rusyn.test_twitter.service.UserService
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController {
    UserController(UserService userService) {
        this.userService = userService
    }
    @Autowired
    private final UserService userService

    @PostMapping("/register")
    ResponseEntity<User> register(@RequestBody User user) {
        def result = userService.register(user.username, user.password)
        ResponseEntity.ok(result)
    }

    @PostMapping("/{id}/follow")
    ResponseEntity<Void> follow(@PathVariable String id, @RequestBody Map<String, String> payload) {
        userService.followUser(id, payload.userIdToFollow)
        ResponseEntity.ok().build()
    }
}