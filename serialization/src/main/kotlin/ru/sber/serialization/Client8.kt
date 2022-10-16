package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

/**
 * Для теста с аннотацией
 */

@JsonIgnoreProperties(ignoreUnknown = true)
data class Client8(
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val passportNumber: String,
    val passportSerial: String,
    val birthDate: LocalDate
)
