import play.core.PlayVersion.current
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  val compile = Seq(

    "uk.gov.hmrc"             %% "play-reactivemongo"       % "6.2.0",
    "uk.gov.hmrc"             %% "bootstrap-play-25"        % "4.7.0"
  )

  val test = Seq(
    "uk.gov.hmrc"             %% "hmrctest"                 % "3.4.0-play-25"         % "test",
    "org.scalatest"           %% "scalatest"                % "3.0.4"                 % "test",
    "com.typesafe.play"       %% "play-test"                % current                 % "test",
    "org.pegdown"             %  "pegdown"                  % "1.6.0"                 % "test, it",
    "uk.gov.hmrc"             %% "service-integration-test" % "0.2.0"                 % "test, it",
    "org.scalatestplus.play"  %% "scalatestplus-play"       % "2.0.0"                 % "test, it"
  )

}
