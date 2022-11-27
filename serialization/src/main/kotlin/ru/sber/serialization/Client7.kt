package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

data class Client7(
    val firstName: String,
    val lastName: String,
    val middleName: String,
)

class Client7UnsafeDeserializer : StdDeserializer<Client7>(Client7::class.java) {

    override fun deserialize(parser: JsonParser?, context: DeserializationContext?): Client7 {
        requireNotNull(parser) { "Json parser can't be null." }
        requireNotNull(context) { "Deserialization context can't be null." }

        return parser.codec.readTree<JsonNode>(parser)
            .get(PROPERTY_NAME_CLIENT).asText()
            .split(PROPERTY_PARTS_DELIMITER)
            .let { (lastName, firstName, middleName) -> Client7(firstName, lastName, middleName) }
    }

    companion object {

        private const val PROPERTY_NAME_CLIENT = "client"
        private const val PROPERTY_PARTS_DELIMITER = " "
    }
}
