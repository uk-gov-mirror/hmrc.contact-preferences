/*
 * Copyright 2020 HM Revenue & Customs
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

package repositories.mocks

import org.mockito.ArgumentMatchers
import org.mockito.Mockito._
import org.mockito.stubbing.OngoingStubbing
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.mockito.MockitoSugar
import reactivemongo.api.commands.{UpdateWriteResult, Upserted, WriteError, WriteResult}
import repositories.JourneyRepository
import repositories.documents.JourneyDocument
import utils.TestUtils

import scala.concurrent.Future


trait MockJourneyRepository extends TestUtils with MockitoSugar with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockJourneyRepository)
  }

  lazy val mockJourneyRepository: JourneyRepository = mock[JourneyRepository]

  def setupMockFindJourneyById(response: Future[Option[JourneyDocument]]): OngoingStubbing[Future[Option[JourneyDocument]]] = {
    when(mockJourneyRepository.findById(ArgumentMatchers.anyString(),ArgumentMatchers.any())(ArgumentMatchers.any()))
      .thenReturn(response)
  }

  def setupMockFailedFindJourneyById(response: Option[JourneyDocument]): OngoingStubbing[Future[Option[JourneyDocument]]] = {
    when(mockJourneyRepository.findById(ArgumentMatchers.anyString(),ArgumentMatchers.any())(ArgumentMatchers.any()))
      .thenReturn(Future.failed(new Exception))
  }

  def setupMockInsertJourney(data: JourneyDocument)(response: Future[UpdateWriteResult]): OngoingStubbing[Future[WriteResult]] = {
    when(mockJourneyRepository.insert(ArgumentMatchers.eq(data))(ArgumentMatchers.any()))
      .thenReturn(response)
  }

  def setupMockFailedInsertJourney(data: JourneyDocument): OngoingStubbing[Future[WriteResult]] = {
    when(mockJourneyRepository.insert(ArgumentMatchers.eq(data))(ArgumentMatchers.any()))
      .thenReturn(Future.failed(new Exception))
  }

}
