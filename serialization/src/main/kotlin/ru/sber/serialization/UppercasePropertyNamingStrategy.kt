package ru.sber.serialization

import com.fasterxml.jackson.databind.PropertyNamingStrategies.NamingBase

class UppercasePropertyNamingStrategy : NamingBase() {

    override fun translate(input: String?): String {
        requireNotNull(input) { "Property name can't be null" }

        return input.uppercase()
    }
}
