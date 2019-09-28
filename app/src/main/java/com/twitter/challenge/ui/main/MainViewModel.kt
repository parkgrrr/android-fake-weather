package com.twitter.challenge.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.twitter.challenge.StatsUtils.findStandardDeviation
import com.twitter.challenge.model.WeatherResponse
import com.twitter.challenge.remote.WeatherService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainViewModel : ViewModel() {

  private val weatherApi by lazy { WeatherService.create() }
  private val disposables = CompositeDisposable()
  private var weatherResponse : WeatherResponse? = null
  private var standardDev : Double? = null
  val status : MutableLiveData<Status> = MutableLiveData()

  fun init() {
    getCurrentWeather()
    checkForStandardDev()
  }

  private fun getCurrentWeather() {
    // if response is cached in VM, set status
    weatherResponse?.let {
      status.value = Status.Success(it)
    } ?: kotlin.run {
      // if response is null in VM, fetch from API
      fetchWeather()
    }
  }

  private fun checkForStandardDev() {
    // not ideal, but check if standard dev is caches in memory as well
    standardDev?.let {stdDev -> status.postValue(Status.ButtonSuccess(stdDev)) }
  }

  fun getStandardDeviation() {
    // if response is cached in VM, set status
    standardDev?.let {
      status.value = Status.ButtonSuccess(it)
    } ?: kotlin.run {
      // if response is null in VM, fetch from API
      fetch5DayWeather()
    }
  }

  private fun fetchWeather() {
    val disposable = weatherApi
        .getCurrentWeather()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe {status.value = Status.Loading() }
        .subscribe (
            {response -> status.value = Status.Success(response); weatherResponse = response },
            {error -> status.value = Status.Error(error.message ?: "")}
        )
    disposables.add(disposable)
  }

  private fun fetch5DayWeather() {
    val responses = mutableSetOf<WeatherResponse>()

    val disposable = Observable
        .mergeDelayError(
            weatherApi.getFutureWeather(1),
            weatherApi.getFutureWeather(2),
            weatherApi.getFutureWeather(3),
            weatherApi.getFutureWeather(4))
        .mergeWith(weatherApi.getFutureWeather(5))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread(), true)
        .doOnSubscribe {status.value = Status.Loading() }
        .subscribe (
            { response -> responses.add(response)},
            {error -> status.value = Status.Error(error.message ?: "")},
            { val tempSDev = getStandardDeviation(responses)
              status.value = Status.ButtonSuccess(tempSDev)
              standardDev = tempSDev
              }
        )

    disposables.add(disposable)
  }

  private fun getStandardDeviation(responses: MutableSet<WeatherResponse>) : Double {
    val doubleArray = DoubleArray(5)
    for (response in responses.withIndex()) {
      doubleArray[response.index] = response.value.weather.temp
    }
    return findStandardDeviation(doubleArray)
  }



  override fun onCleared() {
    super.onCleared()
    disposables.clear()
  }
}