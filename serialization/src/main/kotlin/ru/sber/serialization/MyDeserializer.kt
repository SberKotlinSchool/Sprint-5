package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode

class MyDeserializer : JsonDeserializer<Client7>() {

    override fun deserialize(p0: JsonParser?, p1: DeserializationContext?): Client7 {
        val node : JsonNode = p0!!.readValueAsTree()
        val names : List<String> = node.get("client").textValue().split(" ")
        return Client7(names.get(1), names.get(0), names.get(2))
    }
}