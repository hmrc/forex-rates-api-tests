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

import java.time.LocalDate

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

      forexRates.size shouldBe TestUtils.getExpectedNumberOfRates(dateTo)

    }

    Scenario("Get Forex Rates for a date range not in the database") {

      Given("The RSS feed has been called today")

      val feedRetrieved = forexRatesHelper.triggerRssFeedRetrieval()
      feedRetrieved shouldBe true

      When("I try to retrieve a date range not in the database")

      val dateTo   = LocalDate.of(2021, 12, 25)
      val dateFrom = LocalDate.of(2021, 12, 26)

      val forexRates: Seq[ForexRate] =
        forexRatesHelper.getForexRatesDateRange(
          TestUtils.dateTimeFormatter.format(dateFrom),
          TestUtils.dateTimeFormatter.format(dateTo),
          "EUR",
          "GBP"
        )

      Then("I am returned a 200 with no data")

      forexRates.size shouldBe 0

    }

    Scenario("A 404 is received when trying to retrieve a rate for a weekend day") {

      Given("The RSS feed has been called today")

      val feedRetrieved = forexRatesHelper.triggerRssFeedRetrieval()
      feedRetrieved shouldBe true

      When("I call the last weekend date")

      val weekendDate = TestUtils.getWeekendDate(LocalDate.now())

      val response =
        forexRatesHelper.forexRatesAPI
          .getForexRatesSingleDate(TestUtils.dateTimeFormatter.format(weekendDate), "EUR", "GBP")

      Then("A 404 response code is returned")

      response.status shouldBe 404

    }

  }
}
