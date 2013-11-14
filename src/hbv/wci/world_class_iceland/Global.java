package hbv.wci.world_class_iceland;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class Global {
	public static final String ST1 = "Almenn stundatafla";
	public static final String ST2 = "Mín stundatafla";
	public static final String OPN = "Opnunartímar";
	public static final String UTS = "Útskrá";
	
	public static String[] drawerListItems = new String[] {ST1, OPN};
	
	public static String currentUser;
	
	public static int vikudagur = updateDay();
	public static int updateDay() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.UK);
		System.out.println(cal.get(Calendar.DAY_OF_WEEK));
		return cal.get(Calendar.DAY_OF_WEEK); 
	}
	
	public static HashMap<String, Integer> map = createMap();
	private static HashMap<String, Integer> createMap(){
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
