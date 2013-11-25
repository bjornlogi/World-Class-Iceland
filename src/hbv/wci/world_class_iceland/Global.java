package hbv.wci.world_class_iceland;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Global {
	public static final String ST1 = "Almenn stundatafla";
	public static final String ST2 = "Mín stundatafla";
	public static final String OPN = "Opnunartímar";
	public static final String UTS = "Útskrá";
	public static final String INS = "Innskráning";
	public static String[] drawerListItems = new String[] {ST1, OPN, INS};
	public static String currentUser;
	public static int currentUserID;
	//public static SharedPreferences pref = this.getApplicationContext().getSharedPreferences("login", 0);
	public static Editor editor;
	public Global mContext = this;
	
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
	
	public static boolean isUserLoggedIn(Context ctx){
		SharedPreferences pref = ctx.getApplicationContext().getSharedPreferences("login", 0);
		return pref.getInt("_id", -1) != -1;
	}
	
	public static String weekdayFormatForDB(String input){
		input = input.replaceAll("Þ","T");
		input = input.replaceAll("ð","d");
		input = input.replaceAll("á", "a");
		input = input.replaceAll("ö","o");
		return input.substring(0, 3);
	}
	
	public static int getUsersID(Context ctx){
		SharedPreferences pref = ctx.getApplicationContext().getSharedPreferences("login", 0);
		return pref.getInt("_id", -1);
	}
	
	public static String getUsersEmail(Context ctx){
		SharedPreferences pref = ctx.getApplicationContext().getSharedPreferences("login", 0);
		return pref.getString("netfang", "-1");
	}
	
	public static void setUser(Context ctx, int _id, String netfang){
		SharedPreferences pref = ctx.getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
		Editor editor = pref.edit();
		editor.putInt("_id", _id); 
		editor.putString("netfang", netfang);
		editor.commit();
	}
	
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
	
	public static HashMap<String, Integer> timiDags = initTimiDags();
	private static HashMap<String, Integer> initTimiDags(){
		timiDags = new HashMap<String,Integer>();
		timiDags.put("morgun", 0);
		timiDags.put("hadegi", 1);
		timiDags.put("siddegi", 2);
		timiDags.put("kvold", 3);
		
		return timiDags;
	}
	
	
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
	
	public static String[] timiDagsArray = initDagsArray();
	private static String[] initDagsArray() {
		timiDagsArray = new String[7];
		timiDagsArray[0] = "Morguntímar";
		timiDagsArray[1] = "Hádegistímar";
		timiDagsArray[2] = "Síðdegistímar";
		timiDagsArray[3] = "Kvöldtímar";
		return timiDagsArray;
	}
	
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
}
