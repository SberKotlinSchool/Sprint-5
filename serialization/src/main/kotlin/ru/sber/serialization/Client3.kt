package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class Client3(
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val passportNumber: String,
    val passportSerial: String,
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    val birthDate: LocalDate
)
