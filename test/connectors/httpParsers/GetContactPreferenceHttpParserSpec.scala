/*
 * Copyright 2019 HM Revenue & Customs
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

package connectors.httpParsers

import assets.ContactPreferencesTestConstants._
import connectors.httpParsers.GetContactPreferenceHttpParser.{GetContactPreferenceHttpReads, _}
import play.api.http.Status
import play.api.libs.json.Json
import uk.gov.hmrc.http.HttpResponse
import utils.TestUtils

class GetContactPreferenceHttpParserSpec extends TestUtils {

  "GetContactPreferenceHttpParser.GetContactPreferenceHttpReads" when {

    "given an OK with a correct Json model" should {

      "return a Right containing the correct contact preference moodel" in {
        GetContactPreferenceHttpReads.read("", "", HttpResponse(Status.OK, Some(paperPreferenceDesJson))) shouldBe Right(paperPreferenceModel)
      }
    }

    "given an OK with incorrect Json" should {

      "return a Left(ErrorMessage)" in {
        GetContactPreferenceHttpReads.read("", "", HttpResponse(Status.OK, Some(Json.obj("bad" -> "data")))) shouldBe
          Left(InvalidJson)
      }
    }

    "given an FORBIDDEN with message 'MIGRATION' response" should {

      "return a Left(Migration)" in {
        GetContactPreferenceHttpReads.read("", "", HttpResponse(Status.FORBIDDEN, responseString = Some("MIGRATION"))) shouldBe
          Left(Migration)
      }
    }

    "given an SERVICE_UNAVAILABLE response" should {

      "return a Left(ErrorMessage)" in {
        GetContactPreferenceHttpReads.read("", "", HttpResponse(Status.SERVICE_UNAVAILABLE)) shouldBe
          Left(DependentSystemUnavailable)
      }
    }

    "given any other status" should {

      "return a Left(UnexpectedFailure)" in {
        GetContactPreferenceHttpReads.read("", "", HttpResponse(Status.BAD_GATEWAY)) shouldBe
          Left(UnexpectedFailure(Status.BAD_GATEWAY, s"Status ${Status.BAD_GATEWAY} Error returned when retrieving contact preference"))
      }
    }
  }
}