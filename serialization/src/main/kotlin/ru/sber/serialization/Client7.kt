package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize


@JsonDeserialize(using = Client7Deserializer::class)
data class Client7(
    val firstName: String,
    val lastName: String,
    val middleName: String,
)

private object Client7Deserializer : JsonDeserializer<Client7>() {
    override fun deserialize(jsonParser: JsonParser, p1: DeserializationContext): Client7 {
        val client = jsonParser.readValueAsTree<JsonNode>().get("client").toString().trim('\"')
        val (lastName, firstName, middleName) = client.split(" ")

        return Client7(firstName, lastName, middleName)
    }

}