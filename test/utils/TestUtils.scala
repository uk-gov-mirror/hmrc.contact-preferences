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

package utils

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import config.AppConfig
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.HeaderNames
import play.api.inject.Injector
import play.api.mvc.{AnyContentAsEmpty, Result}
import play.api.test.FakeRequest
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.test.UnitSpec

import scala.concurrent.ExecutionContext

trait TestUtils extends UnitSpec with GuiceOneAppPerSuite with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    super.beforeEach()
    appConfig.features.bypassAuth(false)
  }

  implicit lazy val fakeRequest: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/")
  lazy val injector: Injector = app.injector
  implicit lazy val appConfig: AppConfig = injector.instanceOf[AppConfig]
  implicit lazy val executionContext: ExecutionContext = injector.instanceOf[ExecutionContext]
  implicit lazy val headerCarrier: HeaderCarrier = HeaderCarrier()
  implicit lazy val system: ActorSystem = ActorSystem("Sys")
  implicit lazy val materializer: ActorMaterializer = ActorMaterializer()

  def redirectLocation(result: Result): Option[String] = result.header.headers.get(HeaderNames.LOCATION)

}
