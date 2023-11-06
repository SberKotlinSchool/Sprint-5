package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.ObjectCodec
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.DeserializationContext
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
        module.addDeserializer(Client7::class.java, CustomDeserializer(Client7::class.java))
        objectMapper.registerModule(module)

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }

    class CustomDeserializer(vc: Class<*>?): StdDeserializer<Client7>(vc){
        override fun deserialize(p0: JsonParser?, p1: DeserializationContext?): Client7 {
            val node = p0?.codec?.readTree<TreeNode>(p0)
            val client = node?.get("client").toString().replace("\"", "").split(" ")
            return Client7(firstName = client[1], lastName = client[0], middleName = client[2])
        }

    }
}
