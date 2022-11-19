package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RoundingDeserializer : JsonDeserializer<Client7?>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): Client7 {
        val productNode = p.codec.readTree<JsonNode>(p)
        val (l, f, m) = productNode["client"].textValue().split(" ")
        return Client7(firstName = f, lastName = l, middleName = m)
    }
}

class JsonCustomClassDeserializer {

    @Test
    fun `Нобходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(SimpleModule().addDeserializer(Client7::class.java, RoundingDeserializer()))
        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}
