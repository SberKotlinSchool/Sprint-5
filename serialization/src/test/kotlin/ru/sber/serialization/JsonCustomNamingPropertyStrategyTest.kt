package ru.sber.serialization

import java.util.*
import com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class JsonCustomNamingPropertyStrategyTest {

    // Кастомная стратегия десериализации
    class UppercaseStrategy : PropertyNamingStrategyBase() {
        override fun translate(input: String) = input.uppercase(Locale.getDefault())
    }

    @Test
    fun `Кастомная стратегия десериализации`() {
        // given
        val data =
            """{"FIRSTNAME": "Иван", "LASTNAME": "Иванов", "MIDDLENAME": "Иванович", "PASSPORTNUMBER": "123456", "PASSPORTSERIAL": "1234", "BIRTHDATE": "1990-01-01"}"""
        val objectMapper = ObjectMapper().registerModules(kotlinModule(), JavaTimeModule())
            .setPropertyNamingStrategy(UpperсaseStrategy())

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
