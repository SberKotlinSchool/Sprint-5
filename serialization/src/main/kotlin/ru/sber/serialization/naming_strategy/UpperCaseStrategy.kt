package ru.sber.serialization.naming_strategy

import com.fasterxml.jackson.databind.PropertyNamingStrategies.NamingBase

class UpperCaseStrategy : NamingBase() {
    override fun translate(input: String?): String? =
        input?.uppercase() }
