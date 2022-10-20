package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize


@JsonDeserialize(using = Client7Deserialize::class)
data class Client7(
    val firstName: String?,
    val lastName: String?,
    val middleName: String?,
)

object Client7Deserialize : JsonDeserializer<Client7>() {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): Client7 {
        val tree = parser.codec.readTree<JsonNode>(parser)
        val names = tree.get("client").textValue().split(" ")
        val lastName = names.getOrNull(0)
        val firstName = names.getOrNull(1)
        val middleName = names.getOrNull(2)

        return Client7(firstName, lastName, middleName)
    }
}