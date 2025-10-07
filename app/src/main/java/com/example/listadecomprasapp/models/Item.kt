package com.example.listadecomprasapp.models

data class Item(
    val id: Int,
    var nome: String,
    var quantidade: Int,
    var unidade: String,
    var categoria: String,
    var comprado: Boolean = false
)