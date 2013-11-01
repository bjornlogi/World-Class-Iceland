package hbv.wci.world_class_iceland.opnunartimar;

import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.R.id;
import hbv.wci.world_class_iceland.R.layout;
import hbv.wci.world_class_iceland.R.menu;
import hbv.wci.world_class_iceland.stundatafla.StundataflaActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

/**
 * Activity sem synir valmynd til tess ad sja opnunartima stodva. 
 * 
 * @author Bjorn
 * @see Activity
 */
public class Opnunartimar extends Activity {

	/**
	 * Byr til skjainn, bindur layout ur opnunartimar.xml við skjainn. Tengir virkni við takkana ur layout-i.
	 *
	 * @param savedInstanceState a Bundle which does something
	 * @return none
	 * @see Bundle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opnunartimar);

		Button spong_takki = (Button) this.findViewById(R.id.spong_opnun);
		Button kringlan_takki = (Button) this.findViewById(R.id.kringlan_opnun);
		Button laugar_takki = (Button) this.findViewById(R.id.laugar_opnun);
		Button egils_takki = (Button) this.findViewById(R.id.egils_opnun);
		Button hfj_takki = (Button) this.findViewById(R.id.hfj_opnun);
		Button nes_takki = (Button) this.findViewById(R.id.nes_opnun);
		Button mosfells_takki = (Button) this.findViewById(R.id.mosfells_opnun);
		Button ogur_takki = (Button) this.findViewById(R.id.ogur_opnun);
		Button kpv_takki = (Button) this.findViewById(R.id.kop_opnun);
		Button hr_takki = (Button) this.findViewById(R.id.hr_opnun);
		
	 
		spong_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Spöngin");
//				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
//				j.putExtra("stod","Spöngin");
//				startActivity(j);
			}
		});
		
		kringlan_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Kringlan");
//				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
//				j.putExtra("stod","Kringlan");
//				startActivity(j);
			}
		});
		
		laugar_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Laugar");
//				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
//				j.putExtra("stod","Laugar");
//				startActivity(j);
			}
		});
		
		egils_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Egilshöll");
//				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
//				j.putExtra("stod","Egilshöll");
//				startActivity(j);
			}
		});
		
		hfj_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Hafnarfjörður");
//				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
//				j.putExtra("stod","Hafnarfjörður");
//				startActivity(j);
			}
		});
		
		nes_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Seltjarnarnes");
//				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
//				j.putExtra("stod","Seltjarnarnes");
//				startActivity(j);
			}
		});
		
		mosfells_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Mosfellsbær");
//				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
//				j.putExtra("stod","Mosfellsbær");
//				startActivity(j);
			}
		});
		
		ogur_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Ögurhvarf");
//				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
//				j.putExtra("stod","Ögurhvarf");
//				startActivity(j);
			}
		});
		
		kpv_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{
				createIntent("Kópavogur");
//				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
//				j.putExtra("stod","Kópavogur");
//				startActivity(j);
			}
		});
		
		hr_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{
				createIntent("Háskólinn í Reykjavík");
//				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
//				j.putExtra("stod","Háskólinn í Reykjavík");
//				startActivity(j);
			}
		});
		
	}
	
	private void createIntent(String stod){
		Intent i = new Intent(this, Opnunartimi.class);
		i.putExtra("stod", stod);
		i.putExtra("vikudagur", getIntent().getStringExtra("vikudagur"));
		startActivity(i);
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
		getMenuInflater().inflate(R.menu.menu_worldclass, menu);
		return true;
	}
	
	/**
	 * Styrir i hvada Activity verdur kallad fyrir hvern af valmoguleikunum
	 * 
	 * @param item 
	 * @return boolean gildi sem segir manni breytingin a Activity hafi gengid upp
	 * @see MenuItem
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.opnun_menu:
				Intent j = new Intent(Opnunartimar.this, Opnunartimar.class);
				startActivity(j);
				break;
			case R.id.stundatafla_menu:
				Intent i = new Intent(Opnunartimar.this, StundataflaActivity.class);
				startActivity(i);
				break;
		}
		
		return true; 
	}

}