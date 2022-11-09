package ru.sber.serialization.deserializer

import com.fasterxml.jackson.databind.PropertyNamingStrategies.NamingBase

class UpperCaseNamingStrategy : NamingBase() {

    override fun translate(propertyName: String?): String {
        return propertyName?.uppercase() ?: ""
    }
}