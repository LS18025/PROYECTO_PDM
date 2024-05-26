package com.example.enviorapido_pdm

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.enviorapido_pdm.ui.paquete.Paquete
import java.text.SimpleDateFormat
import java.util.Date



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
        private const val COLUMNA_ID_MUNICIPIO = "ID_MUNICIPIO"
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
        private const val COL_ID_DIRECCION_ENVIO = "ID_DIRECCION_ENVIO"
        private const val COL_ID_DESTINATARIO_ENVIO = "ID_DESTINATARIO_ENVIO"
        private const val COL_ID_TRANSPORTISTA_ENVIO = "ID_TRANSPORTISTA_ENVIO"
        private const val COL_ETIQUETA = "ETIQUETA"
        private const val COL_COSTO_TOTAL_ENVIO = "COSTO_TOTAL_ENVIO"
        private const val COL_FECHA_ENVIO = "FECHA_ENVIO"
        private const val COL_FECHA_PROGRAMADA = "FECHA_PROGRAMADA"
        private const val COL_NUMERO_CONF = "NUMERO_CONF"

        //Tabla Seguimiento
        private const val TABLE_SEGUIMIENTO = "Seguimiento"
        private const val COL_ID_SEGUIMIENTO = "ID_SEGUIMIENTO"
        private const val COL_FECHA_SEGUIMIENTO = "FECHA_SEGUIMIENTO"
        private const val COL_ESTADO_SEGUIMIENTO = "ESTADO_SEGUIMIENTO"
        private const val COL_UBICACION_SEGUIMIENTO = "UBICACION_SEGUIMIENTO"

        //Tabla Paquete
        private const val TABLE_PAQUETE = "Paquete"
        private const val COL_ID_PAQUETE = "ID_PAQUETE"
        private const val COLUMNA_ID_ENVIO = "ID_ENVIO"
        private const val COL_COSTO_PAQUETE = "COSTO_PAQUETE"
        private const val COL_PESO_PAQUETE = "PESO_PAQUETE"
        private const val COL_TAMANO_PAQUETE = "TAMANO_PAQUETE"
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

        //ROL
        val createTableRolSQL = "CREATE TABLE $TABLE_ROL (" +
                "$COL_ID_ROL INTEGER PRIMARY KEY, " +
                "$COL_NOMBRE_ROL TEXT NOT NULL, " +
                "$COL_DECRIPCION_ROL TEXT NOT NULL)"


        db.execSQL(createTableRolSQL)

        //USUARIOS

        val createTableUsuarioSQL = "CREATE TABLE $TABLE_USUARIO (" +
                "$COL_ID_USUARIO text PRIMARY KEY, " +
                "$COL_ID_ROL INTEGER," +
                "$COL_PRIMER_NOMBRE_PERSONA text," +
                "$COL_PRIMER_APELLIDO_PERSONA text," +
                "$COL_EMAIL_PERSONA text," +
                "$COL_TELEFONO_PERSONA text," +
                "$COL_USUARIO text," +
                "$COL_CONTRASENA text,"+
                "FOREIGN KEY($COL_ID_ROL) REFERENCES $TABLE_ROL($COL_ID_ROL))"


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
                "$COL_ID_USUARIO text, " +
                "$COL_ID_DIRECCION INTEGER,"+
                "$COL_ID_DESTINATARIO INTEGER,"+
                "$COL_ID_TRANSPORTISTA INTEGER,"+
                "$COL_ETIQUETA TEXT, " +
                "$COL_COSTO_TOTAL_ENVIO REAL, " +
                "$COL_FECHA_ENVIO DATE, " +
                "$COL_FECHA_PROGRAMADA DATE, " +
                "$COL_NUMERO_CONF TEXT,"+
                "FOREIGN KEY($COL_ID_DIRECCION) REFERENCES $TABLE_DIRECCION($COL_ID_DIRECCION), " +
                "FOREIGN KEY($COL_ID_DESTINATARIO) REFERENCES $TABLE_DESTINATARIO($COL_ID_DESTINATARIO),"+
                "FOREIGN KEY($COL_ID_TRANSPORTISTA) REFERENCES $TABLE_TRANSPORTISTA($COL_ID_TRANSPORTISTA))"

        db.execSQL(createTableEnvioSQL)

        // Seguimiento
        val createTableSeguimientoSQL = "CREATE TABLE $TABLE_SEGUIMIENTO (" +
                "$COL_ID_SEGUIMIENTO INTEGER PRIMARY KEY, " +
                "$COL_ID_ENVIO INTEGER, " +
                "$COL_FECHA_SEGUIMIENTO DATE, " +
                "$COL_ESTADO_SEGUIMIENTO TEXT, " +
                "$COL_UBICACION_SEGUIMIENTO TEXT,"+
                "FOREIGN KEY($COL_ID_ENVIO) REFERENCES $TABLE_ENVIO($COL_ID_ENVIO))"

        db.execSQL(createTableSeguimientoSQL)


        // Paquete
        val createTablePaqueteSQL = "CREATE TABLE $TABLE_PAQUETE (" +
                "$COL_ID_PAQUETE INTEGER PRIMARY KEY, " +
                "$COL_ID_ENVIO INTEGER, " +
                "$COL_COSTO_PAQUETE REAL NOT NULL, " +
                "$COL_PESO_PAQUETE REAL NOT NULL, " +
                "$COL_TAMANO_PAQUETE REAL NOT NULL, " +
                "FOREIGN KEY($COL_ID_ENVIO) REFERENCES $TABLE_ENVIO($COL_ID_ENVIO))"

        db.execSQL(createTablePaqueteSQL)

        // Datos de prueba en la tabla Rol
        db.execSQL("INSERT INTO $TABLE_ROL ($COL_ID_ROL, $COL_NOMBRE_ROL, $COL_DECRIPCION_ROL) VALUES (1, 'Administrador', 'Rol de administrador del sistema')");
        db.execSQL("INSERT INTO $TABLE_ROL ($COL_ID_ROL, $COL_NOMBRE_ROL, $COL_DECRIPCION_ROL) VALUES (2, 'Remitente', 'Rol del Remitente de Envios')");

        val insertUsuarioSQL = "INSERT INTO $TABLE_USUARIO ($COL_ID_USUARIO, $COL_ID_ROL, $COL_PRIMER_NOMBRE_PERSONA, $COL_PRIMER_APELLIDO_PERSONA, $COL_EMAIL_PERSONA, $COL_TELEFONO_PERSONA, $COL_USUARIO, $COL_CONTRASENA) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"

        // Insertar usuario administrador
        db.execSQL("INSERT INTO $TABLE_USUARIO ($COL_ID_USUARIO, $COL_ID_ROL, $COL_PRIMER_NOMBRE_PERSONA, $COL_PRIMER_APELLIDO_PERSONA, $COL_EMAIL_PERSONA, $COL_TELEFONO_PERSONA, $COL_USUARIO, $COL_CONTRASENA) VALUES ('Lkd38FdGd8OHNxuqstiDLEwgDNG2', 1, 'Alexis', 'Orellana', 'od18003@ues.edu.sv', '12345678', 'Admin', 'password')")


        // Datos de prueba en la tabla Direccion
        db.execSQL("INSERT INTO " + TABLE_DIRECCION + " (" + COL_ID_MUNICIPIO + ", " + COL_DIRECCION + ") VALUES (1, 'Calle Principal #123')");
        db.execSQL("INSERT INTO " + TABLE_DIRECCION + " (" + COL_ID_MUNICIPIO + ", " + COL_DIRECCION + ") VALUES (2, 'Avenida Central #456')");

        // Datos de prueba en la tabla Destinatario
        db.execSQL("INSERT INTO " + TABLE_DESTINATARIO + " (" + COL_NOMBRE_DESTINATARIO + ", " + COL_APELLIDO_DESTINATARIO + ", " + COL_TELEFONO_DESTINATARIO + ", " + COL_EMAIL_DESTINATARIO + ") VALUES ('Juan', 'Pérez', '555123456', 'juanperez@example.com')");
        db.execSQL("INSERT INTO " + TABLE_DESTINATARIO + " (" + COL_NOMBRE_DESTINATARIO + ", " + COL_APELLIDO_DESTINATARIO + ", " + COL_TELEFONO_DESTINATARIO + ", " + COL_EMAIL_DESTINATARIO + ") VALUES ('María', 'Gómez', '555987654', 'mariagomez@example.com')");

        // Datos de prueba en la tabla Transportista
        db.execSQL("INSERT INTO " + TABLE_TRANSPORTISTA + " (" + COL_NOMBRE_TRANSPORTISTA + ", " + COL_APELLIDO_TRANSPORTISTA + ", " + COL_TELEFONO_TRANSPORTISTA + ") VALUES ('Carlos', 'Martínez', '555111222')");
        db.execSQL("INSERT INTO " + TABLE_TRANSPORTISTA + " (" + COL_NOMBRE_TRANSPORTISTA + ", " + COL_APELLIDO_TRANSPORTISTA + ", " + COL_TELEFONO_TRANSPORTISTA + ") VALUES ('Ana', 'López', '555333444')");

        val insertEnvio = "INSERT INTO $TABLE_ENVIO ($COL_ID_ENVIO, $COL_ID_USUARIO, $COL_ID_DIRECCION, $COL_ID_DESTINATARIO, $COL_ID_TRANSPORTISTA, $COL_ETIQUETA, $COL_COSTO_TOTAL_ENVIO, $COL_FECHA_ENVIO, $COL_FECHA_PROGRAMADA, $COL_NUMERO_CONF) " +
                "VALUES (86153528, 'id_usuario_ejemplo', 1, 1, 1, 'etiqueta_ejemplo', 50.0, '2024-05-10', '2024-05-15', 'numero_conf_ejemplo')"
        db.execSQL(insertEnvio)


        val insertSeguimiento1 = "INSERT INTO $TABLE_SEGUIMIENTO ($COL_ID_ENVIO, $COL_FECHA_SEGUIMIENTO, $COL_ESTADO_SEGUIMIENTO, $COL_UBICACION_SEGUIMIENTO) " +
                "VALUES (86153528, '2024-05-10', 'En tránsito', 'Ciudad A')"
        db.execSQL(insertSeguimiento1)

        val insertSeguimiento2 = "INSERT INTO $TABLE_SEGUIMIENTO ($COL_ID_ENVIO, $COL_FECHA_SEGUIMIENTO, $COL_ESTADO_SEGUIMIENTO, $COL_UBICACION_SEGUIMIENTO) " +
                "VALUES (86153528, '2024-05-09', 'En almacén', 'Ciudad B')"
        db.execSQL(insertSeguimiento2)


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
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SEGUIMIENTO")
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
        id_Usuario: String,
        id_Direccion: Int,
        id_Destinatario: Int,
        id_Transportista: Int,
        etiqueta: String,
        costo_Total_Envio: Double,
        fecha_Envio: Date,
        fecha_Programada: Date,
        numero_Conf: String
    ): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COL_ID_ENVIO, id_Envio)
            put(COL_ID_USUARIO, id_Usuario)
            put(COL_ID_DIRECCION, id_Direccion)
            put(COL_ID_DESTINATARIO, id_Destinatario)
            put(COL_ID_TRANSPORTISTA, id_Transportista)
            put(COL_ETIQUETA, etiqueta)
            put(COL_COSTO_TOTAL_ENVIO, costo_Total_Envio)
            put(COL_FECHA_ENVIO, SimpleDateFormat("dd-MM-yyyy").format(fecha_Envio))
            put(COL_FECHA_PROGRAMADA, SimpleDateFormat("dd-MM-yyyy").format(fecha_Programada))
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
        IdRol : Int,
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
        valores.put(COL_ID_ROL, IdRol)
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
            put(COLUMNA_ID_MUNICIPIO, id_Municipio)
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
    fun ActualizarDireccion(
        id_Direccion: Int,
        id_Municipio: Int,
        direccion: String
    ): Int {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COLUMNA_ID_MUNICIPIO, id_Municipio)
            put(COL_DIRECCION, direccion)
        }
        val parametros = arrayOf(id_Direccion.toString())
        val IdResultado = db.update(TABLE_DIRECCION, valores, "$COL_ID_DIRECCION=?", parametros)
        db.close()
        return IdResultado
    }

    fun EliminarDireccion(id_Direccion: Int): Int {
        val db = writableDatabase
        val parametros = arrayOf(id_Direccion.toString())
        val IdResultado = db.delete(TABLE_DIRECCION, "$COL_ID_DIRECCION=?", parametros)
        db.close()
        return IdResultado
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

    fun recuperarTodosLosTransportistas(): ArrayList<Transportista> {
        val query = "SELECT * FROM $TABLE_TRANSPORTISTA"
        val db = readableDatabase
        val cursor: Cursor
        val datosTransportistas = ArrayList<Transportista>()

        cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val idTransportista = cursor.getInt(0)
            val nombreTransportista = cursor.getString(1)
            val apellidoTransportista = cursor.getString(2)
            val telefonoTransportista = cursor.getString(3)

            val transportista = Transportista(
                idTransportista,
                nombreTransportista,
                apellidoTransportista,
                telefonoTransportista
            )
            datosTransportistas.add(transportista)
        }
        cursor.close()
        db.close()
        return datosTransportistas
    }
    fun AgregarTransportistas(
        idTransportista: Int,
        nombre: String,
        apellido: String,
        telefono: String
    ): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COL_ID_TRANSPORTISTA, idTransportista)
            put(COL_NOMBRE_TRANSPORTISTA, nombre)
            put(COL_APELLIDO_TRANSPORTISTA, apellido)
            put(COL_TELEFONO_TRANSPORTISTA, telefono)
        }
        val idResultado = db.insert(TABLE_TRANSPORTISTA, null, valores)
        db.close()
        return idResultado
    }

    fun RecuperarUsuario(id_persona: String):ArrayList<Usuarios>
    {
        val query="SELECT * FROM $TABLE_USUARIO WHERE $COL_ID_USUARIO='id_persona'"
        val db=readableDatabase
        val cursor:Cursor
        var DatosUsuario=ArrayList<Usuarios>()
        cursor=db.rawQuery(query,null)
        if (cursor.count==1)
        {
            if (cursor.moveToFirst())
            {
                val id=cursor.getString(0)
                val Nombre=cursor.getString(1)
                val Apellido=cursor.getString(2)
                val Email=cursor.getString(3)
                val Telefono=cursor.getString(4)
                val Usuario=cursor.getString(5)
                val Rol=cursor.getString(6)
            }
        }else
        {
            println("No encontrado")
        }
        cursor.close()
        db.close()
        return DatosUsuario
    }

    fun RecuperarTodosLosUsuarios(): ArrayList<Usuarios> {
        val query = "SELECT * FROM $TABLE_USUARIO"
        val db = readableDatabase
        val cursor: Cursor
        val datosUsuarios = ArrayList<Usuarios>()

        cursor = db.rawQuery(query, null)

        while (cursor.moveToNext())
        {
            val idUsuario = cursor.getString(1)
            val nombreUsuario = cursor.getString(2)
            val apellidoUsuario = cursor.getString(3)
            val emailUsuario = cursor.getString(4)
            val telefonoUsuario = cursor.getString(5)
            val usuariousuario = cursor.getString(6)
            val rolUsuario = cursor.getString(7)


            val usuario = Usuarios(
                idUsuario,
                nombreUsuario,
                apellidoUsuario,
                emailUsuario,
                telefonoUsuario,
                usuariousuario,
                rolUsuario)
            datosUsuarios.add(usuario)
        }
        cursor.close()
        db.close()
        return datosUsuarios

    }

    //actualizar usuarios

    fun ActualizarUsuario(
        id_persona:String,
        primer_nombre_persona:String,
        primer_apellido_persona:String,
        email_persona:String,
        telefono_persona: String?,
        usuario:String):Int
    {
        val db=writableDatabase
        val valores=ContentValues()
        val parametros=arrayOf(id_persona)
        valores.put(COL_PRIMER_NOMBRE_PERSONA,primer_nombre_persona)
        valores.put(COL_PRIMER_APELLIDO_PERSONA, primer_apellido_persona)
        valores.put(COL_EMAIL_PERSONA,email_persona)
        valores.put(COL_TELEFONO_PERSONA,telefono_persona)
        valores.put(COL_USUARIO,usuario)
        val IdResultado=db.update (TABLE_USUARIO,valores,"COL_ID_USUARIO=?",parametros)
        db.close()
        return IdResultado
    }

    //eliminar usuario

    fun EliminarUsuario(id_usuario:String):Int
    {
        val db=writableDatabase
        val parametros=arrayOf(id_usuario)
        val IdResultado=db.delete(TABLE_USUARIO,"COL_ID_USUARIO=?",parametros)
        db.close()
        return IdResultado
    }

    //FUNCIONES DE PAQUETE
    fun AgregarPaquete(
        id_Paquete: Int,
        id_Envio: Int,
        costo_Paquete: Double,
        peso_Paquete: Double,
        tamano_Paquete: Double
    ): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COL_ID_PAQUETE, id_Paquete)
            put(COLUMNA_ID_ENVIO, id_Envio)
            put(COL_COSTO_PAQUETE, costo_Paquete)
            put(COL_PESO_PAQUETE, peso_Paquete)
            put(COL_TAMANO_PAQUETE, tamano_Paquete)
        }
        val IdResultado = db.insert(TABLE_PAQUETE, null, valores)
        db.close()
        return IdResultado
    }

    fun RecuperarTodoslosPaquetes(): ArrayList<Paquete> {
        val query: String = "SELECT * FROM $TABLE_PAQUETE"
        val db = readableDatabase
        val cursor: Cursor
        val datosPaquetes = ArrayList<Paquete>()

        cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id_Paquete = cursor.getInt(0)
            val id_Envio = cursor.getInt(1)
            val costo_Paquete = cursor.getDouble(2)
            val peso_Paquete = cursor.getDouble(3)
            val tamano_Paquete = cursor.getDouble(4)

            val paquete = Paquete(
                id_Paquete,
                id_Envio,
                costo_Paquete,
                peso_Paquete,
                tamano_Paquete
            )
            datosPaquetes.add(paquete)
        }
        cursor.close()
        db.close()
        return datosPaquetes
    }

    fun ActualizarPaquete(
        id_Paquete: Int,
        id_Envio: Int,
        costo_Paquete: Double,
        peso_Paquete: Double,
        tamano_Paquete: Double
    ): Int {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COLUMNA_ID_ENVIO, id_Envio)
            put(COL_COSTO_PAQUETE, costo_Paquete)
            put(COL_PESO_PAQUETE, peso_Paquete)
            put(COL_TAMANO_PAQUETE, tamano_Paquete)
        }
        val parametros = arrayOf(id_Paquete.toString())
        val IdResultado = db.update(TABLE_PAQUETE, valores, "$COL_ID_PAQUETE=?", parametros)
        db.close()
        return IdResultado
    }

    fun EliminarPaquete(id_Paquete: Int): Int {
        val db = writableDatabase
        val parametros = arrayOf(id_Paquete.toString())
        val IdResultado = db.delete(TABLE_PAQUETE, "$COL_ID_PAQUETE=?", parametros)
        db.close()
        return IdResultado
    }
    fun RecuperarPaquetePorId(idPaquete: Int): Paquete? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_PAQUETE WHERE $COL_ID_PAQUETE = ?"
        val cursor = db.rawQuery(query, arrayOf(idPaquete.toString()))
        var paquete: Paquete? = null

        if (cursor.moveToFirst()) {
            paquete = Paquete(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getDouble(2),
                cursor.getDouble(3),
                cursor.getDouble(4)
            )
        }

        cursor.close()
        db.close()

        return paquete
    }


    //Funciones de Seguimiento

    //Funcion Agregar Seguimiento
    fun agregarSeguimiento(
        idEnvio: Int,
        fechaSeguimiento: Date,
        estadoSeguimiento: String,
        ubicacionSeguimiento: String
    ): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COL_ID_ENVIO, idEnvio)
            put(COL_FECHA_SEGUIMIENTO, fechaSeguimiento.time)
            put(COL_ESTADO_SEGUIMIENTO, estadoSeguimiento)
            put(COL_UBICACION_SEGUIMIENTO, ubicacionSeguimiento)
        }
        val idResultado = db.insert(TABLE_SEGUIMIENTO, null, valores)
        db.close()
        return idResultado
    }

    //Funcion Recuperar Todos los Seguimientos
    fun recuperarTodosLosSeguimientos(): ArrayList<Seguimiento> {
        val query = "SELECT * FROM $TABLE_SEGUIMIENTO"
        val db = readableDatabase
        val cursor: Cursor
        val seguimientos = ArrayList<Seguimiento>()

        cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val idSeguimiento = cursor.getInt(0)
            val idEnvio = cursor.getInt(1)
            val fechaSeguimiento = Date(2)
            val estadoSeguimiento = cursor.getString(3)
            val ubicacionSeguimiento = cursor.getString(4)

            val seguimiento = Seguimiento(
                idSeguimiento,
                idEnvio,
                fechaSeguimiento,
                estadoSeguimiento,
                ubicacionSeguimiento
            )
            seguimientos.add(seguimiento)
        }
        cursor.close()
        db.close()
        return seguimientos
    }

    //Funcion Recuperar Seguimiento por ID
    fun recuperarSeguimientosPorIdEnvio(idEnvio: Int): List<Seguimiento> {
        val seguimientos = ArrayList<Seguimiento>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_SEGUIMIENTO WHERE $COL_ID_ENVIO = ?", arrayOf(idEnvio.toString()))

        while (cursor.moveToNext()) {
            val idSeguimiento = cursor.getInt(0)
            val fechaSeguimiento = Date(cursor.getLong(2))
            val estadoSeguimiento = cursor.getString(3)
            val ubicacionSeguimiento = cursor.getString(4)

            val seguimiento = Seguimiento(idSeguimiento, idEnvio, fechaSeguimiento, estadoSeguimiento, ubicacionSeguimiento)
            seguimientos.add(seguimiento)
        }

        cursor.close()
        db.close()

        return seguimientos
    }


    //Funcion Recuperar Seguimientos por ID de Envio
    fun recuperarSeguimientoPorIdEnvio(idEnvio: Int): ArrayList<Seguimiento> {
        val query = "SELECT * FROM $TABLE_SEGUIMIENTO WHERE $COL_ID_ENVIO = $idEnvio"
        val db = readableDatabase
        val cursor: Cursor
        val seguimientos = ArrayList<Seguimiento>()

        cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val idSeguimiento = cursor.getInt(0)
            val fechaSeguimiento = Date(cursor.getLong(2))
            val estadoSeguimiento = cursor.getString(3)
            val ubicacionSeguimiento = cursor.getString(4)

            val seguimiento = Seguimiento(idSeguimiento, idEnvio, fechaSeguimiento, estadoSeguimiento, ubicacionSeguimiento)
            seguimientos.add(seguimiento)
        }
        cursor.close()
        db.close()
        return seguimientos
    }


    //Funcion Eliminar Seguimiento
    fun eliminarSeguimiento(idSeguimiento: Int): Int {
        val db = writableDatabase
        val parametros = arrayOf(idSeguimiento.toString())
        val idResultado = db.delete(TABLE_SEGUIMIENTO, "$COL_ID_SEGUIMIENTO=?", parametros)
        db.close()
        return idResultado
    }

    //Recuperar Usuario por ID
    fun recuperarUsuarioPorId(idUsuario: String): Usuarios? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USUARIO WHERE $COL_ID_USUARIO = ?", arrayOf(idUsuario))

        if (cursor.moveToFirst()) {
            val usuario = Usuarios(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7)
            )
            cursor.close()
            db.close()
            return usuario
        }

        cursor.close()
        db.close()
        return null
    }


    //Funcion getUserRole para obtener el rol de un usuario
    fun getUserRole(idUsuario: String): String {
        val db = readableDatabase
        val query = "SELECT $COL_NOMBRE_ROL FROM $TABLE_ROL WHERE $COL_ID_ROL = (SELECT $COL_ID_ROL FROM $TABLE_USUARIO WHERE $COL_ID_USUARIO = ?)"
        val cursor = db.rawQuery(query, arrayOf(idUsuario))

        Log.d("getUserRole", "Query: $query")

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val rol = cursor.getString(0)
                Log.d("getUserRole", "Role: $rol")
                return rol
            } else {
                Log.d("getUserRole", "No se encontró ningún rol para el usuario con ID: $idUsuario")
                return ""
            }
        }
    }




    fun isDatabaseExists(context: Context): Boolean {
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        return dbFile.exists()
    }

    fun isAdministratorUserExists(): Boolean {
        val db = this.readableDatabase
        val query =
            "SELECT COUNT(*) FROM $TABLE_USUARIO WHERE $COL_ID_ROL = 1" // Verificar si hay algún usuario con el rol de administrador
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count > 0
    }

    fun insertAdministratorUser() {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_ID_USUARIO, "1")
        contentValues.put(COL_ID_ROL, 1)
        contentValues.put(COL_PRIMER_NOMBRE_PERSONA, "Alexis")
        contentValues.put(COL_PRIMER_APELLIDO_PERSONA, "Orellana")
        contentValues.put(COL_EMAIL_PERSONA, "od18003@ues.edu.sv")
        contentValues.put(COL_TELEFONO_PERSONA, "12345678")
        contentValues.put(COL_USUARIO, "Admin")
        contentValues.put(COL_CONTRASENA, "password")

        db.insert(TABLE_USUARIO, null, contentValues)
    }

}