package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode

class CustomDeserializer : JsonDeserializer<Client7>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Client7 {
        val (lastName, firstName, middleName) = p.readValueAsTree<JsonNode>().get("client").asText().split(" ")
        return Client7(firstName, lastName, middleName)
    }
}