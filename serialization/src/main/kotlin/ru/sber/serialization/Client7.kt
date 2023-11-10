package ru.sber.serialization

import com.fasterxml.jackson.annotation.JacksonInject
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder( "firstName", "lastName", "middleName" )
@JsonIgnoreProperties(ignoreUnknown = true)
data class Client7(
    @JacksonInject("firstName")
    val firstName: String,
    @JacksonInject("lastName")
    val lastName: String,
    @JacksonInject("middleName")
    val middleName: String,
)
