package com.rusyn.test_twitter.model

class Comment {
    String id
    String content

    Comment() {
    }

    Comment(String content) {
        this.id = UUID.randomUUID().toString()
        this.content = content
    }


}
