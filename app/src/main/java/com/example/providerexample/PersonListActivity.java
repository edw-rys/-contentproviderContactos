package com.example.providerexample;

import com.example.providerexample.database.DatabaseHandler;
import com.example.providerexample.database.Contacto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class PersonListActivity extends FragmentActivity implements
		PersonListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_list);
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
}
