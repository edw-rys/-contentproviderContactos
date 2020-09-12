package com.example.providerexample.database;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Clase persona para los contactos
 */
public class Contacto {

	// Nombre de la tabla en la base de datos
	public static final String TABLE_NAME = "Persona";

	/**
	 * Columnas
	 */
	// Id del cotnacto
	public static final String COL_ID = "_id";
	// Campos del conactt
	public static final String COL_FIRSTNAME = "firstname";
	public static final String COL_EMAIL = "email";

	// For database projection so order is consistent
	public static final String[] FIELDS = { COL_ID, COL_FIRSTNAME, COL_EMAIL};
	
	/*
	 * El código SQL que crea una tabla para almacenar personas
	 */
	public static final String CREATE_TABLE = 
			"CREATE TABLE " + TABLE_NAME + "(" 
			+ COL_ID + " INTEGER PRIMARY KEY,"
			+ COL_FIRSTNAME + " TEXT NOT NULL DEFAULT '',"
			+ COL_EMAIL + " TEXT NOT NULL DEFAULT '',"
			+ ")";
	
	// Campos correspondientes a las columnas de la base de datos
	public long id = -1;
	public String firstname = "";
	public String email = "";


	/**
	 * Constructor vacío
	 */
	public Contacto() {
	}

	/**
	 * Convierta información de la base de datos en un objeto Persona.
	 */
	public Contacto(final Cursor cursor) {
		// Indices expected to match order in FIELDS!
		this.id = cursor.getLong(0);
		this.firstname = cursor.getString(1);
		this.email = cursor.getString(2);

	}

	/**
	 * Devuelve los campos en un objeto ContentValues, adecuado para la inserción en la base de datos.
	 */
	public ContentValues obtenerContenido() {
		final ContentValues values = new ContentValues();
		// Note that ID is NOT included here
		values.put(COL_FIRSTNAME, firstname);
		values.put(COL_EMAIL, email);

		return values;
	}
}
