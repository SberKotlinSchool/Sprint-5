package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {

    class BestSeriesInSeries: JsonDeserializer<Client7>() {
        override fun deserialize(p0: JsonParser, p1: DeserializationContext?): Client7 {
            val bestClient = p0.codec.readTree<TreeNode>(p0)
                .get("client").toString().replace("\"","").split(" ")
            return Client7(lastName = bestClient[0], firstName = bestClient[1], middleName = bestClient[2])
        }

    }

    @Test
    fun `Нобходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val objectMapper = ObjectMapper()
            .registerModules(SimpleModule().addDeserializer(Client7::class.java , BestSeriesInSeries()))

        // when
        val client = objectMapper.readValue<Client7>(data)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}


