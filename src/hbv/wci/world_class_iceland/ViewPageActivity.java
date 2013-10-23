package hbv.wci.world_class_iceland;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class ViewPageActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 7;

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
        mPager.setCurrentItem(Integer.parseInt(vikudagur));
        
        mPager.setOnPageChangeListener(new OnPageChangeListener() {
        	int currentPage;
        	@Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
        		currentPage = position;
        		
            }

            
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub
//            	 if (state == ViewPager.SCROLL_STATE_DRAGGING) {
//            		 if (currentPage == 0)
//            	          mPager.setCurrentItem(6,false);
//            	     else if (currentPage == NUM_PAGES-1)
//            	          mPager.setCurrentItem(0,false);
//            	 }

            }
        });
        
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    
    
    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        

        @Override
        public Fragment getItem(int position) {
        	ScreenSlidePageFragment pf = new ScreenSlidePageFragment();
        	Bundle bdl = new Bundle(1);
            bdl.putInt("position", position);
            pf.setArguments(bdl);
            return pf;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
