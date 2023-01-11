package ru.sber.serialization

import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize
data class Client7(
    val firstName: String,
    val lastName: String,
    val middleName: String,
)
