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

package uk.gov.hmrc.test.api.service

import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.test.api.client.HttpClient
import uk.gov.hmrc.test.api.conf.TestConfiguration

import scala.concurrent.Await
import scala.concurrent.duration._

class AuthService extends HttpClient {

  val host: String        = TestConfiguration.url("auth")
  val authPayload: String =
    s"""
       |{
       |  "clientId": "id-123232",
       |  "authProvider": "PrivilegedApplication",
       |  "applicationId":"app-1",
       |  "applicationName": "App 1",
       |  "enrolments": ["read:individuals-matching",
       |  "read:individuals-income",
       |  "read:individuals-income-sa",
       |  "read:individuals-income-sa-additional-information",
       |  "read:individuals-income-sa-employments",
       |  "read:individuals-income-sa-foreign",
       |  "read:individuals-income-sa-interests-and-dividends",
       |  "read:individuals-income-sa-partnerships",
       |  "read:individuals-income-sa-pensions-and-state-benefits",
       |  "read:individuals-income-sa-other",
       |  "read:individuals-income-sa-self-employments",
       |  "read:individuals-income-sa-source",
       |  "read:individuals-income-sa-summary",
       |  "read:individuals-income-sa-trusts",
       |  "read:individuals-income-sa-uk-properties",
       |  "read:individuals-income-paye",
       |  "read:individuals-employments",
       |  "read:individuals-employments-paye"],
       |  "ttl": 5000
       |}
     """.stripMargin

  def postLogin: StandaloneWSRequest#Self#Response = {
    val url = s"$host/application/session/login"
    Await.result(
      post(url, authPayload, ("Content-Type", "application/json")),
      10.seconds
    )
  }

}
