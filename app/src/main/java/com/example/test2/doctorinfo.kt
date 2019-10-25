package com.example.test2

class doctorinfo{
    var name : String?=null
    var email : String?=null
    var speciality : String?=null
    var covern : String?=null
    var city : String?=null
    var price : String?=null
    var placetype : String?=null
    var dayLimits : drAvalibleDays ?=null

    constructor(name : String ,email : String,speciality : String,covern : String,city : String,placetype : String,price : String ,dayLimits : drAvalibleDays){
        this.name = name
        this.email = email
        this.speciality = speciality
        this.covern = covern
        this.city = city
        this.placetype = placetype
        this.price=price
        this.dayLimits =dayLimits
    }


}