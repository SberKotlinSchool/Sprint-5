package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonSerializationTest {

    @Test
    fun `Не должны сериализовываться свойства с null значениям Настройка через аннотацию`() {
        // given
        val client = Client5()
        val objectMapper = ObjectMapper()

        // when
        val data = objectMapper.writeValueAsString(client)

        // then
        assertEquals("{}", data)
    }

    @Test
    fun `Не должны сериализовываться свойства с null значениям Настройка через ObjectMapper`() {
        // given
        val client = Client6()
        val objectMapper = ObjectMapper()
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)

        // when
        val data = objectMapper.writeValueAsString(client)

        // then
        assertEquals("{}", data)
    }
}
