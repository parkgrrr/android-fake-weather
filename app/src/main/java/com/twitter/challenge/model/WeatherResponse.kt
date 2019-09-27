package com.twitter.challenge.model

import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    val coord : Coord,
    val weather : Weather,
    val wind : Wind,
    val rain : Rain,
    val clouds : Clouds,
    val name : String
)

data class Coord (

    val lon : Double,
    val lat : Double
)

data class Weather (

    val temp : Double,
    val pressure : Int,
    val humidity : Int
)

data class Wind (

    val speed : Double,
    val deg : Int
)

data class Rain (

    @SerializedName("3h") val threeH : Int
)

data class Clouds (

    val cloudiness : Int
)