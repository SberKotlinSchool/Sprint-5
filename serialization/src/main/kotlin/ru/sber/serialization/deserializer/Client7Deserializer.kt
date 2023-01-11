package ru.sber.serialization.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import ru.sber.serialization.Client7


class Client7Deserializer : JsonDeserializer<Client7>() {
    override fun deserialize(jp: JsonParser?, ctx: DeserializationContext?): Client7 {
        val node: JsonNode? = jp?.codec?.readTree(jp)
        val str = node?.get("client")?.asText()
        val list = str?.split(" ")?.toList()

        return Client7(firstName = list?.get(1) ?: "", lastName = list?.get(0) ?: "", middleName = list?.get(2) ?: "")
    }
}