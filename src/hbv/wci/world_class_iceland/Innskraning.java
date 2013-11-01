package hbv.wci.world_class_iceland;

//import hbv.wci.world_class_iceland.StundataflaFragment.AsyncExecution;

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

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
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
	
	private String[] drawerListItems = new String[] {"Notandi", "Opnunartímar", "Stundatafla", "Útskrá"};
	
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
		
		java.util.TimeZone T1 = TimeZone.getTimeZone("GMT"); 
		SimpleDateFormat DOW = new SimpleDateFormat ("EEE");
		DOW.setTimeZone(T1);
		
		Date date = new Date();
		vikudagur = DOW.format(date);
		createMap();
		
		mDataSource = new DataSource(mContext);
		mDataSource.open();
		
		if (mDataSource.isEmpty())
        	new AsyncExecution().execute("http://www.worldclass.is/heilsuraekt/stundaskra");
		
		final EditText netfangInntak = (EditText) findViewById(R.id.netfangInntak);
		final EditText lykilordInntak = (EditText) findViewById(R.id.lykilordInntakNr3);
		final Button skraInn = (Button) findViewById(R.id.skraInnTakki);
		
		lykilordInntak.setTypeface(Typeface.SANS_SERIF);
		
		skraInn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String netfang = netfangInntak.getText().toString();
				String lykilord = lykilordInntak.getText().toString();
				
				boolean flag = mDataSource.checkUser(netfang, lykilord);
				
				if(flag) {
					Intent i = new Intent(Innskraning.this, StundataflaActivity.class);
					i.putExtra("vikudagur", Integer.toString(map.get(vikudagur)));
					startActivity(i);
				} else {
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
				
			}
		});
		
		final Button nySkra = (Button) findViewById(R.id.nySkraTakki);
		nySkra.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent j = new Intent(Innskraning.this, Nyskraning.class);
				startActivity(j);
			}
		});
		
		/*
		 * CREATE DRAWER
		 */
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

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
	
	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mDrawerList.setItemChecked(position, true);
			mDrawerLayout.closeDrawer(mDrawerList);
			
			String str = drawerListItems[position];
			if (str.equals("Stundatafla")) {
				Intent i = new Intent(Innskraning.this, StundataflaActivity.class);
				i.putExtra("vikudagur", Integer.toString(map.get(vikudagur)));
				startActivity(i);
			} else if (str.equals("Opnunartímar")){
				Intent i = new Intent(Innskraning.this, Opnunartimar.class);
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
		menu.findItem(R.id.opnun_menu).setVisible(!drawerOpen);
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
	
	private static String deIcelandicify(String s) {
		s.replaceAll("á", "a");
		s.replaceAll("í", "i");
		s.replaceAll("ý", "y");
		
		return s;
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
	 * @return boolean gildi sem segir manni breytingin a Activity hafi gengid upp
	 * @see MenuItem
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Drawer toggle button
		if (mDrawerToggle.onOptionsItemSelected(item)) {
	          return true;
	    }
		
		switch (item.getItemId()) {
			case R.id.opnun_menu:
				Intent j = new Intent(Innskraning.this, Opnunartimar.class);
				j.putExtra("vikudagur", Integer.toString(map.get(vikudagur)));
				startActivity(j);
				break;
			case R.id.stundatafla_menu:
				Intent k = new Intent(Innskraning.this, StundataflaActivity.class);
				k.putExtra("vikudagur", Integer.toString(map.get(vikudagur)));
				startActivity(k);
				break;
		}
		
		return true; 
	}
	
	 /**
	 * Skrapar gogn af vef og setur inn i gagnagrunn ef hann er ekki til stadar.
	 * 
	 * @author Bjorn
	 * @see AsyncTask
	 */
	public class AsyncExecution extends AsyncTask<String, Integer, String>{
		ProgressDialog progressDialog;
		/**
		 * Fall sem er keyrt a undan Async verkinu.
		 * 
		 */
		@Override
		protected void onPreExecute() {
			progressDialog= ProgressDialog.show(mContext, "Hleðsla í gangi","Erum að sækja gögn, hinkraðu smá", true);
			super.onPreExecute();
		}
		
		/**
		 * Skrapar gogn af vef og setur inn i gagnagrunn i bakgrunns traedi.
		 * 
		 * @param params URL a sidunni sem sott er gogn af. 
		 */
		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
				try { 
					Document doc = Jsoup.connect(url).get();
					Elements tableElements = doc.select("table");
			
					
					Elements tableClassesElements = tableElements.select(":not(thead) tr");
					String timar[] = {"", "morgun", "", "hadegi", "", "siddegi", "", "kvold"};
					String dagar[] = {"Man", "Tri", "Mid", "Fim", "Fos", "Lau", "Sun"};
					
					for (int i = 0; i < tableClassesElements.size(); i++) {
						Element row = tableClassesElements.get(i);
						Elements rowItems = row.select("td");
						String timi = timar[i];
						
						for (int j = 0; j < rowItems.size(); j++) {
							String dagur = dagar[j];
							Element list = rowItems.get(j);
							Elements listItems = list.select("li"); 
							
							for (int k = 0; k < listItems.size(); k++){
								String hopTimi[] = new String[9];
								Element links = listItems.get(k);
								hopTimi[0] = links.select("a").text();
								hopTimi[1] = links.select(".stod").text();
								hopTimi[2] = links.select(".salur").text();
								hopTimi[3] = links.select(".tjalfari").text();
								hopTimi[4] = links.select(".tegund").text();
								hopTimi[5] = links.select(".time").text();
								hopTimi[6] = timi;
								hopTimi[7] = dagur;
								Elements lokad = links.select(".locked");
								
								
								if (lokad.text() != "")
									hopTimi[8] = lokad.attr("title");
								else
									hopTimi[8] = " ";
								mDataSource.addHoptimi(hopTimi);
								
							}
						}
					}
				}
				catch ( UnknownHostException e ) {
//					TextView t = (TextView) rootView.findViewById(R.id.opnun_header);
//					t.setTextColor(Color.RED);
//					t.setText("Ekki náðist samband við vefþjón");
					//Toast.makeText(mContext,"Ekki náði tenging við vefþjón",Toast.LENGTH_LONG).show();
					System.out.println("Ekki náðist samband við vefþjón");
				}
				catch ( Exception e){
//					TextView t = (TextView) rootView.findViewById(R.id.opnun_header);
//					t.setTextColor(Color.RED);
//					t.setText("Villa kom upp við að ná tengingu við vefþjón");
					//Toast.makeText(mContext,"Villa kom upp við að ná tengingu við vefþjón",Toast.LENGTH_LONG).show();
					System.out.println("Villa kom upp við að ná tengingu við vefþjón");
				}
				return "All Done!";
		}
			
		/**
		 * Kallad reglulega a tetta fall medan a keyrslunni stendur.
		 * 
		 * @param values
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}
		
		/**
		 * Ef haett er vid keyrsluna adur en hun er fullklarud, ta keyra tetta fall
		 * 
		 * @see android.os.AsyncTask#onCancelled()
		 */
		@Override
		protected void onCancelled() {
			mDataSource.dropTable();
			super.onCancelled();
		}
	
		/**
		 * Tegar buid er ad hlada inn asynchronous verkinu, keyrir tetta fall sem birtir
		 * gognin i listanum.
		 * 
		 * @param result
		 */
		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			super.onPostExecute(result);
		}
	}

}
