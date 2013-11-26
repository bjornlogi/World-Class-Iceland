package hbv.wci.world_class_iceland.stundatafla;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import hbv.wci.world_class_iceland.opnunartimar.Opnunartimar;
import hbv.wci.world_class_iceland.Global;
import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.skraning.Innskraning;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity sem birtir almennu stundatoflu forritsins ur gagnagrunni i ListView.
 * 
 * 
 * @author Bjorn
 *
 */
public class AlmennStundatafla extends FragmentActivity implements StundataflaVidmot {
	/*
	 * The number of pages (wizard steps) to show in this demo.
	 */
	private static final int NUM_PAGES = 7*5; //til ad "wrappa" stundatöflunni
	public Context mContext = this;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	/*
	 * Pager widget, hondlar animationid og hondlar listenerinn og utfaersluna a "swipe"inu
	 */
	private ViewPager mPager;

	/*
	 * Pager adapter, sem birtir stundatofluna
	 */
	private PagerAdapter mPagerAdapter;
	private Spinner spinner1, spinner2;
	private String stod = "Allar stöðvar";
	private String tegund = "Allar tegundir";
	private int currentPos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stundatafla);

		setPagerAndAdapter();
		setDate();
		setSpinners();
		setDrawer();
	}
	/*
	 * Tegar ytt er a innbyggda android til baka takkann fer hann ekki til baka um eina sidu, heldur
	 * til baka um Intent
	 */
	@Override
	public void onBackPressed() {
		if (!Global.isUserLoggedIn(this))
			super.onBackPressed();
	}
	/**
	 * Stilli pager og adapterinn fyrir stundatofluna
	 */
	public void setPagerAndAdapter(){
		mPager = (ViewPager) findViewById(R.id.viewpagermain);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
	}
	
	/**
	 * Finnur hvada dagur er til ad akveda hvada sidu a ad birta fyrst, t.e. dagurinn i dag
	 */
	public void setDate(){
		currentPos = 7*2 + Global.map.get(Global.dayOfWeek);
		mPager.setCurrentItem(currentPos);//viljum byrja i midjunni  
	}
	/**
	 * Upphafsstillir spinnerana sem eru valmyndin fyrir stundatofluna og setur a ta listener
	 */
	public void setSpinners() {
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		List<String> list1 = new ArrayList<String>();
		list1.add("Allar stöðvar");
		list1.add("Laugar");
		list1.add("Kringlan");
		list1.add("Ögurhvarf");
		list1.add("Egilshöll");
		list1.add("Mosfellsbær");
		list1.add("Seltjarnarnes");
		list1.add("Spöng");
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
		dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spinner1.setAdapter(dataAdapter1);
		
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		List<String> list2 = new ArrayList<String>();
		list2.add("Allar tegundir");
		list2.add("Brennsla / mótun");
		list2.add("Dans");
		list2.add("Hardcore / alhliða þjálfun");
		list2.add("Hardcore/alhliða");
		list2.add("Lífsstílsnámskeið");
		list2.add("Líkami og sál");
		list2.add("Rólegt");
		list2.add("Spor");
		list2.add("Styrkur");
		list2.add("óflokkað");
		ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
		dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spinner2.setAdapter(dataAdapter2);
		
		addListenerOnSpinner();
		
	}
	
	
	/**
	 * fengin eru gildi þeirra staka sem valin voru úr dropdownlistanum 
	 */
	private boolean first = true;
	private boolean second = true;
	public void addListenerOnSpinner() {
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				stod = String.valueOf(spinner1.getSelectedItem());
				if (first)
					first = !first;
				else
					mPagerAdapter.notifyDataSetChanged();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parentView) {}

		});
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				tegund = String.valueOf(spinner2.getSelectedItem());
				if (second)
					second = !second;
				else
					mPagerAdapter.notifyDataSetChanged();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parentView) {}

		});
	}
	
	public void setDrawer()	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_stundatafla);
		mDrawerList = (ListView) findViewById(R.id.left_drawer_stundatafla);
		mDrawerToggle = Global.setDrawer(mContext, mDrawerLayout, mDrawerList, this);
	}
	
	/**
	 * Pager Adapter sem stýrir fragmentunum.
	 * 
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}
		int i = 0;
		@Override
		public Fragment getItem(int position) {
			StundataflaFragment pf = new StundataflaFragment();
			Bundle bdl = prepareBundle(position);
			pf.setArguments(bdl);
			return pf;
		}
		
		private Bundle prepareBundle(int position){
			Bundle bdl = new Bundle(3);
			bdl.putInt("position", position%7);
			bdl.putInt("update", i++);
			bdl.putBoolean("isCurrent", position == mPager.getCurrentItem());
			bdl.putString("stod", stod);
			bdl.putString("tegund", tegund);
			return bdl;
		}
		
		/**
		 * Fall sem er kallad eftir notifyDataSetChanged(), viljum lata refresha
		 * 
		 */
		public int getItemPosition(Object item) {
			return POSITION_NONE;
		}
		
		@Override
		public int getCount() {
			return NUM_PAGES;
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
		if (mDrawerToggle.onOptionsItemSelected(item)) return true;
		return super.onOptionsItemSelected(item);
	}
	
	
}
