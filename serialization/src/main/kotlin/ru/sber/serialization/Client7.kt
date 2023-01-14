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
    override fun deserialize(jsonParser: JsonParser, dc: DeserializationContext?): Client7 {
        val node = jsonParser.readValueAsTree<JsonNode>()
        val client = node["client"].asText().split(" ")
        return Client7(client[1], client[0], client[2])
    }
}
