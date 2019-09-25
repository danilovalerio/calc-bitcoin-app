package com.example.calculadoradebitcoin

import java.text.NumberFormat
import java.util.*

fun granaToBr(valor: Double) : String{
    val f = NumberFormat.getCurrencyInstance(Locale("pt","br"))
    return f.format(valor)
}