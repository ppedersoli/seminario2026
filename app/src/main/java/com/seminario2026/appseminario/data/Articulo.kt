package com.seminario2026.appseminario.data

data class Articulo(
    val id: Int,
    val id_deposito: Int,
    val id_rubro: Int,
    val descripcion: String,
    val stock: Int,
    val codigoBarras: String
)
