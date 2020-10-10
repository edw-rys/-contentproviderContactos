package com.example.providerexample;

import com.example.providerexample.database.DatabaseHandler;
import com.example.providerexample.database.Contacto;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PersonListActivity extends FragmentActivity implements
		PersonListFragment.Callbacks {
	/**
	 * Objeto de notificación
	 */
	Notification.Builder notificacion;

	public  static final int id_unico = 424;
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_list);

		// Instancia la notificación
		this.notificacion = new Notification.Builder(this);
	}

	/**
	 * Callback method from {@link PersonListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(long id) {
		//this.agregarDetalles(id);
	}


	public void nuevaNota(View view){
		// Se crea una nueva persona
		Contacto p = new Contacto();
		DatabaseHandler.getInstance(this).editarContacto(p);
		// Se abre el fragment y se envía el id
		this.agregarDetalles(p.id);

		// Nueva notificación
		this.createNotification();
	}

	public void agregarDetalles(long id){
		if (mTwoPane) {
			// Mostrar los detalles vacíos
			Bundle arguments = new Bundle();
			arguments.putLong(PersonDetailFragment.ARG_ITEM_ID, id);
			PersonDetailFragment fragment = new PersonDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.person_detail_container, fragment).commit();

		} else {
			// En el modo de panel único, simplemente inicie la actividad de detalle para el ID de artículo seleccionado.
			Intent detailIntent = new Intent(this, PersonDetailActivity.class);
			detailIntent.putExtra(PersonDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}

	// Método para mostrar y ocultar menú
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	// Opciones
	public boolean onOptionsItemSelected(MenuItem item){
		int i= item.getItemId();

		if( i == R.id.op_m_2){
			Intent helpIntent = new Intent(this, HelpActivity.class);

			startActivity(helpIntent);
		}

		if( i == R.id.op_m_3){
			Intent helpIntent = new Intent(this, ConfigActivity.class);

			startActivity(helpIntent);
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Crear notificación
	 */
	public void createNotification() {
		// Configuración de las notificaciones
		this.notificacion.setSmallIcon(R.drawable.ic_launcher);
		this.notificacion.setTicker("Contactos");
		this.notificacion.setWhen(System.currentTimeMillis());
		this.notificacion.setContentTitle("Nuevo contacto");
		this.notificacion.setContentText("Se ha agregado un nuevo contacto");

		// Llamará a la otra activity luego de pulsar en la notificación

		// Administrador de notificaciones
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Construye la notificación
		notificationManager.notify(this.id_unico, this.notificacion.build());
	}
}
