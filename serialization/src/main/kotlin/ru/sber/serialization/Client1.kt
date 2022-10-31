package ru.sber.serialization

import java.time.LocalDate

data class Client1(

    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val passportNumber: String,
    val passportSerial: String,
    val birthDate: LocalDate
)
