package hbv.wci.world_class_iceland;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.View.OnClickListener;



/**
 * Activity sem birtir stundatoflu fyrir daginn i dag.
 * 
 * @author Bjorn
 */
public class Stundatafla extends Activity{
	private ListView mainListView ;
	private DataSource mDataSource;
	public Context mcontext = this;
	
	private Spinner spinner1, spinner2;
	private Button btnSubmit;
	
	/**
	 * Called when the activity is first created.
	 * Byr til skjainn og birtir upplysingar a honum.
	 *
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stundatafla);
		
		addItemsOnSpinner();
		addListenerOnButton();
		
		mainListView = (ListView) findViewById( R.id.mainListView );
		
		mDataSource = new DataSource(this, "Fim");
		mDataSource.open();
		if (mDataSource.getAllHoptimar().isEmpty())
			new AsyncExecution().execute("http://www.worldclass.is/heilsuraekt/stundaskra");
		else
			synaLista();
		
		
	}// loka onCreate
	
	/**
	 * Bætir við stökum á dropdownlistann
	 * 
	 */
	public void addItemsOnSpinner() {

	 
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		List<String> list1 = new ArrayList<String>();
		list1.add("Veldu stöð");
		list1.add("Laugar");
		list1.add("Kringlan");
		list1.add("Hafnarfjörður");
		list1.add("Ögurhvarf");
		list1.add("Egilshöll");
		list1.add("HR");
		list1.add("Kópavogur");
		list1.add("Mosfellsbær");
		list1.add("Seltjarnarnes");
		list1.add("Spöng");
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
		dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spinner1.setAdapter(dataAdapter1);
		
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		List<String> list2 = new ArrayList<String>();
		list2.add("Veldu tegund");
		list2.add("Brennsla / mótun");
		list2.add("Dans");
		list2.add("Hardcore / alhliða þjálfun");
		list2.add("Hardcore/alhliða");
		list2.add("Lífsstílsnámskeið");
		list2.add("Líkami og sál");
		list2.add("Rólegt");
		list2.add("Spor");
		list2.add("Styrkur");
		list2.add("óflokkað");
		ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
		dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spinner2.setAdapter(dataAdapter2);
		
	  }
	 
	 
	  /**
	   * fengin eru gildi þeirra staka sem valin voru úr dropdownlistanum 
	   */
	  public void addListenerOnButton() {
	 
		  spinner1 = (Spinner) findViewById(R.id.spinner1);
		  spinner2 = (Spinner) findViewById(R.id.spinner2);
		  btnSubmit = (Button) findViewById(R.id.btnSubmit);
	 
		  btnSubmit.setOnClickListener(new OnClickListener() {
	 
		  @Override
		  public void onClick(View v) {
	 
			  Toast.makeText(Stundatafla.this,
				"OnClickListener : " + 
				"\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) + 
				"\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
				Toast.LENGTH_SHORT).show();
		  }
	 
		});
	  }
	 
	
	
	
	/**
	 * Birtir gogn ur gagnagrunni a skja a listaformi.
	 */
	void synaLista(){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		list = mDataSource.getAllHoptimar();
		
		SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, 
				new String[] {"timi", "klukkan"}, 
				new int[] {android.R.id.text1, android.R.id.text2}) ;
		
		mainListView.setAdapter( adapter );
		
		mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parnet, android.view.View view,
					int position, long id) {
				String msg = "Svona muntu geta skráð þig í tima!";
				Toast.makeText(Stundatafla.this, msg, Toast.LENGTH_SHORT).show();
			}
		});//loka onclick
	}
		
	/**
	 * Skrapar gogn af vef og setur inn i gagnagrunn ef hann er ekki til stadar.
	 * 
	 * @author Bjorn
	 * @see AsyncTask
	 */
	public class AsyncExecution extends AsyncTask<String, Integer, String>{
		ProgressDialog progressDialog;
		/**
		 * Fall sem er keyrt a undan Async verkinu.
		 * 
		 */
		@Override
		protected void onPreExecute() {
			progressDialog= ProgressDialog.show(mcontext, "Hleðsla í gangi","Erum að sækja gögn, hinkraðu smá", true);
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
			catch ( Exception e ) {
				System.out.println("error: " + e);
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
			synaLista();
			super.onPostExecute(result);
		}
	}
}//loka Stundatafla
