package ru.sber.serialization.utility

import com.fasterxml.jackson.databind.PropertyNamingStrategies.NamingBase

class UpperCaseNamingStrategy : NamingBase() {
    override fun translate(input: String?): String? =
        input?.uppercase() }
