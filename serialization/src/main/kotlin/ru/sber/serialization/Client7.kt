package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = Deserializer::class)
data class Client7(
    val firstName: String,
    val lastName: String,
    val middleName: String,
)

object Deserializer : JsonDeserializer<Client7>() {
    override fun deserialize(parser: JsonParser?, ctxt: DeserializationContext?): Client7 {
        val names = parser?.readValueAsTree<JsonNode>()?.get("client")?.asText()?.split(' ')
            ?: throw NullPointerException("Wrong Json")
        return Client7(
            names.getOrNull(1).orEmpty(),
            names.getOrNull(0).orEmpty(),
            names.getOrNull(2).orEmpty()
        )
    }
}
