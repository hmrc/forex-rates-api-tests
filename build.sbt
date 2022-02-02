lazy val testSuite = (project in file("."))
  .enablePlugins(SbtAutoBuildPlugin)
  .disablePlugins(JUnitXmlReportPlugin) //Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .settings(
    name := "forex-rates-api-tests",
    version := "0.1.0",
    scalaVersion := "2.12.12",
    scalacOptions ++= Seq("-feature"),
    libraryDependencies ++= Dependencies.test
  )
