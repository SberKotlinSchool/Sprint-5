package ru.sber.serialization
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = ClientDeserializer::class)
data class Client7(
    val firstName: String,
    val lastName: String,
    val middleName: String,
)

class ClientDeserializer: JsonDeserializer<Client7>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): Client7 {
        val node = parser.codec.readTree<JsonNode>(parser)
        val (lastName, firstName, middleName) = node.get("client").asText().split(" ")
        return Client7(firstName, lastName, middleName)
    }
}
