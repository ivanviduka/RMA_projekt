package com.example.wegotnext.utils

class Court {
    var city: String? = null
    var lat: Double? = null
    var lng: Double? = null

    constructor() {}

    constructor(city: String, lat: Double, lon: Double) {
        this.city = city
        this.lat = lat
        this.lng = lon
    }
}