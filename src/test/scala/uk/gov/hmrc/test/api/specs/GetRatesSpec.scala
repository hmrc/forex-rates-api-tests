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

      val dateFrom = TestUtils.getLastWeekdayAfter4pm
      val dateTo   = dateFrom.minusDays(3)

      val forexRate: ForexRate =
        forexRatesHelper.getForexRatesDateRange(
          TestUtils.dateTimeFormatter.format(dateFrom),
          TestUtils.dateTimeFormatter.format(dateTo),
          "EUR",
          "GBP"
        )

      Then("I am returned the matching exchange rates")

//      forexRate.date           shouldBe lastWeekday
//      forexRate.baseCurrency   shouldBe "EUR"
//      forexRate.targetCurrency shouldBe "GBP"

    }

  }
}
