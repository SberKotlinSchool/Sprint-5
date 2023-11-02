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
    override fun deserialize(jp: JsonParser, context: DeserializationContext): Client7 {
        val node = jp.readValueAsTree<JsonNode>()
        val client = node.get("client").asText()
        val (lastName, firstName, middleName) = client.split(" ")
        return Client7(firstName, lastName, middleName)
    }
}