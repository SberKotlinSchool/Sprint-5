package ru.sber.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {
    class ClientFriend{
        val client:String? = null
    }
    @Test
    fun `Нобходимо десериализовать данные в класс`() {
        // given
        val data = """{"client": "Иванов Иван Иванович"}"""
        val objectMapper = ObjectMapper()
        val clientFriend = objectMapper.readValue<ClientFriend>(data).client!!.split(" ")
        val clientFriendData = """{"firstName": "%s", "lastName": "%s", "middleName": "%s"}"""
                                        .format(clientFriend[1]
                                        ,clientFriend[0]
                                        ,clientFriend[2])
        // when
        val client = objectMapper.readValue<Client7>(clientFriendData)

        // then
        assertEquals("Иван", client.firstName)
        assertEquals("Иванов", client.lastName)
        assertEquals("Иванович", client.middleName)
    }
}
