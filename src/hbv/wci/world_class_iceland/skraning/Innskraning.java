package hbv.wci.world_class_iceland.skraning;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import hbv.wci.world_class_iceland.data.DataSource;
import hbv.wci.world_class_iceland.opnunartimar.Opnunartimar;
import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.stundatafla.AlmennStundatafla;
import hbv.wci.world_class_iceland.stundatafla.StundataflaNyrTimi;
import hbv.wci.world_class_iceland.stundatafla.StundataflanMin;
import hbv.wci.world_class_iceland.Global;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/** 
 * Activity sem synir innskraningar val og menu fyrir navigation
 * 
 * @author Karl
 * @see Activity
 */
public class Innskraning extends Activity {
	private DataSource mDataSource;
	public Context mContext = this;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private String vikudagur;
	private Map<String,Integer> map;

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

		setOnLoginClickListener();
		setOnRegisterListener();
		
		setDrawer();
		
	}
	
	private void setOnRegisterListener(){
		final Button nySkra = (Button) findViewById(R.id.nySkraTakki);
		nySkra.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent j = new Intent(Innskraning.this, Nyskraning.class);
				startActivity(j);
			}
		});
	}
	
	private void setOnLoginClickListener(){
		final EditText netfangInntak = (EditText) findViewById(R.id.netfangInntak);
		final EditText lykilordInntak = (EditText) findViewById(R.id.lykilordInntakNr3);
		final Button skraInn = (Button) findViewById(R.id.skraInnTakki);
		
		lykilordInntak.setTypeface(Typeface.SANS_SERIF);
		
		skraInn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				mDataSource = new DataSource(mContext);
				mDataSource.open();
				
				String netfang = netfangInntak.getText().toString();
				String lykilord = lykilordInntak.getText().toString();
				
				boolean correctCredentials = mDataSource.checkUser(netfang, lykilord, mContext);
				if(correctCredentials) {
					loginSuccessful(netfang);
				} else {
					loginUnsuccessfulDialog();	
				}
				mDataSource.close();
			}
		});
	}
	
	private void loginSuccessful(String netfang){
		Global.currentUser = netfang;
		Intent i = new Intent(Innskraning.this, AlmennStundatafla.class);
		startActivity(i);
	}
	
	private void loginUnsuccessfulDialog(){
		final Dialog dialog = new Dialog(mContext);
		dialog.setContentView(R.layout.dialog_nyskra);
		dialog.setTitle("Innskráningarvilla");

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		String info = "Ekki tókst að skrá þig inn.\nAthugaðu hvort netfangið og lykilorðið eru rétt.";

								
		text.setText(info);
		
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}
	
	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mDrawerList.setItemChecked(position, true);
			mDrawerLayout.closeDrawer(mDrawerList);
			
			String str = Global.drawerListItems[position];
			if (str.equals(Global.ST1)) {
				Intent i = new Intent(Innskraning.this, AlmennStundatafla.class);
				i.putExtra("vikudagur", Integer.toString(Global.map.get(Global.dayOfWeek)));
				startActivity(i);
			} else if (str.equals(Global.ST2)){
				Intent i = new Intent(Innskraning.this, StundataflanMin.class);
				i.putExtra("vikudagur", Integer.toString(Global.map.get(Global.dayOfWeek)));
				startActivity(i);
			} else if (str.equals(Global.OPN)){
				Intent i = new Intent(Innskraning.this, Opnunartimar.class);
				startActivity(i);
			} else if (str.equals(Global.UTS)) {
				Global.currentUser = null;
				//mDrawerToggle.syncState();
				
				Intent i = new Intent(Innskraning.this, Innskraning.class);
				i.putExtra("vikudagur", Integer.toString(Global.map.get(Global.dayOfWeek)));
				startActivity(i);
			} else if (str.equals(Global.INS)) {
				Intent i = new Intent(Innskraning.this, Innskraning.class);
				startActivity(i);
			} else if (str.contains("@")) {
				//Intent i = new Intent(Innskraning.this, UmNotenda.class);
				//startActivity(i);
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
		// check if user is logged in
		if(!Global.isUserLoggedIn(mContext)) {
			Global.drawerListItems = new String[] {Global.ST1, Global.OPN, Global.INS};
		} else {
			Global.drawerListItems = new String[] {Global.getUsersEmail(mContext), Global.ST1, Global.ST2, Global.OPN, Global.UTS};
		}
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, Global.drawerListItems));
		
		//boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//menu.findItem(R.id.opnun_menu).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public void onBackPressed() {
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
	
	public void setDrawer()	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_innskraning);
		mDrawerList = (ListView) findViewById(R.id.left_drawer_innskraning);

		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, Global.drawerListItems));
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

}
