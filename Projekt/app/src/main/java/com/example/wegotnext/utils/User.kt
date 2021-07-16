package com.example.wegotnext.utils

class User {
    var id: String? = ""
    var firstName: String? = ""
    var lastName: String? = ""
    var username: String? = ""


    constructor() {}

    constructor(id: String, firstname: String, lastname: String, username: String) {
        this.id = id
        this.firstName = firstname
        this.lastName = lastname
        this.username = username
    }

}

