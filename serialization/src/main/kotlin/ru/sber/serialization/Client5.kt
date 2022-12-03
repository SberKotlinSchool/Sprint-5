package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.time.LocalDate

@JsonInclude(NON_NULL)
data class Client5(
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val passportNumber: String? = null,
    val passportSerial: String? = null,
    @JsonFormat(pattern = "dd-MM-yyyy")
    val birthDate: LocalDate? = null
)
