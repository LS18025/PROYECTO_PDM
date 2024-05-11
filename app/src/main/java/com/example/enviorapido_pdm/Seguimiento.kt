package com.example.enviorapido_pdm

import java.util.Date

//Creaacion de la clase seguimiento
data class Seguimiento(
    val idSeguimiento: Int,
    val idEnvio: Int,
    val fechaSeguimiento: Date,
    val estadoSeguimiento: String,
    val ubicacionSeguimiento: String
)
