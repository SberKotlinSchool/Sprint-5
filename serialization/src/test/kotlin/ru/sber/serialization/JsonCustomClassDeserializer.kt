package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {

    @Test
    fun `Необходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val objectMapper = ObjectMapper().registerModules(KotlinModule().addDeserializer(Client7::class.java, FioSplitterDeserializer()))

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}

class FioSplitterDeserializer: JsonDeserializer<Client7>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): Client7 {
        val fioRegexp = Regex("([А-ЯЁ][а-яё]+[\\-\\s]?){3,}")
        val propertyException = UnrecognizedPropertyException.from(context, "Некорректный формат данных, ожидается поле 'client' с ФИО, разделенными пробелами")

        val node = parser.readValueAsTree<JsonNode>()
        val client = node?.get("client")?.textValue() ?: throw propertyException
        if (!fioRegexp.matches(client)) {
            throw propertyException
        }
        val fioArray = client.split(" ")

        return Client7(fioArray[1], fioArray[0], fioArray[2])
    }

}
