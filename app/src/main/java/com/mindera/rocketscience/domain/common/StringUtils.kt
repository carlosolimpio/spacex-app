package com.mindera.rocketscience.domain.common

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit

const val UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSX"

fun String.convertToDate(): String {
    val date = LocalDateTime.parse(this, DateTimeFormatter.ofPattern(UTC_PATTERN))
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    return date.format(formatter)
}

fun String.convertToTime(): String {
    val time = LocalDateTime.parse(this, DateTimeFormatter.ofPattern(UTC_PATTERN))
    val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
    return time.format(formatter)
}

fun String.calculateDaysFromToday(): Long {
    val zonedDateTime = LocalDateTime.parse(this, DateTimeFormatter.ofPattern(UTC_PATTERN))
    val currentDate = ZonedDateTime.now(ZoneOffset.UTC)

    return ChronoUnit.DAYS.between(currentDate.toLocalDate(), zonedDateTime.toLocalDate())
}
