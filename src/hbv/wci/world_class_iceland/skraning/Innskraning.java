package hbv.wci.world_class_iceland.skraning;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import hbv.wci.world_class_iceland.data.DataSource;
import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.stundatafla.AlmennStundatafla;
import hbv.wci.world_class_iceland.Global;

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
	
	/**
	 * Stillir listener sem bidur eftir ad ytt er a nyskra takkann
	 * 
	 */
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
	
	/**
	 * Stillir listener sem bidur eftir ad ytt er a innskra takkann.
	 * 
	 */
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
	/**
	 * Hondlar atburdin tegar innskraning tekst
	 * 
	 * @param netfang
	 */
	private void loginSuccessful(String netfang){
		Global.currentUser = netfang;
		Intent i = new Intent(Innskraning.this, AlmennStundatafla.class);
		startActivity(i);
	}
	
	/**
	 * Hondlar atburdin tegar innskraning tekst ekki
	 * 
	 */
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
	
	public void setDrawer()	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_innskraning);
		mDrawerList = (ListView) findViewById(R.id.left_drawer_innskraning);
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
	
	@Override
	public void onBackPressed() {
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
