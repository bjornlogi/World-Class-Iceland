package hbv.wci.world_class_iceland;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

/** 
 * Activity sem synir innskraningar val og menu fyrir navigation
 * 
 * @author Karl
 * @see Activity
 */
public class Innskraning extends Activity {

	/** Byr til skjainn, bindur layout ur activity_innskraning.xml vid skjainn
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
	 * Byr til valmynd fyrir skjainn, hann kemur ur innskraning.xml
	 * 
	 * @param menu 
	 * @return boolean gildi sem segir manni eitthvad
	 * @see Menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_innskraning, menu);
		return true;
	}
	
	/**
	 * Styrir i hvada Activity verdur kallad fyrir hvern af valmoguleikunum
	 * 
	 * @param item 
	 * @return boolean gildi sem segir manni eitthvad
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
			case R.id.pageviewtest_menu:
				Intent k = new Intent(Innskraning.this, ViewPageActivity.class);
				startActivity(k);
				break;
		}
		
		return true; 
	}

}
