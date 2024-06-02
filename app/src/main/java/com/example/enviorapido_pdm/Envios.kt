package com.example.enviorapido_pdm

data class Envios(
        val id: Int,
        val idUsuario: String,
        val direccion: String,
        val destinatario: String,
        val idTransportista: Int,
        val etiqueta: String,
        val costoTotal: Double,
        val fechaEnvio: String,
        val fechaProgramada: String,
        val numeroConf: String
)
 {

}