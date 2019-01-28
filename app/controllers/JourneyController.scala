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

package controllers

import config.AppConfig
import javax.inject.{Inject, Singleton}
import models.JourneyModel
import play.api.Logger
import play.api.http.HeaderNames
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent}
import repositories.JourneyRepository
import repositories.documents.{DateDocument, JourneyDocument}
import services.{DateService, UUIDService}
import uk.gov.hmrc.play.bootstrap.controller.BaseController

import scala.concurrent.ExecutionContext

@Singleton
class JourneyController @Inject()(journeyRepository: JourneyRepository,
                                  appConfig: AppConfig,
                                  uuidService: UUIDService,
                                  dateService: DateService)(implicit ec: ExecutionContext) extends BaseController {

  val storeJourney: Action[JsValue] = Action.async(parse.json) {
    implicit request => withJsonBody[JourneyModel](
      journey => {
        val journeyId = uuidService.generateUUID
        val journeyDocument = JourneyDocument(journeyId, journey, DateDocument(dateService.timestamp))
        Logger.debug(s"[JourneyController][storeJourney] JourneyModel: $journey")
        Logger.debug(s"[JourneyController][storeJourney] JourneyDocument: $journeyDocument")
        journeyRepository.insert(journeyDocument).map {
          case result if result.ok =>
            Logger.debug(s"[JourneyController][storeJourney] Header Location Redirect: $appConfig.contactPreferencesUrl}/$journeyId")
            Created.withHeaders(HeaderNames.LOCATION -> s"${appConfig.contactPreferencesUrl}/$journeyId")
          case err =>
            Logger.debug(s"[JourneyController][storeJourney] Mongo Errors: ${err.writeErrors.map(_.errmsg)}")
            InternalServerError("failed")
        }.recover {
          case e =>
            Logger.debug(s"[JourneyController][storeJourney] Errors: ${e.getMessage}")
            InternalServerError("failed")
        }
      }
    )
  }

  val findJourney: String => Action[AnyContent] = id => Action.async {
    implicit request =>
      Logger.debug(s"[JourneyController][findJourney] JourneyID: $id")
      journeyRepository.findById(id).map {
        case Some(journeyDocument) =>
          Logger.debug(s"[JourneyController][findJourney] Found Journey: \n\n${Json.toJson(journeyDocument)}")
          Ok(Json.toJson(journeyDocument.journey))
        case _ => NotFound("not found")
      }.recover {
        case e =>
          Logger.debug(s"[JourneyController][findJourney] Errors: ${e.getMessage}")
          InternalServerError("failed")
      }
  }
}