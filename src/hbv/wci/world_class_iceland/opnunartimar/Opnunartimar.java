package hbv.wci.world_class_iceland.opnunartimar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.R.id;
import hbv.wci.world_class_iceland.R.layout;
import hbv.wci.world_class_iceland.R.menu;
import hbv.wci.world_class_iceland.skraning.Innskraning;
import hbv.wci.world_class_iceland.stundatafla.StundataflaActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View.OnClickListener;

/**
 * Activity sem synir valmynd til tess ad sja opnunartima stodva. 
 * 
 * @author Bjorn
 * @see Activity
 */
public class Opnunartimar extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private String[] drawerListItems = new String[] {"Notandi", "Opnunartímar", "Stundatafla", "Útskrá"};
	
	private String vikudagur;
	private Map<String,Integer> map;
	
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
		
		TimeZone T1 = TimeZone.getTimeZone("GMT"); 
		SimpleDateFormat DOW = new SimpleDateFormat ("EEE");
		DOW.setTimeZone(T1);
		
		Date date = new Date();
		vikudagur = DOW.format(date);
		createMap();

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
		
		/*
		 * CREATE DRAWER
		 */
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_opnunartimar);
		mDrawerList = (ListView) findViewById(R.id.left_drawer_opnunartimar);

		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawerListItems));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(
			this,                  /* host Activity */
			mDrawerLayout,         /* DrawerLayout object */
			R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
			R.string.app_name,     /* "open drawer" description for accessibility */
			R.string.app_name      /* "close drawer" description for accessibility */
		){
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
	}
	
	private void createIntent(String stod){
		Intent i = new Intent(this, Opnunartimi.class);
		i.putExtra("stod", stod);
		i.putExtra("vikudagur", getIntent().getStringExtra("vikudagur"));
		startActivity(i);
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
		// Drawer toggle button
		if (mDrawerToggle.onOptionsItemSelected(item)) {
	          return true;
	    }
			
		return super.onOptionsItemSelected(item); 
	}
	
	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mDrawerList.setItemChecked(position, true);
			mDrawerLayout.closeDrawer(mDrawerList);
			
			String str = drawerListItems[position];
			if (str.equals("Stundatafla")) {
				Intent i = new Intent(Opnunartimar.this, StundataflaActivity.class);
				i.putExtra("vikudagur", Integer.toString(map.get(vikudagur)));
				startActivity(i);
			} else if (str.equals("Opnunartímar")){
				Intent i = new Intent(Opnunartimar.this, Opnunartimar.class);
				startActivity(i);
			} else if (str.equals("Útskrá")) {
				System.out.println("Útskrá!");
			}
		}
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggle
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//menu.findItem(R.id.opnun_menu).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
		
	private void createMap(){
		map = new HashMap<String,Integer>();
		map.put("Mon", 0);
		map.put("Tue", 1);
		map.put("Wed", 2);
		map.put("Thu", 3);
		map.put("Fri", 4);
		map.put("Sat", 5);
		map.put("Sun", 6);
	}

}