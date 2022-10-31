package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class Client1(
    @JsonProperty("FIRSTNAME")
    val firstName: String,
    @JsonProperty("LASTNAME")
    val lastName: String,
    @JsonProperty("MIDDLENAME")
    val middleName: String?,
    @JsonProperty("PASSPORTNUMBER")
    val passportNumber: String,
    @JsonProperty("PASSPORTSERIAL")
    val passportSerial: String,
    @JsonProperty("BIRTHDATE")
    val birthDate: LocalDate
)
