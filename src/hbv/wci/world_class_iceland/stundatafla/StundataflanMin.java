package hbv.wci.world_class_iceland.stundatafla;

import java.util.Calendar;
import java.util.HashMap;

import hbv.wci.world_class_iceland.Global;
import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.data.DataSource;
import hbv.wci.world_class_iceland.data.StundatofluTimi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity fyrir personulegu stundatoflu notendans.
 * 
 * @author Bjorn
 *
 */
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
		createTimetable();
		
	}
	
	private void createTimetable(){
		st = data.getAllStundataflanMinTimi(mContext);
		if (st.isEmpty()){
			makeEmptyMessageUnclickable();
		}
		else{
			displayTimetable();
			}
		expandAllGroups();
	}
	
	private void expandAllGroups(){
		for (int position = 0; position < listAdapter.getGroupCount(); position++)
			expListView.expandGroup(position);
	}
	private void makeEmptyMessageUnclickable(){
		listAdapter = new ExpandableListAdapter(mContext, st.listHeader, st.listChild, st.infoChild){
			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition){
				return false;
			}
		};
		expListView.setAdapter(listAdapter);
	}
	
	private void displayTimetable(){
		listAdapter = new ExpandableListAdapter(this, st.listHeader, st.listChild, st.infoChild);
		expListView.setAdapter(listAdapter);
		setListListener();
	}
	
	private void setListListener(){
		
		expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			 
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				
				final String selectedUntrimmed = (String) listAdapter.getChild(groupPosition, childPosition);
				final int getMoney = selectedUntrimmed.indexOf("$");
				
				final boolean einkatimi = isClickedEinka(selectedUntrimmed);
				//erum buin ad finna ut hvort ad timi se einkatimi eda ekki, svo vid viljum ekki 'e' a endanum
				//lengur
				String selected;
				if (einkatimi)
					selected = selectedUntrimmed.substring(0,selectedUntrimmed.length()-1);
				else
					selected = selectedUntrimmed;
				
				createAminningDialog(selected,getMoney,einkatimi);
			
				return true;
			}
			
			
		});
	}
	
	private void createAminningDialog(final String selected, final int getMoney, final boolean einkatimi){
		final Dialog dialog = new Dialog(mContext);
		dialog.setContentView(R.layout.dialog_min_stundatafla);
		dialog.setCanceledOnTouchOutside(true);
		final String justTheID = selected.substring(getMoney+3,selected.length());
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
				data.deleteNotendatimi(uid, justTheID, einkatimi);
				data.updateAminning("false", justTheID);
				createTimetable();
				dialog.dismiss();
			}
		});
		
		onAminningClickListener(dialog, selected,getMoney);
		dialog.show();		
	}
	
	private void onAminningClickListener(final Dialog dialog, final String selected, final int getMoney){
		checkbox_aminning = (CheckBox) dialog.findViewById(R.id.checkbox_aminning);
		
		if(data.getAminning(selected.substring(getMoney+3)).equals("true")) {
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

				//int getMoney2 = selected.indexOf("$");
				//String id = selected.substring(getMoney2+1,selected.length());
				//System.out.println(einkatimi);
				//System.out.println(id);
				//String info_time = st.infoChild.get(id);
				//String[] s = info_time.split(" - ");
				//String hour = s[0].split(":")[0];
				//String min = s[0].split(":")[1];	
				String justTheID = selected.substring(getMoney+3,selected.length());
					
				
				
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
				int sec = secondsTillNotification(justTheID);
				calendar.add(Calendar.SECOND, sec);
				
				System.out.println(sec);
				
				if (checkbox_aminning.isChecked()) {
					data.updateAminning("true", justTheID);
					alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
					Toast.makeText(mContext, "Áminning hefur verið skráð", Toast.LENGTH_LONG).show();
				}
				else {
					data.updateAminning("false", justTheID);
					Toast.makeText(mContext, "Áminning hefur verið afskráð", Toast.LENGTH_LONG).show();
				}
				dialog.dismiss();
			}
		});
	}
	/**
	 * Finnur ut hvad tad eru margar sekundur tangad til tad er klukkutimi i timann
	 * @param justTheID - audkenni timans
	 * @return sekundur tangad til tad er klukkutimi i ad timinn byrjar
	 */
	public int secondsTillNotification(String justTheID){
		String[] uppl = data.getHoptimarInfo(Integer.parseInt(justTheID));
		HashMap<String, Integer> map = Global.map;
		int today = map.get(Global.dayOfWeek);
		
		int weekDay = Global.mapIS.get(uppl[7]);
		//if (weekDay == 8) weekDay = 1;
		
		String klukkan = uppl[5];
		String[] parts = klukkan.split(" - ");
		String[] timi1 = parts[0].split(":");
		String timi1klst = timi1[0];
		String timi1min = timi1[1];
		int secondsFromMidnight1 = (Integer.parseInt(timi1klst)-1)*60*60 + Integer.parseInt(timi1min)*60;
		//System.out.println((Integer.parseInt(timi1klst)-1)*60*60 + Integer.parseInt(timi1min)*60);
		
		String klukkanNuna = Global.timeRightNow();
		String[] parts2 = klukkanNuna.split(" - ");
		String[] timi2 = parts2[0].split(":");
		String timi2klst = timi2[0];
		String timi2min = timi2[1];
		int secondsFromMidnight2 = Integer.parseInt(timi2klst)*60*60 + Integer.parseInt(timi2min)*60;
		
		int secondsTillTakeOff =  secondsFromMidnight1 - secondsFromMidnight2;
		if (today == weekDay && secondsTillTakeOff > 0)
			return secondsTillTakeOff;
		else if (today == weekDay && secondsTillTakeOff < 0)
			return secondsTillTakeOff + Global.secondsInADay*7;
		else if (today < weekDay)
			return secondsTillTakeOff + Global.secondsInADay*(weekDay - today);
		else
			return secondsTillTakeOff + Global.secondsInADay*(7+(weekDay - today));
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
		mDrawerToggle = Global.setDrawer(mContext, mDrawerLayout, mDrawerList, this);
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
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, Global.determineListItems(mContext)));
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
