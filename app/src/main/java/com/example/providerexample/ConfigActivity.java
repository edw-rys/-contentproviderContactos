package com.example.providerexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ConfigActivity extends FragmentActivity implements PersonListFragment.Callbacks, AdapterView.OnItemSelectedListener {

	/*
    @Vars
     */
	Spinner spiner;
	EditText textView;
	Button btnsave;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		// Obtener campos
		// Spinner
		spiner = (Spinner) findViewById(R.id.sizeSpinner);
		// Edit text
		textView = (EditText) findViewById(R.id.text_print);
		btnsave = (Button) findViewById(R.id.savebtn);

		// Clic en el botón guardar
		btnsave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveChanges();
			}
		});

		// Cargar datos al spinner
		this.setDataSpinner();
		// Obtener datos guardados
		this.getSettings();
	}

	// set data spinner
	public void setDataSpinner(){
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.fontsize, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spiner.setAdapter(adapter);
		spiner.setOnItemSelectedListener(  this);
	}

	/**
	 * Guardar configuración
	 */
	public void saveChanges(){
		// Sobreescritura del meétodo
		super.onPause();
		// Crear u obtener objeto SharedPreferences
		SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
		// Hacer editable el objeto SharedPreferences
		SharedPreferences.Editor editor = datos.edit();
		// Establecer información a almacenar

		// spiner
		String item = spiner.getSelectedItem().toString();
		editor.putString("fontsize", item);
		Log.d("Save",item);
		//text
		String message = textView.getText().toString();
		editor.putString("message", message);

		// Tranferir la información SharedPreferences
		editor.apply();
	}

	/**
	 * Obtener datos guardados
	 */
	public void getSettings(){
		// Sobreescritura del meétodo
		super.onPause();
		/// Crear u obtener objeto SharedPreferences
		SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
		// Recuperar información a almacenada
		String data = datos.getString("message","default");
		String item = datos.getString("fontsize","small");
		// Mostar información
		textView.setText(data);

		spiner.setSelection(getDataFontIndex(item));
	}

	/**
	 * Obtener datos del tamaño de fuente (PEQUEÑO, MEDIANO, GRANDE) y retornar el índice
	 * @param font
	 * @return
	 */
	public static int getDataFontIndex(String font){
		if ("PEQUEÑO".equals(font)) {
			return 0;
		} else if ("MEDIANO".equals(font)) {
			return 1;
		} else if ("GRANDRE".equals(font)) {
			return 2;
		}
		return 0;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}


	@Override
	public void onItemSelected(long l) {

	}
}
