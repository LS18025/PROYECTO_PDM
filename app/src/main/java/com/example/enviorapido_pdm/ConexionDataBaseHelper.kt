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

        //Departamento
        private const val TABLE_DEPARTAMENTO = "Departamento"
        private const val COL_ID_DEPARTAMENTO = "id_departamento"
        private const val COL_NOMBRE_DEPARTAMENTO = "nombre_departamento"

        //Municipio
        private const val TABLE_MUNICIPIO = "Departamento"
        private const val COL_ID_DEPARTAMENTO_MUNICIPIO = "ID_DEPARTAMENTO_MUNICIPIO"
        private const val COL_NOMBRE_MUNICIPIO = "NOMBRE_MUNICIPIO"

        //Tabla Direccion
        private const val TABLE_DIRECCION = "Direccion"
        private const val COL_ID_DIRECCION = "ID_DIRECCION"
        private const val COL_ID_MUNICIPIO = "ID_MUNICIPIO"
        private const val COL_DIRECCION = "DIRECCION"

        //Destinatario
        private const val TABLE_DESTINATARIO = "Destinatario"
        private const val COL_ID_DESTINATARIO = "ID_DESTINATARIO"
        private const val COL_NOMBRE_DESTINATARIO = "NOMBRE_DESTINATARIO"
        private const val COL_APELLIDO_DESTINATARIO = "APELLIDO_DESTINATARIO"
        private const val COL_TELEFONO_DESTINATARIO = "TELEFONO_DESTINATARIO"
        private const val COL_EMAIL_DESTINATARIO = "EMAIL_DESTINATARIO"

        //Transportista
        private const val TABLE_TRANSPORTISTA = "Transportista"
        private const val COL_ID_TRANSPORTISTA = "ID_TRANSPORTISTA"
        private const val COL_NOMBRE_TRANSPORTISTA = "NOMBRE_TRANSPORTISTA"
        private const val COL_APELLIDO_TRANSPORTISTA = "APELLIDO_TRANSPORTISTA"
        private const val COL_TELEFONO_TRANSPORTISTA = "TELEFONO_TRANSPORTISTA"

        //Tabla Envíos
        private const val TABLE_ENVIO = "Envio"
        private const val COL_ID_ENVIO = "ID_ENVIO"
        private const val COL_ID_USUARIO_ENVIO = "ID_USUARIO_ENVIO"
        private const val COL_ID_DIRECCION_ENVIO = "ID_DIRECCION_ENVIO"
        private const val COL_ID_DESTINATARIO_ENVIO = "ID_DESTINATARIO_ENVIO"
        private const val COL_ID_TRANSPORTISTA_ENVIO = "ID_TRANSPORTISTA_ENVIO"
        private const val COL_ETIQUETA = "ETIQUETA"
        private const val COL_COSTO_TOTAL_ENVIO = "COSTO_TOTAL_ENVIO"
        private const val COL_FECHA_ENVIO = "FECHA_ENVIO"
        private const val COL_FECHA_PROGRAMADA = "FECHA_PROGRAMADA"
        private const val COL_NUMERO_CONF = "NUMERO_CONF"
    }

    override fun onCreate(db: SQLiteDatabase) {

        //Departamentos
        val createTableDepartamentoSQL= "CREATE TABLE $TABLE_DEPARTAMENTO ("+
                "$COL_ID_DEPARTAMENTO INTEGER PRIMARY KEY, "+
                "$COL_NOMBRE_DEPARTAMENTO TEXT NOT NULL)"

        db.execSQL(createTableDepartamentoSQL)

        // Municipios
        val createTableMunicipioSQL= "CREATE TABLE $TABLE_MUNICIPIO ("+
                "$COL_ID_MUNICIPIO INTEGER PRIMARY KEY, "+
                "$COL_ID_DEPARTAMENTO_MUNICIPIO INTEGER NOT NULL,"+
                "$COL_NOMBRE_MUNICIPIO TEXT NOT NULL,"+
                "FOREIGN KEY($COL_ID_DEPARTAMENTO_MUNICIPIO) REFERENCES $TABLE_DEPARTAMENTO($COL_ID_DEPARTAMENTO))"

        db.execSQL(createTableMunicipioSQL)

        //Direccion
        val createTableDireccionSQL = "CREATE TABLE $TABLE_DIRECCION (" +
                "$COL_ID_DIRECCION INTEGER PRIMARY KEY, " +
                "$COL_ID_MUNICIPIO INTEGER NOT NULL, " +
                "$COL_DIRECCION TEXT NOT NULL,"+
                "FOREIGN KEY($COL_ID_MUNICIPIO) REFERENCES $TABLE_MUNICIPIO($COL_ID_MUNICIPIO))"

        db.execSQL(createTableDireccionSQL)

        //Destinatario
        val createTableDestinatarioSQL = "CREATE TABLE $TABLE_DESTINATARIO (" +
                "$COL_ID_DESTINATARIO INTEGER PRIMARY KEY, " +
                "$COL_NOMBRE_DESTINATARIO TEXT NOT NULL, " +
                "$COL_APELLIDO_DESTINATARIO TEXT NOT NULL," +
                "$COL_TELEFONO_DESTINATARIO TEXT NOT NULL,"+
                "$COL_EMAIL_DESTINATARIO TEXT NOT NULL)"

        db.execSQL(createTableDestinatarioSQL)


        //Transportista
        val createTableTransportistaSQL = "CREATE TABLE $TABLE_TRANSPORTISTA (" +
                "$COL_ID_TRANSPORTISTA INTEGER PRIMARY KEY, " +
                "$COL_NOMBRE_TRANSPORTISTA TEXT NOT NULL, " +
                "$COL_APELLIDO_TRANSPORTISTA TEXT NOT NULL," +
                "$COL_TELEFONO_TRANSPORTISTA TEXT NOT NULL)"

        db.execSQL(createTableTransportistaSQL)

        //Envios
        val createTableEnvioSQL = "CREATE TABLE $TABLE_ENVIO (" +
                "$COL_ID_ENVIO INTEGER PRIMARY KEY, " +
                "$COL_ID_USUARIO_ENVIO INTEGER NOT NULL, " +
                "$COL_ID_DIRECCION_ENVIO INTEGER NOT NULL,"+
                "$COL_ID_DESTINATARIO_ENVIO INTEGER NOT NULL,"+
                "$COL_ID_TRANSPORTISTA_ENVIO INTEGER NOT NULL,"+
                "$COL_ETIQUETA TEXT NOT NULL, " +
                "$COL_COSTO_TOTAL_ENVIO REAL, " +
                "$COL_FECHA_ENVIO DATE NOT NULL, " +
                "$COL_FECHA_PROGRAMADA DATE NOT NULL, " +
                "$COL_NUMERO_CONF TEXT NOT NULL,"+
                "FOREIGN KEY($COL_ID_DIRECCION_ENVIO) REFERENCES $TABLE_DIRECCION($COL_ID_DIRECCION), " +
                "FOREIGN KEY($COL_ID_DESTINATARIO_ENVIO) REFERENCES $TABLE_DESTINATARIO($COL_ID_DESTINATARIO),"+
                "FOREIGN KEY($COL_ID_TRANSPORTISTA) REFERENCES $TABLE_TRANSPORTISTA($COL_ID_TRANSPORTISTA))"

        db.execSQL(createTableEnvioSQL)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DEPARTAMENTO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ENVIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DIRECCION")
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

    //FUNCIONES DE ENVIO
    fun AgregarEnvio(
        id_envio: Int,
        id_persona: Int,
        etiqueta: ByteArray,
        costo_total_envio: Double,
        fecha_envio: String,
        fecha_programada: String,
        numero_conf: String
    ): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COL_ID_ENVIO, id_envio)
            put(COL_ID_USUARIO_ENVIO, id_persona)
            put(COL_ETIQUETA, etiqueta)
            put(COL_COSTO_TOTAL_ENVIO, costo_total_envio)
            put(COL_FECHA_ENVIO, fecha_envio)
            put(COL_FECHA_PROGRAMADA, fecha_programada)
            put(COL_NUMERO_CONF, numero_conf)
        }
        val IdResultado = db.insert(TABLE_ENVIO, null, valores)

        db.close()
        return IdResultado
    }
    fun RecuperarTodoslosEnvios(): ArrayList<Envios> {
        val query: String = "SELECT * FROM $TABLE_ENVIO"
        val db = readableDatabase
        val cursor: Cursor
        var DatosEnvios = ArrayList<Envios>()

        cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val id_persona = cursor.getInt(1)
            val etiqueta = cursor.getBlob(2)
            val costo_total_envio = cursor.getDouble(3)
            val fecha_envio = cursor.getString(4)
            val fecha_programada = cursor.getString(5)
            val numero_conf = cursor.getString(6)

            val envio = Envios(
                id,
                id_persona,
                etiqueta,
                costo_total_envio,
                fecha_envio,
                fecha_programada,
                numero_conf
            )

            DatosEnvios.add(envio)
        }
        cursor.close()
        db.close()
        return DatosEnvios
    }


}