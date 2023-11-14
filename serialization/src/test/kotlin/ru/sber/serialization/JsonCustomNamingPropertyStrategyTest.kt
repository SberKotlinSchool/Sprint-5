package ru.sber.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.cfg.MapperConfig
import com.fasterxml.jackson.databind.introspect.AnnotatedField
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
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
        val objectMapper = ObjectMapper().registerModules(kotlinModule(), JavaTimeModule())
        objectMapper.propertyNamingStrategy = object : PropertyNamingStrategy() {
            override fun nameForConstructorParameter(
                config: MapperConfig<*>?,
                ctorParam: AnnotatedParameter?,
                defaultName: String?
            ): String {
                return defaultName!!.uppercase()
            }
        }

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
