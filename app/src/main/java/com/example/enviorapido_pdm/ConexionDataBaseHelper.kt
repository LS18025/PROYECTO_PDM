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
        private const val TABLE_MUNICIPIO = "Municipio"
        private const val COL_ID_MUNICIPIO = "ID_MUNICIPIO"
        // private const val COL_ID_DEPARTAMENTO_MUNICIPIO = "ID_DEPARTAMENTO_MUNICIPIO"
        private const val COL_NOMBRE_MUNICIPIO = "NOMBRE_MUNICIPIO"

        //Tabla Direccion
        private const val TABLE_DIRECCION = "Direccion"
        private const val COL_ID_DIRECCION = "ID_DIRECCION"
        //private const val COL_ID_MUNICIPIO = "ID_MUNICIPIO"
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

        //Tabla Rol
        private const val TABLE_ROL = "ROL"
        private const val COL_ID_ROL = "ID_ROL"
        private const val COL_NOMBRE_ROL = "NOMBRE_ROL"
        private const val COL_DECRIPCION_ROL = "DESCRIPCION_ROL"

        //Tabla Usuarios
        private const val TABLE_USUARIO = "Usuario"
        private const val COL_ID_USUARIO = "ID_USUARIO"
        private const val COL_PRIMER_NOMBRE_PERSONA = "PRIMER_NOMBRE_PERSONA"
        private const val COL_PRIMER_APELLIDO_PERSONA = "PRIMER_APELLIDO_PERSONA"
        private const val COL_EMAIL_PERSONA = "EMAIL_PERSONA"
        private const val COL_TELEFONO_PERSONA  = "TELEFONO_PERSONA"
        private const val COL_USUARIO  = "USUARIO"
        private const val COL_CONTRASENA = "CONTRASENA"

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
                "$COL_ID_DEPARTAMENTO INTEGER NOT NULL,"+
                "$COL_NOMBRE_MUNICIPIO TEXT NOT NULL,"+
                "FOREIGN KEY($COL_ID_DEPARTAMENTO) REFERENCES $TABLE_DEPARTAMENTO($COL_ID_DEPARTAMENTO))"

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

        val createTableRolSQL = "CREATE TABLE $TABLE_ROL (" +
                "$COL_ID_ROL INTEGER PRIMARY KEY, " +
                "$COL_NOMBRE_ROL TEXT NOT NULL, " +
                "$COL_DECRIPCION_ROL TEXT NOT NULL," +
                "$COL_EMAIL_DESTINATARIO TEXT NOT NULL)"

        db.execSQL(createTableRolSQL)

        //USUARIOS

        val createTableUsuarioSQL = "CREATE TABLE $TABLE_USUARIO (" +
                "$COL_ID_USUARIO text PRIMARY KEY, " +
                "$COL_PRIMER_NOMBRE_PERSONA text," +
                "$COL_PRIMER_APELLIDO_PERSONA text," +
                "$COL_EMAIL_PERSONA text," +
                "$COL_TELEFONO_PERSONA text," +
                "$COL_USUARIO text," +
                "$COL_CONTRASENA text)"

        db.execSQL(createTableUsuarioSQL)

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
                "$COL_ID_USUARIO text NOT NULL, " +
                "$COL_ID_DIRECCION INTEGER NOT NULL,"+
                "$COL_ID_DESTINATARIO INTEGER NOT NULL,"+
                "$COL_ID_TRANSPORTISTA INTEGER NOT NULL,"+
                "$COL_ETIQUETA TEXT NOT NULL, " +
                "$COL_COSTO_TOTAL_ENVIO REAL, " +
                "$COL_FECHA_ENVIO DATE NOT NULL, " +
                "$COL_FECHA_PROGRAMADA DATE NOT NULL, " +
                "$COL_NUMERO_CONF TEXT NOT NULL,"+
                "FOREIGN KEY($COL_ID_DIRECCION) REFERENCES $TABLE_DIRECCION($COL_ID_DIRECCION), " +
                "FOREIGN KEY($COL_ID_DESTINATARIO) REFERENCES $TABLE_DESTINATARIO($COL_ID_DESTINATARIO),"+
                "FOREIGN KEY($COL_ID_TRANSPORTISTA) REFERENCES $TABLE_TRANSPORTISTA($COL_ID_TRANSPORTISTA))"

        db.execSQL(createTableEnvioSQL)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DEPARTAMENTO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MUNICIPIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DIRECCION")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ROL")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DESTINATARIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSPORTISTA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ENVIO")
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
        id_Envio: Int,
        id_Usuario_Envio: Int,
        id_Direccion_Envio: Int,
        id_Destinatario_Envio: Int,
        id_Transportista_Envio: Int,
        etiqueta: String,
        costo_Total_Envio: Double,
        fecha_Envio: String,
        fecha_Programada: String,
        numero_Conf: String
    ): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COL_ID_ENVIO, id_Envio)
            put(COL_ID_USUARIO_ENVIO, id_Usuario_Envio)
            put(COL_ID_DIRECCION_ENVIO, id_Direccion_Envio)
            put(COL_ID_DESTINATARIO_ENVIO, id_Destinatario_Envio)
            put(COL_ID_TRANSPORTISTA_ENVIO, id_Transportista_Envio)
            put(COL_ETIQUETA, etiqueta)
            put(COL_COSTO_TOTAL_ENVIO, costo_Total_Envio)
            put(COL_FECHA_ENVIO, fecha_Envio)
            put(COL_FECHA_PROGRAMADA, fecha_Programada)
            put(COL_NUMERO_CONF, numero_Conf)
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

        while (cursor.moveToNext())
        {
            val id = cursor.getInt(0)
            val idUsuario = cursor.getInt(1)
            val idDireccion = cursor.getInt(2)
            val idDestinatario = cursor.getInt(3)
            val idTransportista = cursor.getInt(4)
            val etiqueta = cursor.getString(5)
            val costoTotal = cursor.getDouble(6)
            val fechaEnvio = cursor.getString(7)
            val fechaProgramada = cursor.getString(8)
            val numeroConf = cursor.getString(9)

            val envio = Envios(
                id,
                idUsuario,
                idDireccion,
                idDestinatario,
                idTransportista,
                etiqueta,
                costoTotal,
                fechaEnvio,
                fechaProgramada,
                numeroConf
            )

            DatosEnvios.add(envio)
        }
        cursor.close()
        db.close()
        return DatosEnvios
    }

    //FUNCIONES DE USUARIOS
    fun AgregarUsuario(
        IdUsuario: String,
        primerNombre: String,
        primerApellido: String,
        email: String,
        telefono: String,
        usuario: String?,
        contrasena: String?
    ): Long {
        val db = writableDatabase
        val valores = ContentValues()

        valores.put(COL_ID_USUARIO, IdUsuario)
        valores.put(COL_PRIMER_NOMBRE_PERSONA, primerNombre)
        valores.put(COL_PRIMER_APELLIDO_PERSONA, primerApellido)
        valores.put(COL_EMAIL_PERSONA, email)
        valores.put(COL_TELEFONO_PERSONA, telefono)
        valores.put(COL_USUARIO, usuario)
        valores.put(COL_CONTRASENA, contrasena)

        val idResultado = db.insert(TABLE_USUARIO, null, valores)
        db.close()
        return idResultado
    }


    //FUNCIONES DE MUNICIPIO
    fun AgregarMunicipio(
        idMunicipio: Int,
        idDepartamento: Int,
        nombreMunicipio: String
    ): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COL_ID_MUNICIPIO, idMunicipio)
            put(COL_ID_DEPARTAMENTO, idDepartamento)
            put(COL_NOMBRE_MUNICIPIO, nombreMunicipio)
        }
        val IdResultado = db.insert(TABLE_MUNICIPIO, null, valores)
        db.close()
        return IdResultado
    }

    fun RecuperarTodoslosMunicipios(): ArrayList<Municipios> {
        val query: String = "SELECT * FROM $TABLE_MUNICIPIO"
        val db = readableDatabase
        val cursor: Cursor
        val DatosMunicipios = ArrayList<Municipios>()

        cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val idMunicipio = cursor.getInt(0)
            val idDepartamento = cursor.getInt(1)
            val nombreMunicipio = cursor.getString(2)

            val municipio = Municipios(idMunicipio, idDepartamento, nombreMunicipio)
            DatosMunicipios.add(municipio)
        }
        cursor.close()
        db.close()
        return DatosMunicipios
    }

    //FUNCIONES DE DIRECCION
    fun AgregarDireccion(
        id_Direccion: Int,
        id_Municipio: Int,
        direccion: String
    ): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COL_ID_DIRECCION, id_Direccion)
            put(COL_ID_MUNICIPIO, id_Municipio)
            put(COL_DIRECCION, direccion)
        }
        val IdResultado = db.insert(TABLE_DIRECCION, null, valores)
        db.close()
        return IdResultado
    }

    fun RecuperarTodaslasDirecciones(): ArrayList<Direccion> {
        val query: String = "SELECT * FROM $TABLE_DIRECCION"
        val db = readableDatabase
        val cursor: Cursor
        val DatosDirecciones = ArrayList<Direccion>()

        cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val idDireccion = cursor.getInt(0)
            val idMunicipio = cursor.getInt(1)
            val direccion = cursor.getString(2)

            val direccionObj = Direccion(idDireccion, idMunicipio, direccion)
            DatosDirecciones.add(direccionObj)
        }
        cursor.close()
        db.close()
        return DatosDirecciones
    }
    //FUNCIONES DE DESTINATARIO
    fun AgregarDestinatario(
        id_Destinatario: Int,
        nombre_Destinatario: String,
        apellido_Destinatario: String,
        telefono_Destinatario: String,
        email_Destinatario: String
    ): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COL_ID_DESTINATARIO, id_Destinatario)
            put(COL_NOMBRE_DESTINATARIO, nombre_Destinatario)
            put(COL_APELLIDO_DESTINATARIO, apellido_Destinatario)
            put(COL_TELEFONO_DESTINATARIO, telefono_Destinatario)
            put(COL_EMAIL_DESTINATARIO, email_Destinatario)
        }
        val IdResultado = db.insert(TABLE_DESTINATARIO, null, valores)
        db.close()
        return IdResultado
    }

    fun RecuperarTodoslosDestinatarios(): ArrayList<Destinatarios> {
        val query: String = "SELECT * FROM $TABLE_DESTINATARIO"
        val db = readableDatabase
        val cursor: Cursor
        val DatosDestinatarios = ArrayList<Destinatarios>()

        cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id_Destinatario = cursor.getInt(0)
            val nombre_Destinatario = cursor.getString(1)
            val apellido_Destinatario = cursor.getString(2)
            val telefono_Destinatario = cursor.getString(3)
            val email_Destinatario = cursor.getString(4)

            val destinatario = Destinatarios(
                id_Destinatario,
                nombre_Destinatario,
                apellido_Destinatario,
                telefono_Destinatario,
                email_Destinatario
            )
            DatosDestinatarios.add(destinatario)
        }
        cursor.close()
        db.close()
        return DatosDestinatarios
    }


}