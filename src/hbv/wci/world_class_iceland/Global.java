package hbv.wci.world_class_iceland;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Global {
	public static final String ST1 = "Almenn stundatafla";
	public static final String ST2 = "Mín stundatafla";
	public static final String OPN = "Opnunartímar";
	public static final String UTS = "Útskrá";
	public static final String INS = "Innskráning";
	public static String[] drawerListItems = new String[] {ST1, OPN, INS};
	public static String currentUser;
	public static int currentUserID;
	public static Editor editor;
	public static int secondsInADay = 24*60*60;
	//public Global mContext = this;
	
	/**
	 * Skilar thremur fyrstu stofum i teim vikudag sem er i dag a ensku
	 * 
	 * @return vikudagur dagsins i dag
	 */
	public static String dayOfWeek = initDay();
	private static String initDay() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.UK);
		
		int day = cal.get(Calendar.DAY_OF_WEEK);
		String dagur = "";
		switch (day) {
			case Calendar.SUNDAY: dagur="Sun"; break;
			case Calendar.MONDAY:  dagur="Mon"; break;
			case Calendar.TUESDAY: dagur="Tue"; break;
			case Calendar.WEDNESDAY: dagur="Wed"; break;
			case Calendar.THURSDAY: dagur="Thu"; break;
			case Calendar.FRIDAY: dagur="Fri"; break;
			case Calendar.SATURDAY: dagur="Sat"; break;
		}
		
		return dagur;
		
	}
	
	/**
	 * Fall sem kannar hvort notandi se loggadur inn eda ekki
	 * 
	 * @param ctx - Samhengid sem kallad er ur
	 * @return boolean hvort einhver se loggadur inn eda ekki
	 */
	public static boolean isUserLoggedIn(Context ctx){
		SharedPreferences pref = ctx.getApplicationContext().getSharedPreferences("login", 0);
		return pref.getInt("_id", -1) != -1;
	}
	/**
	 * Fall sem er mjog serhaeft, en skilar fyrstu tremur stofunum i inntakinu med enskum stofum
	 * 
	 * @param input - vikudagur a islensku
	 * @return fyrstu trir stafirnir, ekki med islenskum stofum
	 */
	public static String weekdayFormatForDB(String input){
		input = input.replaceAll("Þ","T");
		input = input.replaceAll("ð","d");
		input = input.replaceAll("á", "a");
		input = input.replaceAll("ö","o");
		return input.substring(0, 3);
	}
	
	/**
	 * Fall sem skilar audkenni notenda sem er loggadur inn
	 * 
	 * @param ctx - Samhengid sem kallad er ur
	 * @return audkenni notendans sem er skradur inn.
	 */
	public static int getUsersID(Context ctx){
		SharedPreferences pref = ctx.getApplicationContext().getSharedPreferences("login", 0);
		return pref.getInt("_id", -1);
	}
	
	/**
	 * Fall sem skilar tolvupost notenda sem er loggadur inn
	 * 
	 * @param ctx - Samhengid sem kallad er ur
	 * @return tolvupostur notendans sem er skradur inn.
	 */
	public static String getUsersEmail(Context ctx){
		SharedPreferences pref = ctx.getApplicationContext().getSharedPreferences("login", 0);
		return pref.getString("netfang", "-1");
	}
	
	/**
	 * Kemst ad tvi hvort timinn second se a milli first og third.
	 * 
	 * @param first strengur a forminu "hh:mm"
	 * @param second strengur a forminu "hh:mm"
	 * @param third strengur a forminu "hh:mm"
	 * @return true ef first<second<third, false annars
	 */
	public static boolean isBetween(String first, String second, String third) {
		String []firstParts = first.split(":");
		String []secondParts = second.split(":");
		String []thirdParts = third.split(":");
		
		int firstKlu = Integer.parseInt(firstParts[0]);
		int firstMin = Integer.parseInt(firstParts[1]);
		int secondKlu = Integer.parseInt(secondParts[0]);
		int secondMin = Integer.parseInt(secondParts[1]);
		int thirdKlu = Integer.parseInt(thirdParts[0]);
		int thirdMin = Integer.parseInt(thirdParts[1]);
		
		if (secondKlu > firstKlu && secondKlu < thirdKlu) {
			return true;
		} else if (secondKlu == thirdKlu) {
			if (secondMin < thirdMin) {
				return true;
			} else {
				return false;
			}
		}
		if (firstKlu == secondKlu) {
			if (secondMin > firstMin) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Stillir notendann
	 * 
	 * @param ctx - Samhengid sem kallad er ur
	 * @param _id - Audkenni notendans
	 * @param netfang - Netfang notendans
	 */
	public static void setUser(Context ctx, int _id, String netfang){
		SharedPreferences pref = ctx.getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
		Editor editor = pref.edit();
		editor.putInt("_id", _id); 
		editor.putString("netfang", netfang);
		editor.commit();
	}
	/**
	 * Fall sem skilar hvad klukkan er
	 * 
	 * @return hvad klukkan er a HH:mm formati
	 */
	public static String timeRightNow(){
		TimeZone T1 = TimeZone.getTimeZone("GMT"); 
		SimpleDateFormat klukkan = new SimpleDateFormat ("HH:mm");
		klukkan.setTimeZone(T1);
		return klukkan.format(new Date());
	}
	
	/**
	 * Fall sem uppfaerir daginn i dag
	 * 
	 */
	public static void updateDay() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.UK);
		
		int day = cal.get(Calendar.DAY_OF_WEEK);
		String dagur = "";
		switch (day) {
			case Calendar.SUNDAY: dagur="Sun"; break;
			case Calendar.MONDAY:  dagur="Mon"; break;
			case Calendar.TUESDAY: dagur="Tue"; break;
			case Calendar.WEDNESDAY: dagur="Wed"; break;
			case Calendar.THURSDAY: dagur="Thu"; break;
			case Calendar.FRIDAY: dagur="Fri"; break;
			case Calendar.SATURDAY: dagur="Sat"; break;
		}
		
		dayOfWeek = dagur;
	}
	
	/**
	 * Byr til hakktoflu sem breytir strengjum i index fyrir fylki
	 * 
	 */
	public static HashMap<String, Integer> timiDags = initTimiDags();
	private static HashMap<String, Integer> initTimiDags(){
		timiDags = new HashMap<String,Integer>();
		timiDags.put("morgun", 0);
		timiDags.put("hadegi", 1);
		timiDags.put("siddegi", 2);
		timiDags.put("kvold", 3);
		
		return timiDags;
	}
	
	/**
	 * Byr til hakktoflu sem breytir strengjum i index fyrir fylki
	 * 
	 */
	public static HashMap<String, Integer> map = initMap();
	private static HashMap<String, Integer> initMap(){
		map = new HashMap<String,Integer>();
		map.put("Mon", 0);
		map.put("Tue", 1);
		map.put("Wed", 2);
		map.put("Thu", 3);
		map.put("Fri", 4);
		map.put("Sat", 5);
		map.put("Sun", 6);
		
		return map;
	}
	
	/**
	 * Byr til fylki af vikudogum a islensku
	 * 
	 */
	public static String[] weekdayArray = initWeekday();
	private static String[] initWeekday() {
		weekdayArray = new String[7];
		weekdayArray[0] = "Mánudagur";
		weekdayArray[1] = "Þriðjudagur";
		weekdayArray[2] = "Miðvikudagur";
		weekdayArray[3] = "Fimmtudagur";
		weekdayArray[4] = "Föstudagur";
		weekdayArray[5] = "Laugardagur";
		weekdayArray[6] = "Sunnudagur";
		return weekdayArray;
	}
	
	/**
	 * Byr til fylki af tima dags a islensku
	 * 
	 */
	public static String[] timiDagsArray = initDagsArray();
	private static String[] initDagsArray() {
		timiDagsArray = new String[7];
		timiDagsArray[0] = "Morguntímar";
		timiDagsArray[1] = "Hádegistímar";
		timiDagsArray[2] = "Síðdegistímar";
		timiDagsArray[3] = "Kvöldtímar";
		return timiDagsArray;
	}
	
	/**
	 * Byr til hakktoflu sem breytir strengjum i index fyrir fylki
	 * 
	 */
	public static HashMap<String, Integer> mapIS = initMapIS();
	private static HashMap<String, Integer> initMapIS(){
		mapIS = new HashMap<String,Integer>();
		mapIS.put("Man", 0);
		mapIS.put("Tri", 1);
		mapIS.put("Mid", 2);
		mapIS.put("Fim", 3);
		mapIS.put("Fos", 4);
		mapIS.put("Lau", 5);
		mapIS.put("Sun", 6);
		
		return mapIS;
	}
	
	/**
	 * Stillir Navigationid 
	 * 
	 * @param mContext - Samhengi sem kallad er ur
	 * @param mDrawerLayout - Snid og utlit menusins
	 * @param mDrawerList - Listinn sem birtist a drawerinum
	 * @param activity - Activity sem kallad er ur
	 * @return Trigger fyrir navigationid.
	 */
	public static ActionBarDrawerToggle setDrawer(Context mContext, DrawerLayout mDrawerLayout, ListView mDrawerList, final Activity activity){

		final ActionBarDrawerToggle mDrawerToggle;
		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(mContext, R.layout.drawer_list_item, Global.drawerListItems));
		//mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mDrawerList.setOnItemClickListener(new NavDrawerListener(mContext, mDrawerLayout, mDrawerList));

		// enable ActionBar app icon to behave as action to toggle nav drawer
		
		activity.getActionBar().setDisplayHomeAsUpEnabled(true);
		activity.getActionBar().setHomeButtonEnabled(true);
		
		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(
			activity,                  /* host Activity */
			mDrawerLayout,         /* DrawerLayout object */
			R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
			R.string.app_name,     /* "open drawer" description for accessibility */
			R.string.app_name      /* "close drawer" description for accessibility */
		){
            public void onDrawerClosed(View view) {
                activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		return mDrawerToggle;
	}
	
	public static String[] determineListItems(Context ctx){
		// If the nav drawer is open, hide action items related to the content view
		// check if user is logged in
		if(!isUserLoggedIn(ctx)) {
			drawerListItems = new String[] {Global.ST1, Global.OPN, Global.INS};
		} else {
			drawerListItems = new String[] {getUsersEmail(ctx), Global.ST1, Global.ST2, Global.OPN, Global.UTS};
		}
		return drawerListItems;
	}
	
}