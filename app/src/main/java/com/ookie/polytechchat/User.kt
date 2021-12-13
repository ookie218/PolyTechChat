package com.ookie.polytechchat

//This is not simply a data class because FireBase needs an empty constructor to work with
class User {
    var name: String? = null
    var email: String? = null
    var uID: String? = null

    constructor() {

    }

    constructor(name: String?, email: String?, uID: String?) {
        this.name = name
        this.email = email
        this.uID = uID
    }

}