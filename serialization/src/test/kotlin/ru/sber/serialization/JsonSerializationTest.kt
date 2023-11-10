package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonSerializationTest {

    @Test
    fun `Не должны сериализовываться свойства с null значениям Настройка через аннотацию`() {
        val client = Client5()
        val objectMapper = ObjectMapper()

        val data = objectMapper.writeValueAsString(client)

        assertEquals("{}", data)
    }

    @Test
    fun `Не должны сериализовываться свойства с null значениям Настройка через ObjectMapper`() {

        val client = Client6()
        val objectMapper = ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY)

        val data = objectMapper.writeValueAsString(client)

        assertEquals("{}", data)
    }
}
