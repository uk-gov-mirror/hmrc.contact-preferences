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

package controllers.actions.mocks

import assets.BaseTestConstants.testVatNumber
import config.Constants
import connectors.mocks.MockAuthConnector
import controllers.actions.ContactPreferencesAuthorised
import uk.gov.hmrc.auth.core.authorise.Predicate
import uk.gov.hmrc.auth.core.retrieve.{Retrieval, Retrievals}
import uk.gov.hmrc.auth.core.{Enrolment, Enrolments}

trait MockContactPreferencesAuthorised extends MockAuthConnector {

  val mockContactPreferencesAuthorised = new ContactPreferencesAuthorised(mockAuthConnector)

  val vatAuthPredicate: Predicate = Enrolment(Constants.MtdContactPreferencesEnrolmentKey)
    .withIdentifier(Constants.MtdContactPreferencesReferenceKey, testVatNumber)
    .withDelegatedAuthRule(Constants.MtdContactPreferencesDelegatedAuth)

  val allEnrolments: Retrieval[Enrolments] = Retrievals.allEnrolments

}
