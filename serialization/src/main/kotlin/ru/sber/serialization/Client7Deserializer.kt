package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode

class Client7Deserializer : JsonDeserializer<Client7>() {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): Client7 {
        val tree = parser.codec.readTree<JsonNode>(parser)
        val names = tree.get("client").textValue().split(" ")
        val lastName = names.getOrElse(0) { "" }
        val firstName = names.getOrElse(1) { "" }
        val middleName = names.getOrElse(2) { "" }

        return Client7(firstName, lastName, middleName)
    }


}