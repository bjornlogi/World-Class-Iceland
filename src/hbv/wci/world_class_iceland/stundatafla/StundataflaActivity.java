package hbv.wci.world_class_iceland.stundatafla;

import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.R.id;
import hbv.wci.world_class_iceland.R.layout;

import java.util.ArrayList;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class StundataflaActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 7*5; //til ad "wrappa" stundatöflunni

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
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

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.viewpagermain);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        addItemsOnSpinner();
		addListenerOnSpinner();
        Intent myIntent= getIntent();
		String vikudagur = myIntent.getStringExtra("vikudagur");
		currentPos=7*2+Integer.parseInt(vikudagur);
        mPager.setCurrentItem(currentPos);//viljum byrja i midjunni  
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        
    }
    
    public void addItemsOnSpinner() {
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
			    public void onNothingSelected(AdapterView<?> parentView) {
			    }

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
			    public void onNothingSelected(AdapterView<?> parentView) {
			    }

			});
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
}
