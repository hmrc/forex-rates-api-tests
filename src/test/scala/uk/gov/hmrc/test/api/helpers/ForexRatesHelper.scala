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

package uk.gov.hmrc.test.api.helpers

import play.api.libs.json.Json
import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.test.api.models.{ForexRate, User}
import uk.gov.hmrc.test.api.service.{ForexRatesService, IndividualsMatchingService}
import uk.gov.hmrc.test.api.utils.ApiLogger._

class ForexRatesHelper {

  val forexRatesAPI: ForexRatesService = new ForexRatesService

  def getForexRates(date: String, baseCurrency: String, targetCurrency: String): ForexRate = {
    val response: StandaloneWSRequest#Self#Response =
      forexRatesAPI.getForexRates(date, baseCurrency, targetCurrency)
    log.warn(s"Response was status ${response.status} with body ${response.body}")
    Json.parse(response.body).as[ForexRate]
  }

  def triggerRssFeedRetrieval(): Boolean = {
    val response = forexRatesAPI.triggerRssFeedRetrieval()

    if (response.status == 200) {
      true
    } else {
      log.error(
        s"Unexpected status when calling trigger RSS feed retrieval. Status ${response.status} ${response.body}"
      )
      false
    }
  }

}
