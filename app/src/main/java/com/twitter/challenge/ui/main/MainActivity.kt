package com.twitter.challenge.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.twitter.challenge.R
import com.twitter.challenge.truncateWeather
import com.twitter.challenge.celsiusToFahrenheit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    init()
  }

  private fun init() {
    viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    viewModel.status.observe(this, Observer { status -> observeStatus(status) } )
    viewModel.init()
    deviation_button.setOnClickListener { viewModel.getStandardDeviation() }
  }

  private fun observeStatus(status: Status) {
    when (status) {

      is Status.Success -> { updateWeather(status) }
      is Status.Loading -> { progress_bar.visibility = View.VISIBLE}
      is Status.Error -> {
        progress_bar.visibility = View.GONE
        showSnackbar(activity_main, status.error)
      }
      is Status.ButtonSuccess -> {
        progress_bar.visibility = View.GONE
        deviation.text = getString(R.string.temperature, status.data.truncateWeather(), status.data.celsiusToFahrenheit().truncateWeather())
      }
    }
  }

  private fun updateWeather(status : Status.Success) {
    progress_bar.visibility = View.GONE
    val response = status.data
    val weather = getString(R.string.wind_speed, response.wind.speed.truncateWeather())
    val temp = getString(R.string.temperature, response.weather.temp.truncateWeather(), response.weather.temp.celsiusToFahrenheit().truncateWeather())
    temperature.text = temp
    wind_speed.text = weather
    if (status.data.clouds.cloudiness > 50) cloud_icon.visibility = View.VISIBLE
  }

  private fun showSnackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
  }

}
