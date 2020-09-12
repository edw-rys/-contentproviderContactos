package com.example.providerexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.providerexample.database.DatabaseHandler;
import com.example.providerexample.database.Contacto;

/**
 *
 * Un fragmento que representa la pantalla de detalles de una sola persona.
 */
public class PersonDetailFragment extends Fragment {
    /**
     * El argumento del fragmento que representa el ID de elemento que representa este fragmento.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * La persona que presenta este fragmento.
     */
    private Contacto mItem;
    
    /**
     * Los elementos de la interfaz de usuario que muestran los detalles de la persona
     */
    private TextView textFirstName;
    private TextView textLastName;
    private TextView textBio;

    /**
     * Constructor vacío obligatorio para que el administrador de fragmentos
     * cree una instancia del fragmento (por ejemplo, al cambiar la orientación de la pantalla).
     */
    public PersonDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
        	// Se usa el proveedor de contenidos
            mItem = DatabaseHandler.getInstance(getActivity()).obtenerPersona(getArguments().getLong(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // SE MUESTRA LA VISTA CON EL CONTACTO
        View rootView = inflater.inflate(R.layout.fragment_person_detail, container, false);

        if (mItem != null) {
            // Se setean los datos
            textFirstName = ((TextView) rootView.findViewById(R.id.textFirstName));
            textFirstName.setText(mItem.firstname);
            
            textLastName = ((TextView) rootView.findViewById(R.id.textLastName));
            textLastName.setText(mItem.email);

        }

        return rootView;
    }
    
    @Override
    public void onPause() {
    	super.onPause();
        actualizarPersona();
    }
    
    private void actualizarPersona() {
        // ACUALIZAR CONTACTO
    	if (mItem != null) {
    		mItem.firstname = textFirstName.getText().toString();
    		mItem.email = textLastName.getText().toString();

    		DatabaseHandler.getInstance(getActivity()).editarContacto(mItem);
        }
    }
}
