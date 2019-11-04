package com.core.common.android.extensions

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

fun String.toLocalDateTime(vararg formatter: DateTimeFormatter): LocalDateTime? {
    formatter.forEach { dtf ->
        try {
            LocalDateTime.parse(this, dtf)?.also { return it }
        } catch (e: Exception) {
        }
    }
    return null
}