package com.ookie.polytechchat

class Message {
    var message: String? = null
    var senderID: String? = null
    var photoResource: Int? = null
    var photoUrl: String? = null

    //Empty Constructor
    constructor() {}

    constructor(message: String?, senderId: String?) {
        this.message = message
        this.senderID = senderId
    }

    //Constructor if there is a picture
    constructor(message: String?, senderId: String?, photoUrl: String?) {
        this.message = message
        this.senderID = senderId
        this.photoUrl = photoUrl
    }

}