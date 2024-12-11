lazy val testSuite = (project in file("."))
  .disablePlugins(JUnitXmlReportPlugin) //Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .settings(
    name := "forex-rates-api-tests",
    version := "0.1.0",
    scalaVersion := "3.5.2",
    scalacOptions ++= Seq("-feature"),
    libraryDependencies ++= Dependencies.test
  )
