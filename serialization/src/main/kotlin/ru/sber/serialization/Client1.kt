package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class Client1(
    @JsonProperty("FIRSTNAME")
    @JsonAlias("firstName")
    val firstName: String,
    @JsonProperty("LASTNAME")
    @JsonAlias("lastName")
    val lastName: String,
    @JsonProperty("MIDDLENAME")
    @JsonAlias("middleName")
    val middleName: String?,
    @JsonProperty("PASSPORTNUMBER")
    @JsonAlias("passportNumber")
    val passportNumber: String,
    @JsonProperty("PASSPORTSERIAL")
    @JsonAlias("passportSerial")
    val passportSerial: String,
    @JsonProperty("BIRTHDATE")
    @JsonAlias("birthDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthDate: LocalDate
)
