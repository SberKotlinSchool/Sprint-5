package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = Client7Deserializer::class)
data class Client7(
    val firstName: String,
    val lastName: String,
    val middleName: String,
)

class Client7Deserializer : JsonDeserializer<Client7>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): Client7 {
        val node = parser.codec.readTree<JsonNode>(parser)
        val values = node.get("client").textValue().split(' ', limit = 3)
        val lastName = values.getOrElse(0) { "" }
        val firstName = values.getOrElse(1) { "" }
        val middleName = values.getOrElse(2) { "" }
        return Client7(firstName, lastName, middleName)
    }
}

