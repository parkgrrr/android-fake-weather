package com.twitter.challenge

import org.assertj.core.api.Java6Assertions.assertThat
import org.assertj.core.api.Java6Assertions.within
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class TemperatureConverterTests {
  @Test
  fun testCelsiusToFahrenheitConversion() {
    val precision = within(0.01)

    assertThat((-50.00).celsiusToFahrenheit()).isEqualTo(-58.00, precision)
    assertThat(0.00.celsiusToFahrenheit()).isEqualTo(32.00, precision)
    assertThat(10.00.celsiusToFahrenheit()).isEqualTo(50.00, precision)
    assertThat(21.11.celsiusToFahrenheit()).isEqualTo(70.00, precision)
    assertThat(37.78.celsiusToFahrenheit()).isEqualTo(100.00, precision)
    assertThat(100.00.celsiusToFahrenheit()).isEqualTo(212.00, precision)
    assertThat(1000.00.celsiusToFahrenheit()).isEqualTo(1832.00, precision)
  }
}
