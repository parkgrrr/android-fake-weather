package com.twitter.challenge.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.twitter.challenge.remote.WeatherService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainViewModel : ViewModel() {

  private val weatherApi by lazy { WeatherService.create() }
  private val disposables = CompositeDisposable()
  val status : MutableLiveData<Status> = MutableLiveData()

  fun getCurrentWeather() {
    val disposable = weatherApi
        .getCurrentWeather()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe {status.value = Status.Loading() }
        .subscribe (
            {response -> status.value = Status.Success(response) },
            {error -> status.value = Status.Error(error.message ?: "There was an error")}
        )

    disposables.add(disposable)

  }

  override fun onCleared() {
    super.onCleared()
    disposables.clear()
  }
}