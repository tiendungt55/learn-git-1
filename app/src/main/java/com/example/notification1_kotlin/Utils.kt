package com.example.notification1_kotlin

import java.text.SimpleDateFormat
import java.util.Date

object Utils {
    const val DATE_FORMAT="dd/yyyy/MM"

    fun getDateTime():String{
        val simpleDateFormat= SimpleDateFormat(DATE_FORMAT)
        return simpleDateFormat.format(Date())
    }
}