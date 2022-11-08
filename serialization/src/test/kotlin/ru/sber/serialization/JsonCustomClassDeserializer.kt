package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {

    class Client7Deserializer : StdDeserializer<Client7>(Client7::class.java) {
        override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Client7 {
            val node : JsonNode = p!!.codec.readTree(p)!!
            val fio = node.get("client").asText().split(" ")
            return Client7(lastName = fio[0], firstName = fio[1], middleName = fio[2])
        }
    }

    @Test
    fun `Необходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val objectMapper = ObjectMapper()

        val module = SimpleModule()
        module.addDeserializer(Client7::class.java, Client7Deserializer())
        objectMapper.registerModule(module)

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}
