package com.example.wegotnext.model


class Game {
        var id : String? = null
        var city: String? = null
        var court: String? = null
        var createdBy: String? = null
        var time: String? = null
        var gameType: String? = null
        var playersNeeded: Long? = null
        var playersComing: Long? = null

    constructor() {}

    constructor( city: String, court: String, createdBy: String, time: String, gameType: String,
                 playersNeeded: Long, playersComing: Long) {

        this.city = city
        this.court = court
        this.createdBy = createdBy
        this.time = time
        this.gameType = gameType
        this.playersNeeded = playersNeeded
        this.playersComing = playersComing
    }

}
