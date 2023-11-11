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
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Client7 {
        val node = p.codec.readTree<JsonNode>(p)
        val fullName = node.get("client").asText()
        val parts = fullName.split(" ")
        return Client7(parts[1], parts[0], parts[2])
    }
}
