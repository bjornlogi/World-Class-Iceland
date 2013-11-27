package hbv.wci.world_class_iceland.opnunartimar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import hbv.wci.world_class_iceland.Global;
import hbv.wci.world_class_iceland.R;


/**
 * Activity sem synir valmynd til tess ad sja opnunartima stodva. 
 * 
 * @author Bjorn
 * @see Activity
 */
public class Opnunartimar extends Activity implements OpnunVidmot {
	private Context mContext = this;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	
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
				
		setTakkar(); 
		setDrawer();
	}
	
	/**
	 * Upphafsstillir takka og setur a ta listener
	 * 
	 */
	public void setTakkar(){
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
			}
		});
		
		kringlan_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Kringlan");
			}
		});
		
		laugar_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Laugar");
			}
		});
		
		egils_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Egilshöll");
			}
		});
		
		hfj_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Hafnarfjörður");
			}
		});
		
		nes_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Seltjarnarnes");
			}
		});
		
		mosfells_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Mosfellsbær");
			}
		});
		
		ogur_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				createIntent("Ögurhvarf");
			}
		});
		
		kpv_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{
				createIntent("Kópavogur");
			}
		});
		
		hr_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{
				createIntent("Háskólinn í Reykjavík");
			}
		});
	}
	
	/**
	 * Tar sem oll intent eru a sama formi var taegilast ad bua til fall
	 * 
	 */
	public void createIntent(String stod){
		Intent i = new Intent(this, Opnunartimi.class);
		i.putExtra("stod", stod);
		startActivity(i);
	}
	
	/**
	 * Stillir navigationid
	 * 
	 */
	public void setDrawer()	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_opnunartimar);
		mDrawerList = (ListView) findViewById(R.id.left_drawer_opnunartimar);
		mDrawerToggle = Global.setDrawer(mContext, mDrawerLayout, mDrawerList, this);
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
		if (mDrawerToggle.onOptionsItemSelected(item)) return true;
		return super.onOptionsItemSelected(item); 
	}
	
	/**
	 * Keyrt eftir postCreateFallid til ad samstilla mDrawerToggle vid astand activitysins
	 * 
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}
	
	/**
	 * Allar breytingar a stillingum eru sendar yfir i drawerinn
	 * 
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggle
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	/**
	 * Undirbyr listann eftir tvi hvort notandinn se skradur inn eda ekki
	 * 
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, Global.determineListItems(mContext)));
		return super.onPrepareOptionsMenu(menu);
	}
	
	/**
	 * Leyfir notenanum ad yta a til baka takkann ef hann er ekki skradur inn, annars tarf ad nota navDrawer
	 * Hugsunin a bak vid tetta er svo ad innskradur notandi komist aldrei a innskraningar siduna.
	 * 
	 */
	@Override
	public void onBackPressed() {
		if (!Global.isUserLoggedIn(this))
			super.onBackPressed();
	}
	
}
