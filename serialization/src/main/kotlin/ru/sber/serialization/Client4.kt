package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.util.Optional

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class Client4(
    val firstName: String,
    val lastName: String,
    @JsonProperty("middleName")
    val middleName: Optional<String>,
    val passportNumber: String,
    val passportSerial: String,
    val birthDate: LocalDate
)
