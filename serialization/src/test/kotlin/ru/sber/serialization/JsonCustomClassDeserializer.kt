package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {

    @Test
    fun `Нобходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val objectMapper = ObjectMapper().registerModule(KotlinModule()).registerModule(SimpleModule().also {
            it.addDeserializer(Client7::class.java, object : JsonDeserializer<Client7>() {
                override fun deserialize(parser: JsonParser, dzc: DeserializationContext?): Client7 {
                    val node = parser.codec.readTree<JsonNode>(parser)
                    val (lastName, firstName, middleName) = node["client"].asText().split(" ")
                    return Client7(firstName, lastName, middleName)
                }
            })
        })

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}
