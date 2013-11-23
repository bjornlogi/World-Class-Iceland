package hbv.wci.world_class_iceland;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

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
	public static Long currentUserID;
	public static Editor editor;
	
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
}
