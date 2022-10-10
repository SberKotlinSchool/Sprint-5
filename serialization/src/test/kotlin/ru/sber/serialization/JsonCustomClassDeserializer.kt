package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.IOException
import kotlin.test.assertEquals


class JsonCustomClassDeserializer {


    class Client7Deserializer : JsonDeserializer<Client7?>() {
        @Throws(IOException::class)
        override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext?): Client7 {
            val oc = jsonParser.codec
            val node = oc.readTree<JsonNode>(jsonParser)
            val fio = node.get("client").textValue().split(" ")
            if (fio.size != 3) {
                throw JsonMappingException(jsonParser, "Невозможно десериализовать объект")
            }
            return Client7(lastName = fio[0], firstName = fio[1], middleName = fio[2])
        }
    }

    @Test
    fun `Необходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""

        val objectMapper = ObjectMapper()
            .registerModule(SimpleModule().addDeserializer(Client7::class.java, Client7Deserializer()))

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }


    @Test
    fun `Ошибка при десериализации данных в класс`() {
        // given
        val data = """{"client": "Иванов Иван"}"""

        val objectMapper = ObjectMapper()
            .registerModule(SimpleModule().addDeserializer(Client7::class.java, Client7Deserializer()))

        Assertions.assertThrows(JsonMappingException::class.java) { objectMapper.readValue<Client7>(data) }

    }

}

