package hbv.wci.world_class_iceland.skraning;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;

import hbv.wci.world_class_iceland.Global;
import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.R.drawable;
import hbv.wci.world_class_iceland.R.id;
import hbv.wci.world_class_iceland.R.layout;
import hbv.wci.world_class_iceland.R.string;
import hbv.wci.world_class_iceland.data.DataSource;
import hbv.wci.world_class_iceland.opnunartimar.Opnunartimar;
import hbv.wci.world_class_iceland.stundatafla.AlmennStundatafla;
import hbv.wci.world_class_iceland.stundatafla.StundataflanMin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Nyskraning extends Activity {
	private DataSource mDataSource;
	public Context mContext = this;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private String vikudagur;
	private Map<String,Integer> map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nyskraning);
		
		final EditText netfang = (EditText) findViewById(R.id.netfangInntak2);
		final EditText lykilord = (EditText) findViewById(R.id.lykilordInntakNr1);
		final EditText lykilord2 = (EditText) findViewById(R.id.lykilordInntakNr2);
		final EditText kennitala = (EditText) findViewById(R.id.kennitalaInntak);
		final Button nySkra = (Button) findViewById(R.id.nySkraTakki);
		final Context context = this;
		
		lykilord.setTypeface(Typeface.SANS_SERIF);
		lykilord2.setTypeface(Typeface.SANS_SERIF);
		
		nySkra.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDataSource = new DataSource(mContext);
				mDataSource.open();

				// TODO ef gefid upp netfang, lykilord og ytt a nyskra, baeta vid gognum inn i nyskraningar inputin
				//svo ekki turfi ad skrifa aftur
				Boolean validEmail = validate(netfang.getText().toString());
				Boolean noEmptyField = isNotEmptyNyskra(netfang.getText().toString(), lykilord.getText().toString(), kennitala.getText().toString(), lykilord2.getText().toString());
				Boolean validKennitala = isKennitala(kennitala.getText().toString());
				Boolean passwordMatch = passwordMatch(lykilord.getText().toString(), lykilord2.getText().toString());
				Boolean emailAvailable = !mDataSource.userExists(netfang.getText().toString());
				if (validEmail && noEmptyField && validKennitala && passwordMatch && emailAvailable)
				{
					final Dialog dialog = new Dialog(context);
					dialog.setContentView(R.layout.dialog_nyskra);
					dialog.setCanceledOnTouchOutside(false);
					dialog.setTitle("Til hamingju");
		 
					// set the custom dialog components - text, image and button
					TextView text = (TextView) dialog.findViewById(R.id.text);
					String info ="Þú ert nú skráður í kerfið. Upplýsingar þínar:";
					info += "\nnetfang: " + netfang.getText().toString();
					info += "\nkennitala: " + kennitala.getText().toString();
					info += "\nVinsamlegast ýttu á 'OK' til að halda áfram.";
											
					text.setText(info);
					
					Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
					// if button is clicked, close the custom dialog
					dialogButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
							
							Global.currentUser = netfang.getText().toString();
							mDataSource.addUser(new String[]{netfang.getText().toString(),lykilord.getText().toString(),kennitala.getText().toString(),"nei","nei"}, mContext);
							Intent j = new Intent(Nyskraning.this, AlmennStundatafla.class);
							startActivity(j);
						}
					});
		 
					dialog.show();
					
				}			
				else{
					final Dialog dialog = new Dialog(context);
					dialog.setContentView(R.layout.dialog_nyskra);
					dialog.setCanceledOnTouchOutside(false);
					dialog.setTitle("Villa");
		 
					// set the custom dialog components - text, image and button
					TextView text = (TextView) dialog.findViewById(R.id.text);
					String villa ="Vinsamlegast lagaðu eftirfarandi:";
					if(!emailAvailable)
						villa += "\nNetfang er þegar skráð.";
					else {
						if(!validEmail)
							villa += "\nNetfang er ekki á réttu formi.";
						if(!validKennitala)
							villa += "\nKennitala er ekki á réttu formi.";
						if(!passwordMatch)
							villa += "\nLykilorð stemma ekki.";
						if(!noEmptyField)
							villa += "\nFylla verður út í alla reiti.";
					}
								
					text.setText(villa);
					
					
					Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
					// if button is clicked, close the custom dialog
					dialogButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
					dialog.show();
					//Toast.makeText(Nyskraning.this, R.string.rangt, Toast.LENGTH_LONG).show();
				}
									
			}
		});
		
		setDrawer();
	}
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
	    return matcher.find();
	}
	public static boolean isNotEmptyNyskra(String email, String password, String kennitala, String lykilord2){
		return (!email.isEmpty() && !password.isEmpty() && !kennitala.isEmpty() && !lykilord2.isEmpty());	
	}
	public static boolean isKennitala(String kennitala){
		return(kennitala.length() == 10);
	}
	public static boolean passwordMatch(String lykilord1, String lykilord2){
		return(lykilord1.equals(lykilord2));
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
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mDrawerList.setItemChecked(position, true);
			mDrawerLayout.closeDrawer(mDrawerList);
			
			String str = Global.drawerListItems[position];
			if (str.equals(Global.ST1)) {
				Intent i = new Intent(Nyskraning.this, AlmennStundatafla.class);
				i.putExtra("vikudagur", Integer.toString(Global.map.get(Global.dayOfWeek)));
				startActivity(i);
			} else if (str.equals(Global.ST2)){
				Intent i = new Intent(Nyskraning.this, StundataflanMin.class);
				i.putExtra("vikudagur", Integer.toString(Global.map.get(Global.dayOfWeek)));
				startActivity(i);
			} else if (str.equals(Global.OPN)){
				Intent i = new Intent(Nyskraning.this, Opnunartimar.class);
				startActivity(i);
			} else if (str.equals(Global.UTS)) {
				Global.currentUser = null;
				//mDrawerToggle.syncState();
				
				Intent i = new Intent(Nyskraning.this, Innskraning.class);
				i.putExtra("vikudagur", Integer.toString(Global.map.get(Global.dayOfWeek)));
				startActivity(i);
			} else if (str.equals(Global.INS)) {
				Intent i = new Intent(Nyskraning.this, Innskraning.class);
				startActivity(i);
			} else if (str.contains("@")) {
				//Intent i = new Intent(Nyskraning.this, UmNotenda.class);
				//startActivity(i);
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
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
	
	public void setDrawer()	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_nyskraning);
		mDrawerList = (ListView) findViewById(R.id.left_drawer_nyskraning);

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
