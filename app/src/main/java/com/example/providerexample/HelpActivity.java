package com.example.providerexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.providerexample.database.Contacto;
import com.example.providerexample.database.DatabaseHandler;

public class HelpActivity extends FragmentActivity implements PersonListFragment.Callbacks {

	private boolean mTwoPane;
	public static final String ARG_ITEM_ID = "item_id";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
	}
	// Opciones
	public boolean onOptionsItemSelected(MenuItem item){
		int i= item.getItemId();
		if( i == R.id.op_m_1){
			finish();
			startActivity(new Intent(this, PersonListActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onItemSelected(long l) {

	}

	
}
