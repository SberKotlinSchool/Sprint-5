package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

private class SingleLineDeserializer: JsonDeserializer<Client7>() {
    private val fieldName = "client"

    override fun deserialize(p0: JsonParser?, p1: DeserializationContext?): Client7 {
        val node = p0?.readValueAsTree<JsonNode>()
        val result = node?.get(fieldName)
                .toString()
                .filter { it.isLetter() || it.isWhitespace() }
                .split(" ")

        return Client7(
                firstName = result[1],
                lastName = result[0],
                middleName = result[2]
        )
    }
}

@JsonDeserialize(using = SingleLineDeserializer::class)
data class Client7(
    val firstName: String,
    val lastName: String,
    val middleName: String,
)
