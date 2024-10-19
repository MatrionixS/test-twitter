package com.rusyn.test_twitter.controller

import com.rusyn.test_twitter.model.User
import com.rusyn.test_twitter.service.UserService
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerSpec extends Specification {

    @Subject
    UserController userController

    UserService userService = Mock(UserService)

    void setup() {
        userController = new UserController(userService)
    }

    def "should register a user successfully"() {
        given:
        def user = new User(username: "testuser", password: "password123")
        def registeredUser = new User(username: "testuser", password: "hashedPassword")

        userService.register(user.username, user.password) >> registeredUser

        when:
        ResponseEntity<User> response = userController.register(user)

        then:
        response.statusCode == HttpStatus.OK
        response.body == null
        1 * userService.register(user.username, user.password)
    }

    def "should handle registration failure"() {
        given:
        def user = new User(username: "testuser", password: "password123")

        userService.register(user.username, user.password) >> { throw new RuntimeException("Registration failed") }

        when:
        ResponseEntity<User> response = userController.register(user)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR // Adjust according to your error handling
        1 * userService.register(user.username, user.password)
    }
}