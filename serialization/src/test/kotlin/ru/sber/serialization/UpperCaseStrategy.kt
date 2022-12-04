package ru.sber.serialization

import com.fasterxml.jackson.databind.PropertyNamingStrategies.NamingBase

class UpperCaseStrategy : NamingBase() {
    override fun translate(param: String): String {
        return param.uppercase()
    }
}