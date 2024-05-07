package com.example.enviorapido_pdm

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ConexionDataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION)
{
    companion object
    {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "envios.db"

        private const val TABLE_DEPARTAMENTO = "Departamento"
        private const val COL_ID_DEPARTAMENTO = "id_departamento"
        private const val COL_NOMBRE_DEPARTAMENTO = "nombre_departamento"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableDepartamentoSQL= "CREATE TABLE $TABLE_DEPARTAMENTO ("+
                "$COL_ID_DEPARTAMENTO INTEGER PRIMARY KEY, "+
                "$COL_NOMBRE_DEPARTAMENTO TEXT NOT NULL)"

        db.execSQL(createTableDepartamentoSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DEPARTAMENTO")
        onCreate(db)
    }

    fun AgregarDepartamentos(id_departamento:Int,nombre_departamento:String):Long
    {
        val db = writableDatabase
        val valores = ContentValues()
        valores.put(COL_ID_DEPARTAMENTO,id_departamento)
        valores.put(COL_NOMBRE_DEPARTAMENTO,nombre_departamento)
        val IdResultado= db.insert(TABLE_DEPARTAMENTO,null,valores)

        return IdResultado
        db.close()
    }

    fun RecuperarTodoslosDepartamentos():ArrayList<Departamentos>
    {
        val query:String = "SELECT * FROM $TABLE_DEPARTAMENTO"
        val db= readableDatabase
        val cursor:Cursor
        var DatosDepartamentos=ArrayList<Departamentos>()

        cursor = db.rawQuery(query,null)

        while(cursor.moveToNext())
        {
            val Id = cursor.getString(0).toInt()
            val Nombre = cursor.getString(1)

            val departamento = Departamentos(Id,Nombre)

            DatosDepartamentos.add(departamento)
        }
        cursor.close()
        db.close()
        return DatosDepartamentos
    }


}