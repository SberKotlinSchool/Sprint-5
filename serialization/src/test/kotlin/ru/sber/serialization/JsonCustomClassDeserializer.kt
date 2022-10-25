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
        val objectMapper = ObjectMapper()
        val module = SimpleModule()
        module.addDeserializer(Client7::class.java, Client7Deserizlizer(Client7::class.java))
        objectMapper.registerModule(module)


        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}

class Client7Deserizlizer(vc: Class<*>?) : StdDeserializer<Client7>(vc) {
    override fun deserialize(jsonParser: JsonParser?, context: DeserializationContext?): Client7 {
        val jsonNode: JsonNode = jsonParser!!.codec.readTree(jsonParser)
        val client = jsonNode["client"].asText()
        val arrayOrParams = client.split(" ")
        return Client7(
            firstName = arrayOrParams[1],
            lastName = arrayOrParams[0],
            middleName = arrayOrParams[2]
        )
    }
}