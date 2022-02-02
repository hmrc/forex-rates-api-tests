#!/bin/bash -e
ENV=${1:-local}

# Scalafmt checks have been separated from the test command to avoid OutOfMemoryError in Jenkins
sbt scalafmtCheckAll scalafmtSbtCheck

sbt -Denvironment=$ENV "testOnly uk.gov.hmrc.test.api.specs.*"