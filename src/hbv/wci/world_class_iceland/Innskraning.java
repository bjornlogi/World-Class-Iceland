package hbv.wci.world_class_iceland;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class Innskraning extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_innskraning);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.innskraning, menu);
		return true;
	}
	
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
