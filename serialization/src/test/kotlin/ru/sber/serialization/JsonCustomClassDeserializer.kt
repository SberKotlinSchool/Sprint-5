package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {
    class ClientDeserializer : JsonDeserializer<Client7>() {
        override fun deserialize(parser: JsonParser, ctxt: DeserializationContext?): Client7 {
            val objectCodec = parser.codec
            val node = objectCodec.readTree<JsonNode>(parser)
            val fioParts = node.get("client").textValue().split(" ")

            if (fioParts.size < 3) throw JsonParseException(parser, "Не удалось разобрать ФИО клиента")
            return Client7(firstName = fioParts[1], lastName = fioParts[0], middleName = fioParts[2])
        }
    }

    @Test
    fun `Необходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val objectMapper = ObjectMapper()
            .registerModule(SimpleModule().addDeserializer(Client7::class.java, ClientDeserializer()))

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}
