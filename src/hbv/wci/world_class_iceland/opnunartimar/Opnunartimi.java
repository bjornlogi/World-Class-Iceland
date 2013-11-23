package hbv.wci.world_class_iceland.opnunartimar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import hbv.wci.world_class_iceland.Global;
import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.R.id;
import hbv.wci.world_class_iceland.R.layout;
import hbv.wci.world_class_iceland.data.Stod;
import hbv.wci.world_class_iceland.skraning.Innskraning;
import hbv.wci.world_class_iceland.stundatafla.StundataflaActivity;

/**
 * Activity sem synir opnunartima einnar stodvar.
 * 
 * @author Bjorn
 * @see Activity
 */
public class Opnunartimi extends Activity implements OpnunStodVidmot{
	private Context mContext = this;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private String vikudagur;
	private Map<String,Integer> map;
	private String stod;
	
	/**
	 * Birtir skja fyrir ta stod sem valin var.
	 * Finnur ut hvada stod er valin med ad na i skilabod sent med Intent. Birtir allar upplysingar
	 * byggdar a tvi.
	 * 
	 * @param savedInstanceState
	 * @return none
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opnunartimi);
		
		setTitle();
		setDate();
		createMap();
		//naum i opnunartimana i dag fyrir stodina
		Stod timarObj = new Stod(stod); 
		String opnunIDag = timarObj.OpnunFyrirDag(this.vikudagur);	
		birtaErOpid(opnunIDag);		
		birtaMynd(stod);
		birtaOpnunartima(timarObj);
		
		/*
		 * CREATE DRAWER
		 */
		setDrawer();
		
	}
	
	/**
	 * Birtir titilinn, t.e.a.s. hvada stod hefur verid valin og stillir global breytuna stod
	 */
	
	public void setTitle(){
		Intent myIntent= getIntent();
		stod = myIntent.getStringExtra("stod");
	
		TextView titill = (TextView)findViewById(R.id.otimi_titill);
		titill.setText(stod);
	}
	
	public void setDate(){
		TimeZone T1 = TimeZone.getTimeZone("GMT"); 
		SimpleDateFormat DOW = new SimpleDateFormat ("EEE");
		
		DOW.setTimeZone(T1);
		
		this.vikudagur = DOW.format(new Date());
	}
	
	/**
	 * Finnur annad hvort vinstri eda haegri hluta strengsins date
	 * 
	 * @param date strengur a forminu "hh:mm-hh:mm"
	 * @param OpnunEdaLokun 0 fyrir opnun, 1 fyrir lokun
	 * @return String opnunatima eda lokunartima
	 */
	public String SplittIOpnunOgLokun (String date, int OpnunEdaLokun) {
		//splittum i opnun og lokun
		String[] parts = date.split("-");
		String timaStrengur = parts[OpnunEdaLokun];
		return timaStrengur;
	}
	
	/**
	 * Birtir "Opid" ef tad er opid nuna, annars "Lokad"
	 * 
	 * @param opnunIDag
	 * @param klukkanNuna
	 */
	public void birtaErOpid (String opnunIDag) {
		
		TimeZone T1 = TimeZone.getTimeZone("GMT"); 
		SimpleDateFormat klukkan = new SimpleDateFormat ("HH:mm");
		klukkan.setTimeZone(T1);
		String klukkanNuna = klukkan.format(new Date());
		
		TextView opidTV = (TextView)findViewById(R.id.opid);
		Boolean opid;
		
		if (opnunIDag.equals("Lokað")) {
			opid = false;
		} else {
			String opnun = SplittIOpnunOgLokun(opnunIDag,0);
			String lokun = SplittIOpnunOgLokun(opnunIDag,1);
			opid = isBetween(opnun,klukkanNuna, lokun);
		}
		
		if (opid) {
			opidTV.setText("Opið");
			opidTV.setTextColor(Color.parseColor("#105420"));		
		} else {
			opidTV.setText("Lokað");
			opidTV.setTextColor(Color.RED);
		}
		opidTV.setTypeface(null, Typeface.BOLD);
	}
	
	/**
	 * Setur inn tilsvarandi strengi inn i id.opnun_dagar1, id.opnun_timar1 og id.skilabod
	 * 
	 * @param timar stodin sem a ad birta
	 * @see Stod
	 */
	public void birtaOpnunartima (Stod timar) {
		TextView opnunardagar1 = (TextView)findViewById(R.id.opnun_dagar1);
		TextView opnunartimar1 = (TextView)findViewById(R.id.opnun_timar1);
		
		String[] opntimar = new String[] {timar.OpnunFyrirDag("Mon"), timar.OpnunFyrirDag("Tue"), timar.OpnunFyrirDag("Wed"), timar.OpnunFyrirDag("Thu"), timar.OpnunFyrirDag("Fri"), timar.OpnunFyrirDag("Sat"), timar.OpnunFyrirDag("Sun")};
		String[] vikudagar = new String[] {"Mán","Þri", "Mið", "Fim", "Fös", "Lau", "Sun"};
		
		
		int start = Integer.parseInt(timar.OpnunFyrirDag("hvar á að byrja"));
		String opnunardagar = timar.OpnunFyrirDag("margir eins") + "\n";
		String opnunartimar = opntimar[0];
		TextView ts = (TextView)findViewById(R.id.skilabod);
		if (timar.Taekjasalur()) {
			opnunartimar += "*";
		}
		ts.setText(timar.Skilabod());
		opnunartimar += "\n";
		
		for (int i = start; i<7; i++){
			String substring = vikudagar[i] + ":\n";
			opnunardagar += substring;
			String substring2 = opntimar[i];
			if (timar.Taekjasalur()) {
				substring2 += "*";
			}
			substring2 += "\n";
			opnunartimar += substring2;
		}
		opnunardagar1.setText(opnunardagar);
		opnunartimar1.setText(opnunartimar);
		
	}
	
	/**
	 * Setur mynd af stodinni inn i ImageView med ID-id stod_mynd
	 * 
	 * @param stod nafn a stod
	 */
	public void birtaMynd (String stod) { 
		ImageView image;
		String icon = deUTFfy(stod) + "mynd";
		
		image = (ImageView)findViewById(R.id.stod_mynd);
		
		int resID = getResources().getIdentifier(icon, "drawable",  getPackageName()); 
	    image.setImageResource(resID);
	}
	
	/**
	 * Skilar nyjum streng sem er byggdur a inntaki sem er ekki med ser islenskum stofum
	 * 
	 * @param s hvada strengur sem er
	 * @return s med ollum islenskum stofum breytt i sambaerilega ascii stafi, engin bil og allt i litlum stofum
	 */
	public String deUTFfy(String s) {
		s = s.toLowerCase();
		s = s.replaceAll("ö","o");
		s = s.replaceAll("í","i");
		s = s.replaceAll("á","a");
		s = s.replaceAll("æ","ae");
		s = s.replaceAll("ú","u");
		s = s.replaceAll("þ","t");
		s = s.replaceAll("ó","o");
		s = s.replaceAll("ð","d");
		s = s.replaceAll(" ", "");
		
		return s;
	}
	
	/**
	 * Kemst ad tvi hvort timinn second se a milli first og third.
	 * 
	 * @param first strengur a forminu "hh:mm"
	 * @param second strengur a forminu "hh:mm"
	 * @param third strengur a forminu "hh:mm"
	 * @return true ef first<second<third, false annars
	 */
	public boolean isBetween(String first, String second, String third) {
		String []firstParts = first.split(":");
		String []secondParts = second.split(":");
		String []thirdParts = third.split(":");
		
		int firstKlu = Integer.parseInt(firstParts[0]);
		int firstMin = Integer.parseInt(firstParts[1]);
		int secondKlu = Integer.parseInt(secondParts[0]);
		int secondMin = Integer.parseInt(secondParts[1]);
		int thirdKlu = Integer.parseInt(thirdParts[0]);
		int thirdMin = Integer.parseInt(thirdParts[1]);
		
		if (secondKlu > firstKlu && secondKlu < thirdKlu) {
			return true;
		} else if (secondKlu == thirdKlu) {
			if (secondMin < thirdMin) {
				return true;
			} else {
				return false;
			}
		}
		if (firstKlu == secondKlu) {
			if (secondMin > firstMin) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
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
			
			String str = Global.drawerListItems[position];
			if (str.equals(Global.ST1)) {
				Intent i = new Intent(Opnunartimi.this, StundataflaActivity.class);
				i.putExtra("vikudagur", Integer.toString(Global.map.get(Global.dayOfWeek)));
				startActivity(i);
			} else if (str.equals(Global.ST2)){
				/*
				Intent i = new Intent(Opnunartimi.this, ?.class);
				i.putExtra("vikudagur", Integer.toString(Global.map.get(Global.dayOfWeek)));
				startActivity(i);
				*/
			} else if (str.equals(Global.OPN)){
				Intent i = new Intent(Opnunartimi.this, Opnunartimar.class);
				startActivity(i);
			} else if (str.equals(Global.UTS)) {
				Global.currentUser = null;
				Global.currentUserID = -1;
				SharedPreferences pref = mContext.getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
				Editor editor = pref.edit();
				editor.clear();
				editor.commit();
				
				Intent i = new Intent(Opnunartimi.this, Innskraning.class);
				//i.putExtra("vikudagur", Integer.toString(Global.map.get(Global.dayOfWeek)));
				startActivity(i);
			} else if (str.equals(Global.INS)) {
				Intent i = new Intent(Opnunartimi.this, Innskraning.class);
				startActivity(i);
			} else if (str.contains("@")) {
				//Intent i = new Intent(Opnunartimi.this, UmNotenda.class);
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
		if(Global.currentUser == null) {
			Global.drawerListItems = new String[] {Global.ST1, Global.OPN, Global.INS};
		} else {
			Global.drawerListItems = new String[] {Global.currentUser, Global.ST1, Global.ST2, Global.OPN, Global.UTS};
		}
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, Global.drawerListItems));
		
		//boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//menu.findItem(R.id.opnun_menu).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	
	public void createMap(){
		map = new HashMap<String,Integer>();
		map.put("Mon", 0);
		map.put("Tue", 1);
		map.put("Wed", 2);
		map.put("Thu", 3);
		map.put("Fri", 4);
		map.put("Sat", 5);
		map.put("Sun", 6);
	}
	
	public void setDrawer()	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_opnunartimi);
		mDrawerList = (ListView) findViewById(R.id.left_drawer_opnunartimi);

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
