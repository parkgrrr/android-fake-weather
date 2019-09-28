package com.twitter.challenge

/**
 * This class returns the population sample standard deviation.
 * Breaking these methods out is overkill and not elegant, but I did it for the sake of testing.
 */
object StatsUtils {

  fun findStandardDeviation(tempArray: DoubleArray) : Double {
    val mean = findMean(tempArray)
    val valuesSquared = addAndSquare(tempArray, mean)
    return squareRootVales(valuesSquared, tempArray.size)
    }

  private fun findMean(tempArray: DoubleArray) : Double {
    return tempArray.average()
  }

  private fun addAndSquare(tempArray: DoubleArray, mean : Double) : Double {
    var valuesSquared = 0.0
    for (num in tempArray) {
      valuesSquared += Math.pow(num - mean, 2.0)
    }
    return valuesSquared
  }

  private fun squareRootVales(valuesSquared: Double, populationSize: Int) : Double {
    return Math.sqrt(valuesSquared / (populationSize -1))
  }
}