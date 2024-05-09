package com.example.enviorapido_pdm

data class Envios(
        val id_Envio: Int,
        val id_UsuarioEnvio: Int,
        val id_DireccionEnvio: Int,
        val id_DestinatarioEnvio: Int,
        val id_TransportistaEnvio: Int,
        val etiqueta: String,
        val costo_Total_Envio: Double,
        val fecha_Envio: String,
        val fecha_Programada: String,
        val numero_Conf: String
) {

}