package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class Client2(
    @JsonAlias("name")
    @JsonProperty("firstName")
    val firstName: String,
    @JsonProperty("lastName")
    val lastName: String,
    @JsonProperty("middleName")
    val middleName: String?,
    @JsonProperty("passportNumber")
    val passportNumber: String,
    @JsonProperty("passportSerial")
    val passportSerial: String,
    @JsonProperty("birthDate")
    val birthDate: LocalDate
)
