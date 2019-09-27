package com.twitter.challenge.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.twitter.challenge.R
import com.twitter.challenge.TemperatureConverter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private lateinit var mainViewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    init()
  }

  private fun init() {
    mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    mainViewModel.status.observe(this, Observer { status -> observeStatus(status) } )
    mainViewModel.getCurrentWeather()
  }

  private fun observeStatus(status: Status) {
    when (status) {
      is Status.Success -> {
        val response = status.data
        val temp = getString(R.string.temperature, response.weather.temp, TemperatureConverter.celsiusToFahrenheit(response.weather.temp.toFloat()))
        temperature.text = temp
        wind_speed.text = response.wind.speed.toString()
      }
    }
  }

}
