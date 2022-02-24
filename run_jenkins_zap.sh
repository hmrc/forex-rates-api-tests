#!/usr/bin/env bash
sbt -Dzap.proxy=true -Denvironment=local 'testOnly uk.gov.hmrc.test.api.specs.ZAPSpec'