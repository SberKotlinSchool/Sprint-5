package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonProperty

data class Client7(
    @JsonProperty("firstName")
    val firstName: String,
    @JsonProperty("lastName")
    val lastName: String,
    @JsonProperty("middleName")
    val middleName: String,
)
