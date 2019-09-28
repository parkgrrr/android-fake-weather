package com.twitter.challenge

fun Double.celsiusToFahrenheit(): Double {
  return this * 1.8 + 32
}

fun Double.truncateWeather(): String {
  val stringFormat = "%.2f"
  return stringFormat.format(this)
}
