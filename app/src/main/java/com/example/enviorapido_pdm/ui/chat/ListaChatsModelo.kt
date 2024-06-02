package com.example.enviorapido_pdm.ui.chat

class ListaChatsModelo {


    private  var uid : String = ""

    constructor()

    constructor(uid : String){
        this.uid=uid
    }

    fun getUid() : String?{
        return uid
    }

    fun setUid(uid: String?){
        this.uid = uid!!
    }

}