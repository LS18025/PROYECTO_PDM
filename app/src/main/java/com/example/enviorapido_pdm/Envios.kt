package com.example.enviorapido_pdm

data class Envios(
        val id_envio: Int,
        val id_persona: Int,
        val etiqueta: ByteArray,
        val costo_total_envio: Double,
        val fecha_envio: String,
        val fecha_programada: String,
        val numero_conf: String
) {

}