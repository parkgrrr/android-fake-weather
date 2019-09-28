package com.twitter.challenge

/**
 * This class returns the population sample standard deviation.
 */
object StatsUtils {

  fun findStandardDeviation(tempArray: DoubleArray): Double {
    val mean = tempArray.average()
    val valuesSquared = addAndSquare(tempArray, mean)
    return Math.sqrt(valuesSquared / (tempArray.size - 1))
  }

  private fun addAndSquare(tempArray: DoubleArray, mean: Double): Double {
    var valuesSquared = 0.0
    for (num in tempArray) {
      valuesSquared += Math.pow(num - mean, 2.0)
    }
    return valuesSquared
  }

}