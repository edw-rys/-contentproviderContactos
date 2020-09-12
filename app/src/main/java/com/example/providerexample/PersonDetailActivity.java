package com.example.providerexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

/**
 *Una actividad que representa una pantalla de detalles de una sola persona.
 * Esta actividad solo se utiliza en dispositivos móviles. En los dispositivos del
 * tamaño de una tableta, los detalles de los elementos se presentan en paralelo con una
 * lista de elementos en una {@link PersonListActivity}.
 */
public class PersonDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        //Muestra el botón Arriba en la barra de acciones.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //SavedInstanceState no es nulo cuando hay un estado de fragmento guardado de
        // configuraciones anteriores de esta actividad (por ejemplo, cuando se gira la pantalla de vertical a horizontal).

        // En este caso, el fragmento se volverá a agregar automáticamente a su contenedor, por lo que no es necesario agregarlo manualmente.

        if (savedInstanceState == null) {
            // Crea el fragmento de detalle y agrégalo a la actividad
            // utilizando una transacción de fragmentos.
            Bundle arguments = new Bundle();
            arguments.putLong(PersonDetailFragment.ARG_ITEM_ID,
                    getIntent().getLongExtra(PersonDetailFragment.ARG_ITEM_ID, -1));
            PersonDetailFragment fragment = new PersonDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.person_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Esta ID representa el botón Inicio o Arriba.
                // En el caso de esta actividad, se muestra el botón Arriba
                NavUtils.navigateUpTo(this, new Intent(this, PersonListActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
