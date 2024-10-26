package com.rusyn.test_twitter.controller


import com.rusyn.test_twitter.dto.UserDto
import com.rusyn.test_twitter.dto.UserRequest
import com.rusyn.test_twitter.model.User
import com.rusyn.test_twitter.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Subject

class UserControllerSpec extends Specification{

    def userService = Mock(UserService)

    @Subject
    def userController = new UserController(userService)

    def "should return OK response with UserDto when user is successfully registered"() {
        given: "A valid user request"
        def userRequest = new UserRequest(username: "testUser", password: "testPass")

        and: "The userService returns a UserDto"
        def expectedUserDto = new UserDto(username: "testUser")
        userService.register(userRequest) >> expectedUserDto

        when: "The register method is called"
        ResponseEntity<UserDto> response = userController.register(userRequest)

        then: "The response status is OK"
        response.statusCode == HttpStatus.OK

        and: "The response body contains the UserDto"
        response.body == expectedUserDto
    }

    def "should return OK response with UserDto when user is successfully authenticated"() {
        given: "A valid user request"
        def userRequest = new UserRequest(username: "testUser", password: "testPass")

        and: "The userService returns an authenticated UserDto"
        def authenticatedUserDto = new UserDto(username: "testUser")
        userService.authenticate(userRequest) >> authenticatedUserDto

        when: "The login method is called"
        ResponseEntity<UserDto> response = userController.login(userRequest)

        then: "The response status is OK"
        response.statusCode == HttpStatus.OK

        and: "The response body contains the authenticated UserDto"
        response.body == authenticatedUserDto
    }

    def "should return OK response with UserDto when user is updated"() {
        given: "A valid user entity"
        def user = new User(username: "testUser", password: "newPassword")

        and: "The userService returns the updated UserDto"
        def updatedUserDto = new UserDto(username: "testUser")
        userService.updateUser(user.username, user.password) >> updatedUserDto

        when: "The updateUser method is called"
        ResponseEntity<UserDto> response = userController.updateUser(user)

        then: "The response status is OK"
        response.statusCode == HttpStatus.OK

        and: "The response body contains the updated UserDto"
        response.body == updatedUserDto
    }

    def "should return OK response with no body when followUser is called"() {
        given: "A valid user ID"
        def userId = "12345"

        when: "The follow method is called"
        ResponseEntity<Void> response = userController.follow(userId)

        then: "The response status is OK"
        response.statusCode == HttpStatus.OK

        and: "The userService followUser method is called with the correct user ID"
        1 * userService.followUser(userId)
    }

    def "should return OK response with UserDto when user is deleted"() {
        given: "A userService mock that returns a deleted UserDto"
        def deletedUserDto = new UserDto(username: "deletedUser")
        userService.deleteUser() >> deletedUserDto

        when: "The deleteUser method is called"
        ResponseEntity<UserDto> response = userController.deleteUser()

        then: "The response status is OK"
        response.statusCode == HttpStatus.OK

        and: "The response body contains the deleted UserDto"
        response.body == deletedUserDto
    }


}