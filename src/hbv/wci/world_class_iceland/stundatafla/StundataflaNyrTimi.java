package hbv.wci.world_class_iceland.stundatafla;

import android.os.Bundle;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
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
import hbv.wci.world_class_iceland.skraning.Innskraning;
import hbv.wci.world_class_iceland.stundatafla.AlmennStundatafla;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class StundataflaNyrTimi extends Activity {

	private DataSource mDataSource;
	public Context mContext = this;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private Spinner spinner;
	private String timiDags = "Tími dags";
	
	
//	private String vikudagur;
	private Map<String,Integer> map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nyrtimi);
		createMap();
		setSpinners();
		
		final EditText name = (EditText) findViewById(R.id.nafnNyrTimi);
		final Button timasetning = (Button) findViewById(R.id.NyrTimiKlukkan);
		final EditText lysing = (EditText) findViewById(R.id.lysingNyrTimi);
		final Button nySkra = (Button) findViewById(R.id.nySkraTima);
		final Spinner spinner = (Spinner) findViewById(R.id.spinner);
		final Context context = this;
		
		timasetning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(StundataflaNyrTimi.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    	String selectedMinuteString = Integer.toString(selectedMinute);
                    	String selectedHourString = Integer.toString(selectedHour);
                    	if(selectedMinute<10)
                    		selectedMinuteString = '0' + Integer.toString(selectedMinute);
                    	if(selectedHour<10)
                    		selectedHourString = '0' + Integer.toString(selectedHour);
                        timasetning.setText( "" + selectedHourString + ":" + selectedMinuteString);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Veldu tíma");
                mTimePicker.show();

            }
        });
		
		nySkra.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String nafnTima = name.getText().toString();
				String timasetningTima = timasetning.getText().toString();
				String vikudagur = String.valueOf(spinner.getSelectedItem());
				
				Boolean noEmptyField = isNotEmpty(name.getText().toString(), timasetning.getText().toString(), String.valueOf(spinner.getSelectedItem()));
				
				Boolean nameError = isName(nafnTima);
				Boolean timasetningError = isTimasetning(timasetningTima);
				Boolean spinnerError = isSpinner(vikudagur);
								
				if (noEmptyField)
				{
					showSuccessDialog();
					addToDatabase(nafnTima, timasetningTima, Global.weekdayFormatForDB(vikudagur), lysing.getText().toString());
				}			
				else{
					showFailureDialog(nameError, timasetningError, spinnerError);
				}
									
			}
		});
		
		setDrawer();
	}
	
	public void addToDatabase(String name, String time, String weekday, String descr){
		mDataSource = new DataSource(mContext);
		mDataSource.open();
		mDataSource.addEinkatimi(name,time,weekday, descr);
		
	}
	
	public void showSuccessDialog(){
		final Dialog dialog = new Dialog(mContext);
		dialog.setContentView(R.layout.dialog_nyskra);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setTitle("Til hamingju");
		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		String info ="Tíminn þinn verður skráður í þína stundatöflu.";
		info += "\nVinsamlegast ýttu á 'OK' til að halda áfram.";
								
		text.setText(info);
		
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Intent i = new Intent(StundataflaNyrTimi.this, StundataflanMin.class);
				startActivity(i);
			}
		});

		dialog.show();
	}
	
	public void showFailureDialog(Boolean nameError, Boolean timasetningError, Boolean spinnerError){
		final Dialog dialog = new Dialog(mContext);
		dialog.setContentView(R.layout.dialog_nyskra);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setTitle("Villa");

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		String villa ="Vinsamlegast fylltu út í alla reiti!";
		if(nameError)
			villa+="\nNafn tíma er tómt";
		if(timasetningError)
			villa+="\nTímasetning er ekki valin";
		if(spinnerError)
			villa+="\nTími dags er ekki valinn";
									
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
	}
	/**
	 * Upphafsstillir spinnerana sem eru valmyndin fyrir stundatofluna og setur a ta listener
	 */
	public void setSpinners() {
		spinner = (Spinner) findViewById(R.id.spinner);
		List<String> list1 = new ArrayList<String>();
		list1.add("Veldu vikudag");
		list1.add("Mánudagur");
		list1.add("Þriðjudagur");
		list1.add("Miðvikudagur");
		list1.add("Fimmtudagur");
		list1.add("Föstudagur");
		list1.add("Laugardagur");
		list1.add("Sunnudagur");
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item, list1);
		dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spinner.setAdapter(dataAdapter1);		
	}
	
	public static boolean isNotEmpty(String nameString, String timasetningString, String spinnerString){
		return !nameString.isEmpty() && !timasetningString.equals("Tímasetning") && !spinnerString.equals("Tími dags");	
	}
	
	public static boolean isName(String nameString){
		return nameString.isEmpty();	
	}
	
	public static boolean isTimasetning(String timasetningString){
		return timasetningString.equals("Tímasetning");
	}
	
	public static boolean isSpinner(String spinnerString){
		return spinnerString.equals("Veldu vikudag");
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
		// TODO er að vísa í opnun_menu sem er í Innskraning

		// check if user is logged in
		if(Global.currentUser == null) {
			Global.drawerListItems = new String[] {Global.ST1, Global.OPN};
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
				Intent i = new Intent(StundataflaNyrTimi.this, AlmennStundatafla.class);
				i.putExtra("vikudagur", Integer.toString(map.get(Global.dayOfWeek)));
				startActivity(i);
			} else if (str.equals(Global.ST2)){
				Intent i = new Intent(StundataflaNyrTimi.this, StundataflanMin.class);
				i.putExtra("vikudagur", Integer.toString(map.get(Global.dayOfWeek)));
				startActivity(i);
			} else if (str.equals(Global.OPN)){
				Intent i = new Intent(StundataflaNyrTimi.this, Opnunartimar.class);
				startActivity(i);
			} else if (str.equals(Global.UTS)) {
				Global.currentUser = null;
				//mDrawerToggle.syncState();
				
				Intent i = new Intent(StundataflaNyrTimi.this, Innskraning.class);
				i.putExtra("vikudagur", Integer.toString(map.get(Global.dayOfWeek)));
				startActivity(i);
			} else if (str.contains("@")) {
				//Intent i = new Intent(StundataflaNyrTimi.this, UmNotenda.class);
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
