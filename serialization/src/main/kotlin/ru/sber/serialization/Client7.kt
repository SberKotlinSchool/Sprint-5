package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = Client7JsonDeserializer::class)
data class Client7(
    val firstName: String,
    val lastName: String,
    val middleName: String,
)

object Client7JsonDeserializer : JsonDeserializer<Client7>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Client7 {
        if (p == null)
            throw NullPointerException("No value to parse!")
        val jsonClient = p.readValueAsTree<JsonNode>().get("client")
            ?: throw NullPointerException("No field \"client\" to parse in JSON string")

        val names = jsonClient.asText().split(' ')
        return Client7(
            names.getOrNull(1).orEmpty(),
            names.getOrNull(0).orEmpty(),
            names.getOrNull(2).orEmpty()
        )
    }
}
