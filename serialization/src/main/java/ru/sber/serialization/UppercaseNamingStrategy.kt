package ru.sber.serialization

import com.fasterxml.jackson.databind.PropertyNamingStrategies.NamingBase

class UppercaseNamingStrategy : NamingBase() {
    override fun translate(p0: String?): String {
        if (p0 != null) {
            return p0.uppercase()
        }
        return ""
    }
}