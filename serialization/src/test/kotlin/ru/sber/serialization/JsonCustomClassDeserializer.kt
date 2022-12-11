package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {
    @Test
    fun `Необходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val objectMapper = jacksonObjectMapper()
            .registerModule(
                SimpleModule()
                    .addDeserializer(Client7::class.java, object : JsonDeserializer<Client7>() {
                        override fun deserialize(parser: JsonParser, context: DeserializationContext) = parser
                            .readValueAsTree<JsonNode>()
                            .get("client").textValue()
                            .split(" ")
                            .let { Client7(lastName = it[0], firstName = it[1], middleName = it[2]) }
                    })
            )

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}