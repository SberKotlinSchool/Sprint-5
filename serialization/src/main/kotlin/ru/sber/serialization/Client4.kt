package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.util.Optional

data class Client4(
    val firstName: String,
    val lastName: String,
    @JsonProperty("middleName")
    val middleName: Optional<String>,
    val passportNumber: String,
    val passportSerial: String,
    val birthDate: LocalDate
)
