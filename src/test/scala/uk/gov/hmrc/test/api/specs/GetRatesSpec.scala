/*
 * Copyright 2022 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.test.api.specs

import uk.gov.hmrc.test.api.models.ForexRate
import uk.gov.hmrc.test.api.utils.TestUtils

import java.time.{DayOfWeek, LocalDate}

class GetRatesSpec extends BaseSpec {

  Feature("Retrieving Forex Rates") {

    Scenario("Get Forex Rates for a single specified date") {

      Given("The RSS feed has been called today")

      val feedRetrieved = forexRatesHelper.triggerRssFeedRetrieval()
      feedRetrieved shouldBe true

      When("I call the last weekday")

      val lastWeekday          = TestUtils.getLastWeekdayAfter4pm
      val forexRate: ForexRate =
        forexRatesHelper.getForexRatesSingleDate(TestUtils.dateTimeFormatter.format(lastWeekday), "EUR", "GBP")

      Then("I am returned an exchange rate")

      forexRate.date           shouldBe lastWeekday
      forexRate.baseCurrency   shouldBe "EUR"
      forexRate.targetCurrency shouldBe "GBP"

    }

    Scenario("Get Forex Rates for a specified date range") {

      Given("The RSS feed has been called today")

      val feedRetrieved = forexRatesHelper.triggerRssFeedRetrieval()
      feedRetrieved shouldBe true

      When("I retrieve a date range")

      val dateTo   = TestUtils.getLastWeekdayAfter4pm
      val dateFrom = dateTo.minusDays(3)

      val forexRates: Seq[ForexRate] =
        forexRatesHelper.getForexRatesDateRange(
          TestUtils.dateTimeFormatter.format(dateFrom),
          TestUtils.dateTimeFormatter.format(dateTo),
          "EUR",
          "GBP"
        )

      Then("I am returned the matching exchange rates")

      // TODO if we add more tests, put this in a test util
      val expectedNumberOfRates = dateTo.getDayOfWeek match {
        case DayOfWeek.MONDAY    => 2
        case DayOfWeek.TUESDAY   => 2
        case DayOfWeek.WEDNESDAY => 3
        case DayOfWeek.THURSDAY  => 4
        case DayOfWeek.FRIDAY    => 4
      }

      forexRates.size shouldBe expectedNumberOfRates
    }

    Scenario("A 404 is received when trying to retrieve a rate for a weekend day") {

      Given("The RSS feed has been called today")

      val feedRetrieved = forexRatesHelper.triggerRssFeedRetrieval()
      feedRetrieved shouldBe true

      When("I call the last weekend date")

      val weekendDate = LocalDate.now().getDayOfWeek match {
        case DayOfWeek.MONDAY    => LocalDate.now().minusDays(1)
        case DayOfWeek.TUESDAY   => LocalDate.now().minusDays(2)
        case DayOfWeek.WEDNESDAY => LocalDate.now().minusDays(3)
        case DayOfWeek.THURSDAY  => LocalDate.now().minusDays(4)
        case DayOfWeek.FRIDAY    => LocalDate.now().minusDays(5)
        case _                   => LocalDate.now()
      }

      val responseCode: Int =
        forexRatesHelper.getForexRatesWeekendDate(TestUtils.dateTimeFormatter.format(weekendDate), "EUR", "GBP")

      Then("A 404 response code is returned")

      responseCode shouldBe 404

    }

  }
}
