package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {

    @Test
    fun `Нобходимо десериализовать данные в класс`() {

        class CustomDeserializer: StdDeserializer<Client7>(Client7::class.java){
            override fun deserialize(p0: JsonParser?, p1: DeserializationContext?): Client7 {
                val jsonNode: JsonNode? = p0?.codec?.readTree(p0)
                val client = jsonNode?.get("client")?.textValue() ?: ""


                val clientFio = client
                    .trim()
                    .replace(Regex("[\\s]{2,}"), " ")
                    .split(" ")

                return Client7(
                    lastName = clientFio[0],
                    firstName = clientFio[1],
                    middleName = clientFio[2]
                )
            }

        }
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val objectMapper = ObjectMapper()
            .registerModule(KotlinModule())
            .registerModule(
                SimpleModule("CustomModule").addDeserializer(
                    Client7::class.java,
                    CustomDeserializer()
                )
            )

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}
