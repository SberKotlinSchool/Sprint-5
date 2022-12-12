package ru.sber.serialization

import java.time.LocalDate
import com.fasterxml.jackson.annotation.JsonFormat

data class Client3(
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val passportNumber: String,
    val passportSerial: String,

    @JsonFormat(pattern = "dd-MM-yyyy")
    val birthDate: LocalDate
)
