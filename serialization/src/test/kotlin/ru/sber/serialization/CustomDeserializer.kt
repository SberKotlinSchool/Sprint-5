package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

class CustomDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Client7>(vc) {

    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext?): Client7 {
        val node = jp.codec.readTree<JsonNode>(jp)
        val (lastName, firstName, middleName) = node["client"].asText().split(" ")
        return Client7(firstName, lastName, middleName)
    }
}