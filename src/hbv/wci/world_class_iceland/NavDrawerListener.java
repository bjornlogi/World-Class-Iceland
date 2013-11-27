package hbv.wci.world_class_iceland;

import hbv.wci.world_class_iceland.opnunartimar.Opnunartimar;
import hbv.wci.world_class_iceland.skraning.Innskraning;
import hbv.wci.world_class_iceland.stundatafla.AlmennStundatafla;
import hbv.wci.world_class_iceland.stundatafla.StundataflanMin;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class NavDrawerListener implements ListView.OnItemClickListener{
	final private Context mContext;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	//private ActionBarDrawerToggle mDrawerToggle;
	
	/**
	 * Smidur sem tekur inn breytur ur activityinu sem byr til tilvikid
	 * 
	 * @param ctx - Samhengid
	 * @param drawerLayout - Snid og utlit
	 * @param drawerList - Listi hluta
	 */
	public NavDrawerListener(Context ctx,DrawerLayout drawerLayout, ListView drawerList){
		mContext = ctx;
		mDrawerLayout = drawerLayout;
		mDrawerList = drawerList;
	}
	/**
	 * Yfirskrifun a innbyggt fall i ListView, tar stillum vid Intentin fyrir navigationid a onClick
	 * 
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
		
		String str = Global.drawerListItems[position];
		if (str.equals(Global.ST1)) {
			Intent i = new Intent(mContext, AlmennStundatafla.class);
			mContext.startActivity(i);
		} else if (str.equals(Global.ST2)){
			Intent i = new Intent(mContext, StundataflanMin.class);
			mContext.startActivity(i);
		} else if (str.equals(Global.OPN)){
			Intent i = new Intent(mContext, Opnunartimar.class);
			mContext.startActivity(i);
		} else if (str.equals(Global.UTS)) {
			Global.currentUser = null;
			Global.currentUserID = -1;
			SharedPreferences pref = mContext.getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
			Editor editor = pref.edit();
			editor.clear();
			editor.commit();
			
			Intent i = new Intent(mContext, Innskraning.class);
			mContext.startActivity(i);
		} else if (str.equals(Global.INS)) {
			Intent i = new Intent(mContext, Innskraning.class);
			mContext.startActivity(i);
		} else if (str.contains("@")) {
			//Intent i = new Intent(mContext, UmNotenda.class);
			//startActivity(i);
		}
	}
		
}


	
	
