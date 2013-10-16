package hbv.wci.world_class_iceland;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Synir opnunartima einnar stodvar.
 * 
 * @author Bjorn
 * @see Activity
 */
public class Opnunartimi extends Activity {
	
	/**
	 * bla
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opnunartimi);
		
		Intent myIntent= getIntent();
		String stod = myIntent.getStringExtra("stod");
	
		TextView titill = (TextView)findViewById(R.id.otimi_titill);
		TextView opnunartimar = (TextView)findViewById(R.id.opnun_dagar1);
		titill.setText(stod);

		//DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.ICELAND);
		java.util.TimeZone T1 = TimeZone.getTimeZone("GMT");
		SimpleDateFormat klukkan = new SimpleDateFormat ("HH:mm");
		SimpleDateFormat DOW = new SimpleDateFormat ("EEE");
		klukkan.setTimeZone(T1);
		DOW.setTimeZone(T1);
		
		Date date = new Date();
		String vikudagur = DOW.format(date);
		String dagur = vikudagur.toString();
		
		date = new Date();
		String klukkanNuna = klukkan.format(new Date());
		
		
		//naum i opnunartimana i dag fyrir stodina
		OpnunObj timarObj = new OpnunObj(stod); 
		String opnunIDag = timarObj.OpnunFyrirDag(dagur);	
		System.out.println(klukkanNuna);
		birtaErOpid(opnunIDag, klukkanNuna);		
		birtaMynd(stod);
		birtaOpnunartima(timarObj);
		
	}
	
	/**
	 * 
	 * 
	 * @param date 
	 * @param OpnunEdaLokun
	 * @return String
	 */
	public String SplittIOpnunOgLokun (String date, int OpnunEdaLokun) {
		//splittum i opnun og lokun
		String[] parts = date.split("-");
		String timaStrengur = parts[OpnunEdaLokun];
		return timaStrengur;
	}
	
	/**
	 * 
	 * @param opnunIDag
	 * @param klukkanNuna
	 */
	public void birtaErOpid (String opnunIDag, String klukkanNuna) {
		TextView opidTV = (TextView)findViewById(R.id.opid);
		Boolean opid;
		
		if (opnunIDag.equals("Lokað")) {
			opid = false;
		} else {
			String opnun = SplittIOpnunOgLokun(opnunIDag,0);
			String lokun = SplittIOpnunOgLokun(opnunIDag,1);
			opid = isBetween(opnun,klukkanNuna, lokun);
		}
		
		if (opid) {
			opidTV.setText("Opið");
			opidTV.setTextColor(Color.parseColor("#105420"));		
		} else {
			opidTV.setText("Lokað");
			opidTV.setTextColor(Color.RED);
		}
		opidTV.setTypeface(null, Typeface.BOLD);
	}
	
	/**
	 * 
	 * @param timar
	 */
	public void birtaOpnunartima (OpnunObj timar) {
		TextView opnunardagar1 = (TextView)findViewById(R.id.opnun_dagar1);
		TextView opnunartimar1 = (TextView)findViewById(R.id.opnun_timar1);
		
		String[] opntimar = new String[] {timar.OpnunFyrirDag("Mon"), timar.OpnunFyrirDag("Tue"), timar.OpnunFyrirDag("Wed"), timar.OpnunFyrirDag("Thu"), timar.OpnunFyrirDag("Fri"), timar.OpnunFyrirDag("Sat"), timar.OpnunFyrirDag("Sun")};
		String[] vikudagar = new String[] {"Mán","Þri", "Mið", "Fim", "Fös", "Lau", "Sun"};
		
		
		int start = Integer.parseInt(timar.OpnunFyrirDag("hvar á að byrja"));
		String opnunardagar = timar.OpnunFyrirDag("margir eins") + "\n";
		String opnunartimar = opntimar[0];
		TextView ts = (TextView)findViewById(R.id.skilabod);
		if (timar.Taekjasalur()) {
			opnunartimar += "*";
		}
		ts.setText(timar.Skilabod());
		opnunartimar += "\n";
		
		for (int i = start; i<7; i++){
			String substring = vikudagar[i] + ":\n";
			opnunardagar += substring;
			String substring2 = opntimar[i];
			if (timar.Taekjasalur()) {
				substring2 += "*";
			}
			substring2 += "\n";
			opnunartimar += substring2;
		}
		opnunardagar1.setText(opnunardagar);
		opnunartimar1.setText(opnunartimar);
		
	}
	
	/**
	 * 
	 * @param stod
	 */
	public void birtaMynd (String stod) {
		Drawable mynd;
		ImageView image;
		String icon = deUTFfy(stod) + "mynd";
		
		image = (ImageView)findViewById(R.id.stod_mynd);
		
		int resID = getResources().getIdentifier(icon, "drawable",  getPackageName()); 
	    image.setImageResource(resID);
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public String deUTFfy(String s) {
		s = s.toLowerCase();
		s = s.replaceAll("ö","o");
		s = s.replaceAll("í","i");
		s = s.replaceAll("á","a");
		s = s.replaceAll("æ","ae");
		s = s.replaceAll("ú","u");
		s = s.replaceAll("þ","t");
		s = s.replaceAll("ó","o");
		s = s.replaceAll(" ", "");
		
		return s;
	}
	
	/**
	 * 
	 * @param first
	 * @param second
	 * @param third
	 * @return
	 */
	public Boolean isBetween(String first, String second, String third) {
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
	
}
