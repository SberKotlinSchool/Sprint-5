package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = [JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES])
data class Client1(
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val passportNumber: String,
    val passportSerial: String,
    val birthDate: LocalDate
)
