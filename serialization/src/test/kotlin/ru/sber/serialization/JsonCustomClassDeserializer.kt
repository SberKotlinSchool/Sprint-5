package ru.sber.serialization

import com.fasterxml.jackson.databind.InjectableValues
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class JsonCustomClassDeserializer {

    @Test
    fun `Нобходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""

        val objectMapper = ObjectMapper().registerModules(KotlinModule(), JavaTimeModule())

        val newData = objectMapper.readTree(data).get("client").asText().split(" ")

        val inject = InjectableValues.Std()
            .addValue("firstName", newData[1])
            .addValue("lastName", newData[0])
            .addValue("middleName", newData[2])

        // when
        val client = objectMapper.setInjectableValues(inject)
            .readValue<Client7>(data)
        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}
