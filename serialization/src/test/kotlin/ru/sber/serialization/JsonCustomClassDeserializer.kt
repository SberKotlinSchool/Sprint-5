package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {

    @Test
    fun `Нобходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val objectMapper = ObjectMapper().registerModules(
            kotlinModule(),
            SimpleModule().apply { addDeserializer(Client7::class.java, CustomClient7Deserializer()) })

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}

@Suppress("VulnerableCodeUsages")
private class CustomClient7Deserializer : StdDeserializer<Client7>(Client7::class.java) {
    override fun deserialize(jsonParser: JsonParser?, context: DeserializationContext?): Client7 {
        val clientSplit = jsonParser!!.readValueAs(Client7JsonDto::class.java).client.split(" ")
        return Client7(
            firstName = clientSplit[1],
            lastName = clientSplit[0],
            middleName = clientSplit[2]
        )
    }
}

private class Client7JsonDto(
    val client: String
)
