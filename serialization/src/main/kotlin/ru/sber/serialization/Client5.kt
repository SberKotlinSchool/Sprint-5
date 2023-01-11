package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.LocalDate

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
data class Client5(
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val passportNumber: String? = null,
    val passportSerial: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd") val birthDate: LocalDate? = null
)
