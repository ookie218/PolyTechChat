package com.ookie.polytechchat

//This is not simply a data class because FireBase needs an empty constructor to work with
class User {
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var uID: String? = null
    var idNumber: String? = null
    var department: String? = null
    var year: String? = null
    var role: String? = null //Only using if they are faculty


    //Set to -1, so it cannot be possible to reference another image
    val NO_IMAGE_PROVIDED = -1

    var mImageResourceID = NO_IMAGE_PROVIDED


    constructor() {

    }

    //If Student to test
    constructor(firstName: String?, email: String?, uID: String?) {
        this.firstName = firstName
        this.email = email
        this.uID = uID
    }

    //If Student
    constructor(firstName: String?, lastName: String?, email: String?, idNumber: String?, password: String?,
                role: String?, department: String?, year: String?, uID: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.idNumber = idNumber
        this.role = role
        this.department = department
        this.year = year
        this.uID = uID
    }

    //If Image
    constructor(firstName: String?, lastName: String?, email: String?, idNumber: String?,
                role: String?, department: String?, year: String?, imageResourceId: Int, uID: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.idNumber = idNumber
        this.role = role
        this.department = department
        this.year = year
        this.mImageResourceID = imageResourceId
        this.uID = uID
    }


}
