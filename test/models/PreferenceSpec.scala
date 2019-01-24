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

package models

import play.api.libs.json.{JsObject, Json}
import utils.TestUtils

class PreferenceSpec extends TestUtils {

  val digitalJson: JsObject = Json.obj("preference" -> Digital.value)
  val paperJson: JsObject = Json.obj("preference" -> Paper.value)
  val invalidJson: JsObject = Json.obj("preference" -> InvalidPreference.value)

  "Preference.apply" should {

    "when given a valid Preference" should {

      "for DIGITAL return Digital case object" in {
        Preference(Digital.value) shouldBe Digital
      }

      "for PAPER return Paper case object" in {
        Preference(Paper.value) shouldBe Paper
      }
    }

    "when given an invalid Preference" should {

      "for banana an InvalidPreference" in {
        Preference("banana") shouldBe InvalidPreference
      }
    }
  }

  "Preference.unapply" should {

    "when given a valid Preference" should {

      "for Digital case object return DIGITAL " in {
        Preference.unapply(Digital) shouldBe Digital.value
      }

      "for Paper case object return PAPER" in {
        Preference.unapply(Paper) shouldBe Paper.value
      }
    }

    "when given an invalid Preference" should {

      "for InvalidPreference should return INVALID" in {
        Preference.unapply(InvalidPreference) shouldBe InvalidPreference.value
      }
    }
  }

  "Preference.read" should {

    "when given a valid JSON document" should {

      "when given an valid Preference" should {

        "for DIGITAL return Digital case object" in {
          digitalJson.as[Preference] shouldBe Digital
        }

        "for PAPER return Paper case object" in {
          paperJson.as[Preference] shouldBe Paper
        }
      }

      "when given an invalid Preference" should {

        "for invalidJson return InvalidPreference case object" in {
          invalidJson.as[Preference] shouldBe InvalidPreference
        }
      }
    }

    "when given a invalid JSON document" should {

      "throw a JsResultException" in {
        val json = Json.obj("foo" -> "bar")
        json.validate[Preference].isError shouldBe true
      }
    }
  }

  "Preference.write" should {

    "when given an valid Preference" should {

      "for DIGITAL return Digital case object" in {
        Json.toJson(Digital) shouldBe digitalJson
      }

      "for PAPER return Paper case object" in {
        Json.toJson(Paper) shouldBe paperJson
      }
    }

    "when given an invalid Preference" should {

      "for invalidJson return InvalidPreference case object" in {
        Json.toJson(InvalidPreference) shouldBe invalidJson
      }
    }
  }
}
