package com.twitter.challenge.remote

import com.twitter.challenge.BuildConfig
import com.twitter.challenge.model.WeatherResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


interface WeatherService {

  @GET("current.json")
  fun getCurrentWeather() : Single<WeatherResponse>

  @GET("future_{day}.json")
  fun getFutureWeather(@Path(value = "day") day:Int) : Observable<WeatherResponse>

  companion object {
    private val baseUrl = "https://twitter-code-challenge.s3.amazonaws.com/"

    fun create() : WeatherService {
      val logging = HttpLoggingInterceptor()

      if (BuildConfig.DEBUG) logging.level = HttpLoggingInterceptor.Level.BODY

      // Setting faster timeouts to test network failure
      val client = OkHttpClient.Builder()
          .addInterceptor(logging)
          .connectTimeout(5, TimeUnit.SECONDS)
          .writeTimeout(5, TimeUnit.SECONDS)
          .readTimeout(5, TimeUnit.SECONDS)
          .retryOnConnectionFailure(false)
          .build()

      val retrofit = Retrofit.Builder()
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create())
          .client(client)
          .baseUrl(baseUrl)
          .build()
      return retrofit.create(WeatherService::class.java)
    }
  }
}