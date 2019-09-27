package com.twitter.challenge.ui.main

import com.twitter.challenge.model.WeatherResponse


sealed class Status {
  class Loading : Status()
  data class Error(val error: String) : Status()
  data class Success(val data : WeatherResponse) : Status()
  data class ButtonSuccess(val data : String) : Status()
}