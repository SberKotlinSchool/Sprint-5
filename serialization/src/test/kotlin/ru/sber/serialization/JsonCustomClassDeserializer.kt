package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import io.mockk.InternalPlatformDsl.toStr
import org.junit.jupiter.api.Test
import org.junit.platform.engine.support.hierarchical.Node
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {

    @Test
    fun `Нобходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val objectMapper = ObjectMapper().registerModules(kotlinModule(),
            SimpleModule().addDeserializer(Client7::class.java,  Client7Deserializer() ))

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}

class Client7Deserializer: JsonDeserializer<Client7>() {

    override fun deserialize(p0: JsonParser, p1: DeserializationContext): Client7 {
      val oNode : ObjectNode = p0.readValueAsTree()
        val (lastName, firstName, middleName) = oNode.get("client").asText().split(" ")
        return Client7(firstName, lastName, middleName)
    }
}