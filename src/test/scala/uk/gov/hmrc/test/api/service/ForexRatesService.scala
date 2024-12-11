/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.test.api.service

import play.api.libs.ws.{DefaultWSProxyServer, StandaloneWSRequest}
import uk.gov.hmrc.test.api.client.HttpClient
import uk.gov.hmrc.test.api.conf.TestConfiguration
import uk.gov.hmrc.test.api.utils.Zap

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

class ForexRatesService extends HttpClient {
  val host: String              = TestConfiguration.url("forex-rates")
  val getRatesURL: String       = s"$host/rates"
  val triggerRssFeedURL: String = s"$host/test-only/retrieve-rates"

  def getForexRatesSingleDate(
    date: String,
    baseCurrency: String,
    targetCurrency: String
  ) =
    Await.result(
      getWithProxyIfEnabled(s"$getRatesURL/$date/$baseCurrency/$targetCurrency"),
      10.seconds
    )

  def getForexRatesDateRange(
    dateFrom: String,
    dateTo: String,
    baseCurrency: String,
    targetCurrency: String
  ) =
    Await.result(
      getWithProxyIfEnabled(s"$getRatesURL/$dateFrom/$dateTo/$baseCurrency/$targetCurrency"),
      10.seconds
    )

  def triggerRssFeedRetrieval() =
    Await.result(
      getWithProxyIfEnabled(triggerRssFeedURL),
      10.seconds
    )

  private def getWithProxyIfEnabled(
    url: String,
    headers: (String, String)*
  ) =
    if (Zap.enabled) {
      wsClient
        .url(url)
        .withHttpHeaders(headers: _*)
        .withProxyServer(DefaultWSProxyServer("localhost", 11000))
        .get()
    } else {
      get(url, headers: _*)
    }
}
