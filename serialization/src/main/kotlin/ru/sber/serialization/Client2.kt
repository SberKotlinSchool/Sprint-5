package ru.sber.serialization

import java.time.LocalDate
import com.fasterxml.jackson.annotation.JsonProperty

data class Client2(
    @JsonProperty("name")
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val passportNumber: String,
    val passportSerial: String,
    val birthDate: LocalDate
)
