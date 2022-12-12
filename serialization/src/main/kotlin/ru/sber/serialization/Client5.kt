package ru.sber.serialization

import java.time.LocalDate
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL

@JsonInclude(NON_NULL)
data class Client5(
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val passportNumber: String? = null,
    val passportSerial: String? = null,
    val birthDate: LocalDate? = null
)
