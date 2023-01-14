package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonProperty

class Client7(
        @JsonProperty("client")
        val firstName: String,
        @JsonProperty("client")
        val lastName: String,
        @JsonProperty("client")
        val middleName: String,
)
