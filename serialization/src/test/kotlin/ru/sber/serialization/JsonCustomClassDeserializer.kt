package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {

    @Test
    fun `Нобходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val objectMapper = ObjectMapper()
            .registerModules(KotlinModule(), SimpleModule().also {
                it.addDeserializer(Client7::class.java, Client7Deserializer())
            })

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}

class Client7Deserializer : JsonDeserializer<Client7>() {
    override fun deserialize(jsonParser: JsonParser, context: DeserializationContext): Client7 {
        val jsonNode = jsonParser.codec.readTree<JsonNode>(jsonParser)
        val fields = jsonNode.get("client").textValue().split(" ")
        val lastName = fields.getOrElse(0) { "" }
        val firstName = fields.getOrElse(1) { "" }
        val middleName = fields.getOrElse(2) { "" }

        return Client7(firstName, lastName, middleName)
    }
}
