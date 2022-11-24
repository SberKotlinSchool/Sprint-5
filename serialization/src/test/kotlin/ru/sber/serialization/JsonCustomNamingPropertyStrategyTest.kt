package ru.sber.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class JsonCustomNamingPropertyStrategyTest {

    @Test
    fun `Кастомная стратегия десериализации`() {
        // given
        val data =
            """{"FIRSTNAME": "Иван", "LASTNAME": "Иванов", "MIDDLENAME": "Иванович", "PASSPORTNUMBER": "123456", "PASSPORTSERIAL": "1234", "BIRTHDATE": "1990-01-01"}"""
        val objectMapper = ObjectMapper()
        val upperCaseStrategy = object : PropertyNamingStrategies.NamingBase() {
            override fun translate(p: String): String = p.uppercase()
        }
        objectMapper
            .registerModules(JavaTimeModule(), KotlinModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .propertyNamingStrategy = upperCaseStrategy
        // when
        val client = objectMapper.readValue<Client1>(data)
        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
        assertEquals("123456", client.passportNumber)
        assertEquals("1234", client.passportSerial)
        assertEquals(LocalDate.of(1990, 1, 1), client.birthDate)
    }
}
