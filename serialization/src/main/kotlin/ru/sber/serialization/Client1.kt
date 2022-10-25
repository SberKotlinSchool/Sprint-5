package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class Client1(
    @JsonProperty("firstName")
    @JsonAlias("FIRSTNAME")
    val firstName: String,
    @JsonProperty("lastName")
    @JsonAlias("LASTNAME")
    val lastName: String,
    @JsonProperty("middleName")
    @JsonAlias("MIDDLENAME")
    val middleName: String?,
    @JsonProperty("passportNumber")
    @JsonAlias("PASSPORTNUMBER")
    val passportNumber: String,
    @JsonProperty("passportSerial")
    @JsonAlias("PASSPORTSERIAL")
    val passportSerial: String,
    @JsonProperty("birthDate")
    @JsonAlias("BIRTHDATE")
    val birthDate: LocalDate
)
