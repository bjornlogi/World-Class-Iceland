package hbv.wci.world_class_iceland;

import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.data.DataSource;
import hbv.wci.world_class_iceland.skraning.Innskraning;
import hbv.wci.world_class_iceland.stundatafla.StundataflaActivity;

import java.net.UnknownHostException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

public class StartUpScreen extends Activity {
	private DataSource mDataSource;
	public Context mContext = this;
	SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startupscreen);
		
		if (hoptimarExists() && Global.isUserLoggedIn(mContext)){
			Global.currentUser = pref.getString("netfang", "-1");
			createIntent("StundataflaActivity");
			mDataSource.close();
		}
		else if (!Global.isUserLoggedIn(mContext) && hoptimarExists()){
			createIntent("Innskraning");
			mDataSource.close();
		}
		else if (!hoptimarExists() && isNetworkAvailable()){
			System.out.print("ble");
			new AsyncExecution().execute("http://www.worldclass.is/heilsuraekt/stundaskra");
		}
	}
	
	private void createIntent(String destination){
		Intent i;
		if (destination == "StundataflaActivity")
			i = new Intent(StartUpScreen.this, StundataflaActivity.class);
		else i = new Intent(StartUpScreen.this, Innskraning.class);
		i.putExtra("vikudagur", Integer.toString(0));
		startActivity(i);
	}
	
//	private boolean isUserLoggedIn(){
//		Long userID = pref.getLong("_id", -1);
//		if (userID == -1)
//			return false;
//		else{
//			Global.currentUserID = userID;
//			return true;
//		}
//	}
	
	/**
	 * Kannar hvort ad gagnagrunnur se til, ef ekki ta byr hann til nytt async og skilar false
	 * 
	 * @return hvort gagnagrunnur se til
	 */
	private boolean hoptimarExists(){
		mDataSource = new DataSource(mContext);
		mDataSource.open();
//		
//		if ( && isNetworkAvailable()){
//        	new AsyncExecution().execute("http://www.worldclass.is/heilsuraekt/stundaskra");
//        	return false;
//		}
		return !mDataSource.isEmpty();
	}
	
	/*
	 * Athugar hvort ad siminn se nettengdur
	 */
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	public class AsyncExecution extends AsyncTask<String, Integer, String>{
		ProgressDialog progressDialog;
		/**
		 * Fall sem er keyrt a undan Async verkinu.
		 * 
		 */
		@Override
		protected void onPreExecute() {
			progressDialog= ProgressDialog.show(mContext, "Hleð niður gögnum","Hinkraðu augnablik", true);
			super.onPreExecute();
		}
		
		/**
		 * Skrapar gogn af vef og setur inn i gagnagrunn i bakgrunns traedi.
		 * 
		 * @param params URL a sidunni sem sott er gogn af. 
		 */
		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
				try { 
					Document doc = Jsoup.connect(url).get();
					Elements tableElements = doc.select("table");
			
					
					Elements tableClassesElements = tableElements.select(":not(thead) tr");
					String timar[] = {"", "morgun", "", "hadegi", "", "siddegi", "", "kvold"};
					String dagar[] = {"Man", "Tri", "Mid", "Fim", "Fos", "Lau", "Sun"};
					
					for (int i = 0; i < tableClassesElements.size(); i++) {
						Element row = tableClassesElements.get(i);
						Elements rowItems = row.select("td");
						String timi = timar[i];
						
						for (int j = 0; j < rowItems.size(); j++) {
							String dagur = dagar[j];
							Element list = rowItems.get(j);
							Elements listItems = list.select("li"); 
							
							for (int k = 0; k < listItems.size(); k++){
								String hopTimi[] = new String[9];
								Element links = listItems.get(k);
								hopTimi[0] = links.select("a").text();
								hopTimi[1] = links.select(".stod").text();
								hopTimi[2] = links.select(".salur").text();
								hopTimi[3] = links.select(".tjalfari").text();
								hopTimi[4] = links.select(".tegund").text();
								hopTimi[5] = links.select(".time").text();
								hopTimi[6] = timi;
								hopTimi[7] = dagur;
								Elements lokad = links.select(".locked");
								
								
								if (lokad.text() != "")
									hopTimi[8] = lokad.attr("title");
								else
									hopTimi[8] = " ";
								mDataSource.addHoptimi(hopTimi);
								
							}
						}
					}
				}
				catch ( UnknownHostException e ) {
					System.out.println("Ekki náðist samband við vefþjón");
				}
				catch ( Exception e){
					System.out.println("Villa kom upp við að ná tengingu við vefþjón");
				}
				return "All Done!";
		}
			
		/**
		 * Kallad reglulega a tetta fall medan a keyrslunni stendur.
		 * 
		 * @param values
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}
		
		/**
		 * Ef haett er vid keyrsluna adur en hun er fullklarud, ta keyra tetta fall
		 * 
		 * @see android.os.AsyncTask#onCancelled()
		 */
		@Override
		protected void onCancelled() {
			mDataSource.dropTable();
			super.onCancelled();
		}
	
		/**
		 * Tegar buid er ad hlada inn asynchronous verkinu, keyrir tetta fall sem birtir
		 * gognin i listanum.
		 * 
		 * @param result
		 */
		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			if (Global.isUserLoggedIn(mContext)){
				createIntent("StundataflaActivity");
			} else {
				createIntent("Innskraning");		
			}
			mDataSource.close();
			super.onPostExecute(result);
		}
	}
}
