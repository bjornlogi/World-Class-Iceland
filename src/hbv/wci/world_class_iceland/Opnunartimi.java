package hbv.wci.world_class_iceland;

import java.text.SimpleDateFormat;
import java.util.Date;
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
 * Activity sem synir opnunartima einnar stodvar.
 * 
 * @author Bjorn
 * @see Activity
 */
public class Opnunartimi extends Activity {
	
	/**
	 * Birtir skja fyrir ta stod sem valin var.
	 * Finnur ut hvada stod er valin med ad na i skilabod sent med Intent. Birtir allar upplysingar
	 * byggdar a tvi.
	 * 
	 * @param savedInstanceState
	 * @return none
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opnunartimi);
		
		Intent myIntent= getIntent();
		String stod = myIntent.getStringExtra("stod");
	
		TextView titill = (TextView)findViewById(R.id.otimi_titill);
		titill.setText(stod);
		
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
		Stod timarObj = new Stod(stod); 
		String opnunIDag = timarObj.OpnunFyrirDag(dagur);	
		birtaErOpid(opnunIDag, klukkanNuna);		
		birtaMynd(stod);
		birtaOpnunartima(timarObj);
		
	}
	
	/**
	 * Finnur annad hvort vinstri eda haegri hluta strengsins date
	 * 
	 * @param date strengur a forminu "hh:mm-hh:mm"
	 * @param OpnunEdaLokun 0 fyrir opnun, 1 fyrir lokun
	 * @return String opnunatima eda lokunartima
	 */
	public String SplittIOpnunOgLokun (String date, int OpnunEdaLokun) {
		//splittum i opnun og lokun
		String[] parts = date.split("-");
		String timaStrengur = parts[OpnunEdaLokun];
		return timaStrengur;
	}
	
	/**
	 * Birtir "Opid" ef tad er opid nuna, annars "Lokad"
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
	 * Setur inn tilsvarandi strengi inn i id.opnun_dagar1, id.opnun_timar1 og id.skilabod
	 * 
	 * @param timar stodin sem a ad birta
	 * @see Stod
	 */
	public void birtaOpnunartima (Stod timar) {
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
	 * Setur mynd af stodinni inn i ImageView med ID-id stod_mynd
	 * 
	 * @param stod nafn a stod
	 */
	public void birtaMynd (String stod) { 
		ImageView image;
		String icon = deUTFfy(stod) + "mynd";
		
		image = (ImageView)findViewById(R.id.stod_mynd);
		
		int resID = getResources().getIdentifier(icon, "drawable",  getPackageName()); 
	    image.setImageResource(resID);
	}
	
	/**
	 * Skilar nyjum streng sem er byggdur a inntaki sem er ekki med ser islenskum stofum
	 * 
	 * @param s hvada strengur sem er
	 * @return s med ollum islenskum stofum breytt i sambaerilega ascii stafi, engin bil og allt i litlum stofum
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
		s = s.replaceAll("ð","d");
		s = s.replaceAll(" ", "");
		
		return s;
	}
	
	/**
	 * Kemst ad tvi hvort timinn second se a milli first og third.
	 * 
	 * @param first strengur a forminu "hh:mm"
	 * @param second strengur a forminu "hh:mm"
	 * @param third strengur a forminu "hh:mm"
	 * @return true ef first<second<third, false annars
	 */
	public boolean isBetween(String first, String second, String third) {
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
