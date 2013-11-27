package hbv.wci.world_class_iceland.stundatafla;

import android.os.Bundle;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Dialog;
import android.content.res.Configuration;



import hbv.wci.world_class_iceland.Global;
import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.data.DataSource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class StundataflaNyrTimi extends Activity {

	private DataSource mDataSource;
	public Context mContext = this;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private Spinner spinner;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nyrtimi);
		
		setSpinners();
		setNyskraListener();
		setTimasetningListener();		
		setDrawer();
	}
	
	private void setTimasetningListener(){
		final Button timasetning = (Button) findViewById(R.id.NyrTimiKlukkan);
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
	}
	
	private void setNyskraListener(){
		final Button nySkra = (Button) findViewById(R.id.nySkraTima);
		final EditText name = (EditText) findViewById(R.id.nafnNyrTimi);
		final Button timasetning = (Button) findViewById(R.id.NyrTimiKlukkan);
		final EditText lysing = (EditText) findViewById(R.id.lysingNyrTimi);
		
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
	}
	
	private void addToDatabase(String name, String time, String weekday, String descr){
		mDataSource = new DataSource(mContext);
		mDataSource.open();
		mDataSource.addEinkatimi(name,time,weekday, descr);
		
	}
	
	private void showSuccessDialog(){
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
	
	private void showFailureDialog(Boolean nameError, Boolean timasetningError, Boolean spinnerError){
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
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, Global.determineListItems(mContext)));
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) return true;
		return super.onOptionsItemSelected(item);
	}
	
	public void setDrawer()	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_nyskraning);
		mDrawerList = (ListView) findViewById(R.id.left_drawer_nyskraning);
		mDrawerToggle = Global.setDrawer(mContext, mDrawerLayout, mDrawerList, this);
	}
	 
}
