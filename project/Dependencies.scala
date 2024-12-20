import sbt._

object Dependencies {

  val test = Seq(
    "org.scalatest"       %% "scalatest"               % "3.2.19" % Test,
    "com.vladsch.flexmark" % "flexmark-all"            % "0.64.8" % Test,
    "com.typesafe"         % "config"                  % "1.4.3"  % Test,
    "com.typesafe.play"   %% "play-ahc-ws-standalone"  % "2.2.9" % Test,
    "org.slf4j"            % "slf4j-simple"            % "2.0.13" % Test,
    "com.typesafe.play"   %% "play-ws-standalone-json" % "2.2.9" % Test
  )
}
