package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {

    @Test
    fun `Необходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val objectMapper = ObjectMapper()
            .registerModules(KotlinModule().addDeserializer(Client7::class.java, CustomDeserializer()))

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }

    private class CustomDeserializer : JsonDeserializer<Client7>() {

        override fun deserialize(parser: JsonParser?, context: DeserializationContext?): Client7 {
            try {
                val tree = parser!!.codec.readTree<JsonNode>(parser)
                val client: String? = tree?.get("client")?.textValue()
                val values = client?.split(" ")
                val lastName = values?.get(0)
                val firstName = values?.get(1)
                val middleName = values?.get(2)
                return Client7(firstName!!, lastName!!, middleName!!)
            } catch (e: Exception) {
                throw Exception("Ошибка при десериализации в класс Client7 ", e)
            }
        }
    }

}
