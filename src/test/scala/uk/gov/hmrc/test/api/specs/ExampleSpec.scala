/*
 * Copyright 2021 HM Revenue & Customs
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

import uk.gov.hmrc.test.api.models.User
import uk.gov.hmrc.test.api.models.User._

class ExampleSpec extends BaseSpec {

  Feature("Example of using the Individuals Matching API") {

    Scenario("Get an individuals details by MatchId") {

      Given("There is an existing individual with a MatchId")
      val authBearerToken: String    = authHelper.getAuthBearerToken
      val individualsMatchId: String = testDataHelper.createAnIndividual(authBearerToken, ninoUser)

      When("I use that MatchId to retrieve the same individuals details")
      val actualUser: User =
        individualsMatchingHelper.getIndividualByMatchId(authBearerToken, individualsMatchId)

      Then("I am returned the individuals details")
      actualUser shouldBe ninoUser
    }

  }
}
