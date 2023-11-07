package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonCustomClassDeserializer {

  @Test
  fun `Нобходимо десериализовать данные в класс`() {
    // given
    val data = """{"client": "Иванов Иван Иванович"}"""
    val objectMapper = ObjectMapper().registerModules(
      SimpleModule().addDeserializer(Client7::class.java,
        object : JsonDeserializer<Client7>() {
          override fun deserialize(p0: JsonParser?, p1: DeserializationContext?): Client7 {
            val node = p0?.readValueAsTree<JsonNode>()
            val clientField = node?.get("client")?.textValue()?.split(' ')
              ?: throw MismatchedInputException.from(p0, Client7::class.java,
                "Not found required field [client]")
            if (clientField.size < 3)
              throw MismatchedInputException.from(p0, Client7::class.java,
                "Value of field [client] is not valid")
            return Client7(clientField[1], clientField[0], clientField[2])
          }
        }))
    // when
    val client = objectMapper.readValue<Client7>(data)

    // then
    assertEquals("Иван", client.firstName)
    assertEquals("Иванов", client.lastName)
    assertEquals("Иванович", client.middleName)
  }
}
