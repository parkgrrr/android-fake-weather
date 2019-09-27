package com.twitter.challenge.remote

import com.twitter.challenge.model.WeatherResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface WeatherService {

  @GET("current.json")
  fun getCurrentWeather() : Single<WeatherResponse>

  @GET("future_{day}.json")
  fun getFutureWeather(@Path(value = "day") day:Int) : Single<WeatherResponse>

  companion object {
    private val baseUrl = "https://twitter-code-challenge.s3.amazonaws.com/"

    fun create() : WeatherService {
      val retrofit = Retrofit.Builder()
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl(baseUrl)
          .build()
      return retrofit.create(WeatherService::class.java)
    }
  }
}