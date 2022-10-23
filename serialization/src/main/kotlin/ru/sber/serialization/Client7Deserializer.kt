package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode

class Client7Deserializer : JsonDeserializer<Client7>() {

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Client7 {


        val node : JsonNode = p!!.readValueAsTree()
        val client = node.get("client").textValue()

        val firstName = client.split(' ')[1]
        val lastName =  client.split(' ')[0]
        val middleName =  client.split(' ')[2]
        return Client7(firstName, lastName, middleName)
    }

}
