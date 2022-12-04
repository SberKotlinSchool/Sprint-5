package ru.sber.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

class CustomClass7Deserializer : StdDeserializer<Client7>(
    Client7::class.java
) {
    override fun deserialize(parser: JsonParser?, context: DeserializationContext?): Client7 {
        val treeNode: TreeNode = parser!!.readValueAsTree()
        val paramsString = treeNode.get("client").toString()
        val params = paramsString.substring(1, paramsString.length - 1).split(" ")

        return Client7(params[1], params[0], params[2])
    }
}