package ru.sber.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class JsonCustomNamingPropertyStrategyTest {

    @Test
    fun `Кастомная стратегия десериализации`() {
        val data =
            """{"FIRSTNAME": "Иван", "LASTNAME": "Иванов", "MIDDLENAME": "Иванович", "PASSPORTNUMBER": "123456", "PASSPORTSERIAL": "1234", "BIRTHDATE": "1990-01-01"}"""
        val objectMapper = ObjectMapper().registerModules(KotlinModule(), JavaTimeModule())
            .setPropertyNamingStrategy(UpperCaseStrategy())
        // или можно проперти - что будет быстрее:
        //.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)

        val client = objectMapper.readValue<Client1>(data)

        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
        assertEquals("123456", client.passportNumber)
        assertEquals("1234", client.passportSerial)
        assertEquals(LocalDate.of(1990, 1, 1), client.birthDate)
    }

    private class UpperCaseStrategy : PropertyNamingStrategies.NamingBase() {
        override fun translate(fieldValue: String?): String {
            return fieldValue!!.uppercase()
        }
    }
}
