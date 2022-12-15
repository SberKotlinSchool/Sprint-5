package ru.sber.serialization


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class Client1(
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
