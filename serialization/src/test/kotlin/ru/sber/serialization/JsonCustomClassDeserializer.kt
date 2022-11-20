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

    @Test
    fun `Нобходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val clientDeserializer = object : StdDeserializer<Client7>(Client7::class.java) {
            override fun deserialize(jp: JsonParser?, ctxt: DeserializationContext?): Client7 {
                val node: JsonNode = jp!!.codec.readTree(jp)!!
                val fio = node.get("client").asText().split(" ")
                val (lastName, firstName, middleName) = fio
                return Client7(firstName, lastName, middleName)
            }

        }

        val objectMapper = ObjectMapper()
            .registerModule(SimpleModule()
                .also { it.addDeserializer(Client7::class.java, clientDeserializer) })

        // when
        val client = objectMapper.readValue<Client7>(data)


        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}
