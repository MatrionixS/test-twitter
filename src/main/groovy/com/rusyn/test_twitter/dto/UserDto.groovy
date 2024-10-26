package com.rusyn.test_twitter.dto

import com.rusyn.test_twitter.model.Post

class UserDto {
    private String id
    private String username

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }
    Set<String> following
    Set<Post> posts

    void setFollowing(Set<String> following) {
        this.following = Set.copyOf(following)
    }

    void setPosts(Set<Post> posts) {
        this.posts = Set.copyOf(posts)
    }
}
