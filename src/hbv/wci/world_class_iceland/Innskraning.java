package hbv.wci.world_class_iceland;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

/** Activity sem sýnir innskráningar val og menu fyrir navigation
 * 
 * @see         Activity
 */
public class Innskraning extends Activity {

	/** Býr til skjáinn, bindur layout úr activity_innskraning.xml við skjáinn
	 *
	 * @param savedInstanceState a Bundle which does something
	 * @return none
	 * @see Bundle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_innskraning);
	}

	/**
	 * Býr til valmynd fyrir skjáinn, hann kemur úr innskraning.xml
	 * 
	 * @param menu 
	 * @return boolean gildi sem segir manni eitthvað
	 * @see Menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.innskraning, menu);
		return true;
	}
	
	/**
	 * Stýrir í hvaða Activity verður kallað fyrir hvern af valmöguleikunum
	 * 
	 * @param item 
	 * @return boolean gildi sem segir manni eitthvað
	 * @see MenuItem
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.opnun_menu:
				Intent j = new Intent(Innskraning.this, Opnunartimar.class);
				startActivity(j);
				break;
			case R.id.stundatafla_menu:
				Intent i = new Intent(Innskraning.this, Stundatafla.class);
				startActivity(i);
				break;
			case R.id.testdb_menu:
				Intent k = new Intent(Innskraning.this, TestDatabaseActivity.class);
				startActivity(k);
				break;
		}
		
		return true; 
	}

}
