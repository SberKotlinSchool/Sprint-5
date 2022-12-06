package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode


class DataDeserializer : JsonDeserializer<Client7>() {

    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext?): Client7 {
        val node = parser.codec.readTree<JsonNode>(parser)
        val fio = node.get("client").asText()
        val parts = fio.split(" ")
        return Client7(parts[1], parts[0], parts[2])
    }
}