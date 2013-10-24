package hbv.wci.world_class_iceland;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class ViewPageActivity extends FragmentActivity {
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
    
   
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.viewpagermain);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        Intent myIntent= getIntent();
		String vikudagur = myIntent.getStringExtra("vikudagur");
        mPager.setCurrentItem(7*2+Integer.parseInt(vikudagur));//viljum byrja i midjunni        
    }

    @Override
    public void onBackPressed() {
        
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        
    }
    
    
    /**
     * Pager Adapter sem stýrir fragmentunum.
     * 
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        

        @Override
        public Fragment getItem(int position) {
        	StundataflaFragment pf = new StundataflaFragment();
        	Bundle bdl = new Bundle(1);
            bdl.putInt("position", position%7);
            pf.setArguments(bdl);
            return pf;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
