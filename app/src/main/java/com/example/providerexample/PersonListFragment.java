package com.example.providerexample;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.providerexample.database.Contacto;
import com.example.providerexample.database.PersonProvider;

/**
 *
 * Un fragmento de lista que representa una lista de personas.
 */
public class PersonListFragment extends ListFragment {

	/**
	 * La clave de lote de serialización (estado de instancia guardada) que representa la posición del artículo activado. Solo se usa en tabletas.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * El objeto de devolución de llamada actual del fragmento, al que se le notifica de los clics en los elementos de la lista.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * La posición actual del artículo activado. Solo se usa en tabletas.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 *
	 * Una interfaz de devolución de llamada que deben implementar todas las actividades que contienen este fragmento.
	 * Este mecanismo permite que las actividades sean notificadas sobre la selección de elementos.
	 */
	public interface Callbacks {
		/**
		 * Devolución de llamada para cuando se ha seleccionado un elemento.
		 */
		public void onItemSelected(long l);
	}

	/**
	 *
	 * Una implementación ficticia de la interfaz {@link Callbacks} que no hace nada
	 * Se usa solo cuando este fragmento no está adjunto a una actividad.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(long id) {
		}
	};

	public PersonListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new SimpleCursorAdapter(getActivity(),
				R.layout.person_listitem, null, new String[] {
						Contacto.COL_FIRSTNAME, Contacto.COL_EMAIL }, new int[] { R.id.cardFirstName,
						R.id.cardLastName }, 0));

		// Cargar el contenido
		getLoaderManager().initLoader(0, null, new LoaderCallbacks<Cursor>() {
			@Override
			public Loader<Cursor> onCreateLoader(int id, Bundle args) {
				return new CursorLoader(getActivity(),
						PersonProvider.URI_PERSONS, Contacto.FIELDS, null, null,
						null);
			}

			@Override
			public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
				((SimpleCursorAdapter) getListAdapter()).swapCursor(c);
			}

			@Override
			public void onLoaderReset(Loader<Cursor> arg0) {
				((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_person_list, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restaurar la posición del artículo activado previamente serializado.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Las actividades que contienen este fragmento deben implementar sus devoluciones de llamada.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// REstablezca la interfaz de devoluciones de llamada activa a la implementación ficticia.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		// Notifique a la interfaz de devoluciones de llamada activa (la actividad, si el fragmento se adjunta a uno) que se ha seleccionado un elemento.
		mCallbacks.onItemSelected(getListAdapter().getItemId(position));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Activa el modo de activación al hacer clic. Cuando este modo está activado,
	 * los elementos de la lista recibirán el estado "activado" cuando se toquen.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// Al configurar CHOICE_MODE_SINGLE, ListView automáticamente le dará a los elementos el estado 'activado' cuando se toquen.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
}
