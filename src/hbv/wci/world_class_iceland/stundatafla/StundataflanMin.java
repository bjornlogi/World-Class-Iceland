package hbv.wci.world_class_iceland.stundatafla;

import java.util.Calendar;
import java.util.List;

import hbv.wci.world_class_iceland.Global;
import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.data.DataSource;
import hbv.wci.world_class_iceland.data.StundatofluTimi;
import hbv.wci.world_class_iceland.opnunartimar.Opnunartimar;
import hbv.wci.world_class_iceland.skraning.Innskraning;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StundataflanMin extends Activity {
	public Context mContext = this;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private ExpandableListAdapter listAdapter;
	private DataSource data = new DataSource(this);
	private PendingIntent pendingIntent;
	public StundatofluTimi st;
	public CheckBox checkbox_aminning;
	private ExpandableListView expListView;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stundataflanmin);
		
		setDrawer();
		setOnButtonClickedListener();
		data.open();
		
		
		
		expListView = (ExpandableListView) findViewById(R.id.stundataflan);
		setAdapter();
		
	}
	
	private void setAdapter(){
		st = data.getAllStundataflanMinTimi(mContext);
		listAdapter = new ExpandableListAdapter(this, st.listHeader, st.listChild, st.infoChild);
		expListView.setAdapter(listAdapter);
		setListListener();
	}
	
	private void setListListener(){
		
		expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			 
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				
				final String selected = (String) listAdapter.getChild(groupPosition, childPosition);
				final int getMoney = selected.indexOf("$");
				
				final boolean einkatimi = isClickedEinka(selected);
				String newS = selected;
				//erum buin ad finna ut hvort ad timi se einkatimi eda ekki, svo vid viljum ekki 'e' a endanum
				//lengur
				if (einkatimi) newS = newS.substring(0,selected.length()-1);
				
				//Toast.makeText(getActivity(), selected + "\nUserID is " + userID, Toast.LENGTH_LONG).show();
				//System.out.println(Global.isUserLoggedIn(getActivity()));

				
				final Dialog dialog = new Dialog(mContext);
				dialog.setContentView(R.layout.dialog_min_stundatafla);
				dialog.setCanceledOnTouchOutside(true);
				
				dialog.setTitle( selected.substring(0, getMoney) );
			 
				// set the custom dialog components - text, image and button
				TextView text = (TextView) dialog.findViewById(R.id.text_eyda);
				String info = "Viltu eyða tímanum úr þinni stundatöflu?";					
				text.setText(info);
				
				TextView text2 = (TextView) dialog.findViewById(R.id.text_aminning);
				String info2 = "Viltu áminningu?";					
				text2.setText(info2);
			
				Button buttonLoka = (Button) dialog.findViewById(R.id.dialog_loka);
				// if button is clicked, close the custom dialog
				buttonLoka.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				
				Button buttonEyda = (Button) dialog.findViewById(R.id.dialog_eyda);
				
				buttonEyda.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int uid = Global.getUsersID(mContext);
						data.deleteNotendatimi(uid, selected.substring(getMoney+3), einkatimi);
						setAdapter();
						dialog.dismiss();
					}
				});
				
				checkbox_aminning = (CheckBox) dialog.findViewById(R.id.checkbox_aminning);
				
				if(data.getAminning(newS.substring(getMoney+3)).equals("true")) {
					checkbox_aminning.setChecked(true);
				}
				else {
					checkbox_aminning.setChecked(false);
				}
				checkbox_aminning.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						
						Intent myIntent = new Intent(mContext, AminningService.class);
						pendingIntent = PendingIntent.getService(mContext, 0, myIntent, 0);
						AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Service.ALARM_SERVICE);

						int getMoney2 = selected.indexOf("$");
						String id = selected.substring(getMoney2+1,selected.length());
						//System.out.println(einkatimi);
						//System.out.println(id);
						//String info_time = st.infoChild.get(id);
						//String[] s = info_time.split(" - ");
						//String hour = s[0].split(":")[0];
						//String min = s[0].split(":")[1];	
						String justTheID;
						if(einkatimi)
							justTheID = selected.substring(getMoney2+3,selected.length()-1);
						else
							justTheID = selected.substring(getMoney2+3,selected.length());
							
						
						String[] uppl = data.getHoptimarInfo(Integer.parseInt(justTheID));						
						
						int weekDay = Global.mapIS.get(uppl[7]);
						weekDay += 2;
						if (weekDay == 8) weekDay = 1;
						
						System.out.println(weekDay);
						
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(System.currentTimeMillis());
						
						//calendar.set(Calendar.DAY_OF_WEEK, weekDay);
						//calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour)-1);
						//calendar.set(Calendar.MINUTE, Integer.parseInt(min));
						//calendar.set(Calendar.MONTH, 11);
						//calendar.set(Calendar.YEAR, 2013);				
						//calendar.set(Calendar.DAY_OF_MONTH, 24);
						//calendar.set(Calendar.HOUR_OF_DAY, 15);
						//calendar.set(Calendar.MINUTE, 16);
						//calendar.set(Calendar.SECOND, 0);
						
						calendar.add(Calendar.SECOND, 5);
						
						
						
						if (checkbox_aminning.isChecked()) {
							data.updateAminning("true", justTheID);
							alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
							Toast.makeText(mContext, "Áminning hefur verið skráð", Toast.LENGTH_LONG).show();
						}
						else {
							data.updateAminning("false", justTheID);
							Toast.makeText(mContext, "Áminning hefur verið afskráð", Toast.LENGTH_LONG).show();
						}

					}
				});
				dialog.show();
			
				return true;
			}
			
			
		});
	}
	
	private boolean isClickedEinka(String selected){
		return selected.indexOf('e', selected.length()-1) != -1;
	}
	
	private void setOnButtonClickedListener(){
		final Button skraNyjanTima = (Button) findViewById(R.id.skraNyjanTima);
		skraNyjanTima.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent j = new Intent(StundataflanMin.this, StundataflaNyrTimi.class);
				startActivity(j);
			}
		});
	}
	
	public void setDrawer()	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_stundataflan_min);
		mDrawerList = (ListView) findViewById(R.id.left_drawer_stundataflan_min);

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
	
	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mDrawerList.setItemChecked(position, true);
			mDrawerLayout.closeDrawer(mDrawerList);
			
			String str = Global.drawerListItems[position];
			if (str.equals(Global.ST1)) {
				Intent i = new Intent(StundataflanMin.this, AlmennStundatafla.class);
				//i.putExtra("vikudagur", Integer.toString(Global.map.get(Global.dayOfWeek)));
				startActivity(i);
			} else if (str.equals(Global.ST2)){
				
				Intent i = new Intent(StundataflanMin.this, StundataflanMin.class);
				//i.putExtra("vikudagur", Integer.toString(Global.map.get(Global.dayOfWeek)));
				startActivity(i);
				
			} else if (str.equals(Global.OPN)){
				Intent i = new Intent(StundataflanMin.this, Opnunartimar.class);
				startActivity(i);
			} else if (str.equals(Global.UTS)) {
				Global.currentUser = null;
				Global.currentUserID = -1;
				SharedPreferences pref = mContext.getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
				Editor editor = pref.edit();
				editor.clear();
				editor.commit();
				
				Intent i = new Intent(StundataflanMin.this, Innskraning.class);
				//i.putExtra("vikudagur", Integer.toString(Global.map.get(Global.dayOfWeek)));
				startActivity(i);
			} else if (str.equals(Global.INS)) {
				Intent i = new Intent(StundataflanMin.this, Innskraning.class);
				startActivity(i);
			} else if (str.contains("@")) {
				//Intent i = new Intent(StundataflanMin.this, UmNotenda.class);
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
}
