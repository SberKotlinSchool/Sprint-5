package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
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
        val objectMapper = ObjectMapper().registerModules(KotlinModule(), SimpleModule().let {
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

private class Client7Deserializer : StdDeserializer<Client7>(Client7::class.java) {
    override fun deserialize(parser: JsonParser?, context: DeserializationContext?): Client7 {
        data class ReadingDto(val client: String)
        val splitValues = parser!!.readValueAs(ReadingDto::class.java).client.split(" ")
        return Client7(splitValues[1], splitValues[0], splitValues[2])
    }
}
