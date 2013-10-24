package hbv.wci.world_class_iceland;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/** 
 * Activity sem synir innskraningar val og menu fyrir navigation
 * 
 * @author Karl
 * @see Activity
 */
public class Innskraning extends Activity {
	private DataSource mDataSource;
	public Context mContext = this;

	/** Byr til skjainn, bindur layout ur activity_innskraning.xml vid skjainn
	 *
	 * @param savedInstanceState a Bundle which does something
	 * @return none
	 * @see Bundle
	 */
	String vikudagur;
	Map<String,Integer> map;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_innskraning);
		
		java.util.TimeZone T1 = TimeZone.getTimeZone("GMT"); 
		SimpleDateFormat DOW = new SimpleDateFormat ("EEE");
		DOW.setTimeZone(T1);
		
		Date date = new Date();
		vikudagur = DOW.format(date);
		createMap();
		
		mDataSource = new DataSource(mContext);
		mDataSource.open();
		
		final Button skraInn = (Button) findViewById(R.id.skraInnTakki);
		skraInn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final EditText netfangInntak = (EditText) findViewById(R.id.netfangInntak);
				final EditText lykilordInntak = (EditText) findViewById(R.id.lykilordInntak);
				
				String netfang = netfangInntak.getText().toString();
				String lykilord = lykilordInntak.getText().toString();
				
				boolean flag = mDataSource.checkUser(netfang, lykilord);
				
				if(flag) {
					Intent i = new Intent(Innskraning.this, ViewPageActivity.class);
					startActivity(i);
				} else {
					Toast.makeText(Innskraning.this, "Rangt netfang og/eða lykilorð.", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		final Button nySkra = (Button) findViewById(R.id.nySkraTakki);
		nySkra.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void createMap(){
		map = new HashMap<String,Integer>();
		map.put("Mon", 0);map.put("Tue", 1);map.put("Wed", 2);
		map.put("Thu", 3);map.put("Fri", 4);map.put("Sat", 5);
		map.put("Sun", 6);
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
				k.putExtra("vikudagur", Integer.toString(map.get(vikudagur)));
				startActivity(k);
				break;
			case R.id.expandable_menu:
				Intent l = new Intent(Innskraning.this, Expandable.class);
				startActivity(l);
				break;
		}
		
		return true; 
	}

}
