package com.ookie.polytechchat

//This is not simply a data class because FireBase needs an empty constructor to work with
class User {
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var uID: String? = null

    var facultyIDNumber: String? = null
    var idNumber: String? = null
    var department: String? = null
    var year: String? = null

    var student: Boolean? = null
    var facultyMember: Boolean? = null
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
    constructor(firstName: String?, lastName: String?, email: String?, idNumber: String?, year: String?, uID: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.idNumber = idNumber
        this.year = year
        this.uID = uID
    }


    //Use this constructor if there is an image available for Student
    constructor(firstName: String?, lastName: String?, email: String?, idNumber: String?, uID: String?, imageResourceID: Int) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.idNumber = idNumber
        //this.year = year
        this.uID = uID
        this.mImageResourceID = imageResourceID
    }



/*
    //If faculty
    constructor(firstName: String?, lastName: String?, email: String?, facultyIdNumber: String, department: String, uID: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.facultyIDNumber = facultyIdNumber
        this.department = department
        this.uID = uID
    }
*/

    //Use this constructor if there is an image available for faculty
    constructor(firstName: String?, lastName: String?, email: String?, facultyIdNumber: String, department: String, uID: String?, imageResourceID: Int) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.uID = uID
        this.mImageResourceID = imageResourceID
    }

    //Use this constructor if there is a title but no image available
    constructor(firstName: String?, lastName: String?, email: String?, uID: String?, role: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.uID = uID
        this.role = role
    }


    //Use this constructor if there is a title available
    constructor(firstName: String?, lastName: String?, email: String?, uID: String?, imageResourceID: Int, role: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.uID = uID
        this.mImageResourceID = imageResourceID
        this.role = role
    }

}
