package ru.sber.serialization

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.TextNode
import java.io.IOException
import java.time.LocalDate


@JsonIgnoreProperties(ignoreUnknown = true)
data class Client1(
    @JsonProperty("firstName") val firstName: String,
    @JsonProperty("lastName") val lastName: String,
    @JsonProperty("middleName") val middleName: String?,
    @JsonProperty("passportNumber") val passportNumber: String,
    @JsonProperty("passportSerial") val passportSerial: String,
    @JsonProperty("birthDate") val birthDate: LocalDate
)

class PersonDeserializer(vc: Class<*>?) : StdDeserializer<Client1>(vc) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext?): Client1 {
        val node = jp.codec.readTree<JsonNode>(jp)
        val FIRSTNAME = node["FIRSTNAME"].asText()
        val LASTNAME = node["LASTNAME"].asText()
        val MIDDLENAME = node["MIDDLENAME"].asText()
        val PASSPORTNUMBER = node["PASSPORTNUMBER"].asText()
        val PASSPORTSERIAL = node["PASSPORTSERIAL"].asText()

        val BIRTHDATE = node["BIRTHDATE"].asText()


        return Client1(FIRSTNAME, LASTNAME, MIDDLENAME, PASSPORTNUMBER, PASSPORTSERIAL, LocalDate.parse(BIRTHDATE))
    }
}
