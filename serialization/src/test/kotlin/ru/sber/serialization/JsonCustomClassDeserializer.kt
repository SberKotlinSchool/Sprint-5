package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {

    @Test
    fun `Необходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""

        val objectMapper = ObjectMapper()
                .registerModules(
                        SimpleModule().addDeserializer(Client7::class.java, JacksonFioModule()))

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }

    /**
     * Кастомный дессериализатор ФИО одной строкой.
     * Формат JSON: "Фамилия Имя Отчество"
     */
    class JacksonFioModule : JsonDeserializer<Client7>() {

        override fun deserialize(parser: JsonParser?, context: DeserializationContext?): Client7 {
            val node = parser?.readValueAsTree<JsonNode>()
            val ex = UnrecognizedPropertyException.from(context, "Не найдено поле 'client' или формат данных некорректный")

            val client = node?.get("client")?.textValue() ?: throw ex

            val regex = """^[а-яА-Я]* [а-яА-Я]* [а-яА-Я]*$""".toRegex()
            if (!regex.containsMatchIn(client)) {
                throw ex
            }

            val splittedClient = client.split(" ")
            return Client7(firstName = splittedClient[1],
                    lastName = splittedClient[0],
                    middleName = splittedClient[2])
        }
    }
}
