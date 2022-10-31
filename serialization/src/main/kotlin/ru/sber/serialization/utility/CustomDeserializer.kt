package ru.sber.serialization.utility

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import ru.sber.serialization.Client7

class CustomDeserializer: JsonDeserializer<Client7>() {
    override fun deserialize(p0: JsonParser?, p1: DeserializationContext?): Client7 {
        val jsonParameters = p0?.readValueAsTree<JsonNode>()?.get("client").toString().split(" ").toTypedArray()

        return Client7(
            firstName = jsonParameters[1],
            lastName = jsonParameters[0].trim('"'),
            middleName = jsonParameters[2].trim('"'))
    }

}