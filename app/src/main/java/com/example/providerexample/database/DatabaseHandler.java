package com.example.providerexample.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	private static DatabaseHandler singleton;

	/**
	 * Patrón de diseño singleton, para una sóla instancia
	 * @param context
	 * @return
	 */
	public static DatabaseHandler getInstance(final Context context) {
		if (singleton == null) {
			singleton = new DatabaseHandler(context);
		}
		return singleton;
	}

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "contacts_database";

	private final Context context;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// Good idea to have the context that doesn't die with the window
		this.context = context.getApplicationContext();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Crear la tabla
		db.execSQL(Contacto.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/**
	 * Méotdo para obtener un contacto
	 * @param id
	 * @return
	 */
	public synchronized Contacto obtenerPersona(final long id) {
		// Instanciar la BD
		final SQLiteDatabase db = this.getReadableDatabase();

		// Hacer el query del dato
		final Cursor cursor = db.query(Contacto.TABLE_NAME, Contacto.FIELDS,
				Contacto.COL_ID + " IS ?", new String[] { String.valueOf(id) },
				null, null, null, null);
		// No existe
		if (cursor == null || cursor.isAfterLast()) {
			return null;
		}

		//
		Contacto item = null;
		// Obtener primer resultado
		if (cursor.moveToFirst()) {
			// Obtener contacto
			item = new Contacto(cursor);
		}
		// Terminar la consulta
		cursor.close();
		// Retornar el contacto
		return item;
	}

	/**
	 * Método para editar un contacto
	 * @param contacto
	 * @return
	 */
	public synchronized boolean editarContacto(final Contacto contacto) {
		// Bandera que indica el estado
		boolean editado = false;
		// Cambios en la bd
		int result = 0;
		// Instanciar la BD
		final SQLiteDatabase db = this.getWritableDatabase();

		// Si el id es válido
		if (contacto.id > -1) {
			// Actualizar datos del contacto con este id
			result += db.update(Contacto.TABLE_NAME, contacto.obtenerContenido(),
					Contacto.COL_ID + " IS ?",
					new String[] { String.valueOf(contacto.id) });
		}

		if (result > 0) {
			editado = true;
		} else {
			//En caso de que no se haya actualizado, se inserta
			final long id = db.insert(Contacto.TABLE_NAME, null,
					contacto.obtenerContenido());
			// Estado de creado
			if (id > -1) {
				contacto.id = id;
				editado = true;
			}
		}
		// Editado o creado
		if (editado) {
			notificarActualizacionContacto();
		}
		
		return editado;
	}

	/**
	 * Método para eliminar personas
	 * @param person
	 * @return
	 */
	public synchronized int eliminarPersona(final Contacto person) {
		// Instanciar la BD
		final SQLiteDatabase db = this.getWritableDatabase();
		// Actualziar tabla
		final int result = db.delete(Contacto.TABLE_NAME,
				Contacto.COL_ID + " IS ?",
				new String[] { Long.toString(person.id) });
		// Elimiando
		if (result > 0) {
			notificarActualizacionContacto();
		}
		return result;
	}
	
	private void notificarActualizacionContacto() {

		// Se modifican los datos
		context.getContentResolver().notifyChange(
				PersonProvider.URI_PERSONS, null, false);
	}
}
