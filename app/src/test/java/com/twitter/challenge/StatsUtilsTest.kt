package com.twitter.challenge

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.assertj.core.data.Offset
import org.junit.Test

class StatsUtilsTest {

  private val precision: Offset<Double> = within(0.01)

  @Test
  fun testFindStandardDeviation() {
    var tempArray = DoubleArray(100)
    tempArray.fill(5.55)
    assertThat(StatsUtils.findStandardDeviation(tempArray)).isEqualTo(0.00, precision)

    tempArray = DoubleArray(2)
    tempArray.fill(5.55)
    assertThat(StatsUtils.findStandardDeviation(tempArray)).isEqualTo(0.00, precision)

    tempArray = doubleArrayOf(10.3, 20.45, 30.7, 40.2, 50.8)
    assertThat(StatsUtils.findStandardDeviation(tempArray)).isEqualTo(15.93, precision)

    tempArray = doubleArrayOf(27.64, 89.99, 63.33, 16.34, 57.66, 44.26, 74.55)
    assertThat(StatsUtils.findStandardDeviation(tempArray)).isEqualTo(25.895, precision)
  }

  @Test
  fun testSDWithNegatives() {
    var tempArray = doubleArrayOf(58.64, -74.99, 42.33, 12.34, -59.66, 96.26, -63.55)
    assertThat(StatsUtils.findStandardDeviation(tempArray)).isEqualTo(68.13, precision)

    tempArray = doubleArrayOf(-58.64, -74.99, -42.33, -12.34, -59.66, -96.26, -63.55)
    assertThat(StatsUtils.findStandardDeviation(tempArray)).isEqualTo(26.19, precision)
  }

  @Test
  fun testSDWithLargeNumbers() {
    var tempArray = doubleArrayOf(-5800.64, -7498.99, -42680.33, -120372.34, -59350.66, -96054.26, -63203.55)
    assertThat(StatsUtils.findStandardDeviation(tempArray)).isEqualTo(42543.59, precision)

    tempArray = doubleArrayOf(5800.64, 7498.99, 42680.33, 120372.34, 59350.66, 96054.26, 63203.55)
    assertThat(StatsUtils.findStandardDeviation(tempArray)).isEqualTo(42543.59, precision)

    tempArray = doubleArrayOf(5800.64, 74.99, 68.33, 3.34, 59350.66, 0.26, 3.55)
    assertThat(StatsUtils.findStandardDeviation(tempArray)).isEqualTo(22162.16, precision)

    tempArray = doubleArrayOf(5800.64, -74.99, 68.33, 3.34, -59350.66, -0.26, 3.55)
    assertThat(StatsUtils.findStandardDeviation(tempArray)).isEqualTo(22900.14, precision)
  }
}