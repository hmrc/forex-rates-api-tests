/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.test.api.utils

import java.time.format.DateTimeFormatter
import java.time.{DayOfWeek, LocalDate, LocalDateTime, ZoneId}
import java.util.Locale

object TestUtils {

  def getLastWeekdayAfter4pm: LocalDate = {

    val today = LocalDateTime.now()

    val lastDayAfter4pm = if (today.getHour < 17) {
      today.minusDays(1)
    } else {
      today
    }

    val lastWeekday = if (lastDayAfter4pm.getDayOfWeek == DayOfWeek.SUNDAY) {
      lastDayAfter4pm.minusDays(2)
    } else if (lastDayAfter4pm.getDayOfWeek == DayOfWeek.SATURDAY) {
      lastDayAfter4pm.minusDays(1)
    } else {
      lastDayAfter4pm
    }

    lastWeekday.toLocalDate
  }

  def getExpectedNumberOfRates(dateTo: LocalDate): Int =
    dateTo.getDayOfWeek match {
      case DayOfWeek.MONDAY    => 2
      case DayOfWeek.TUESDAY   => 2
      case DayOfWeek.WEDNESDAY => 3
      case DayOfWeek.THURSDAY  => 4
      case DayOfWeek.FRIDAY    => 4
      case DayOfWeek.SATURDAY  => 3
      case DayOfWeek.SUNDAY    => 2
    }

  def getWeekendDate(weekendDate: LocalDate): LocalDate =
    weekendDate.getDayOfWeek match {
      case DayOfWeek.MONDAY    => LocalDate.now().minusDays(1)
      case DayOfWeek.TUESDAY   => LocalDate.now().minusDays(2)
      case DayOfWeek.WEDNESDAY => LocalDate.now().minusDays(3)
      case DayOfWeek.THURSDAY  => LocalDate.now().minusDays(4)
      case DayOfWeek.FRIDAY    => LocalDate.now().minusDays(5)
      case _                   => LocalDate.now()
    }

  val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter
    .ofPattern("yyyy-MM-dd")
    .withLocale(Locale.UK)
    .withZone(ZoneId.systemDefault())

}
