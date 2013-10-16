package hbv.wci.world_class_iceland;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * Activity sem birtir gogn ur gagnagrunni sem geymir upplysingar um stundartoflu.
 * Gogn eru birt a lista formi.
 * 
 * @author Maria og Sonja
 * @see ListActivity
 */
public class TestDatabaseActivity extends ListActivity {
	Spinner sp;
	private DataSource mDataSource;
	private Context mContext;
	//private ArrayAdapter<Hoptimar> mAdapter;
	private ArrayAdapter<String> mAdapter ;
	private ListView mainListView ;
	
	/**
	 * Byr til skjainn og birtir upplysingar a honum.
	 * 
	 * @param savedInstanceState
	 * @see Bundle
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mainListView = (ListView) findViewById( R.id.mainListView );
		mContext = this;
		mDataSource = new DataSource(this);
		mDataSource.open();
		
		if (mDataSource.getAllHoptimar().isEmpty())
			new AsyncExecution().execute("http://www.worldclass.is/heilsuraekt/stundaskra");
		else
			showList();		
	}
	
	/**
	 * Skrapar gogn af vef og setur inn i gagnagrunn ef hann er ekki til stadar.
	 * 
	 * @author Maria og Sonja
	 * @see AsyncTask
	 */
	public class AsyncExecution extends AsyncTask<String, Integer, String>{
		
		/**
		 * 
		 */
		@Override
		protected void onPreExecute() {
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
					
					System.out.println("til hamingju");
				}
				catch ( Exception e ) {
					System.out.println("error: " + e);
				}
				
				System.out.println(mAdapter);
				
				return "All Done!";
		}
		
		/**
		 * @param values
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}
	
		/**
		 * @param result
		 */
		@Override
		protected void onPostExecute(String result) {
			showList();
			super.onPostExecute(result);
		
		}
	}
	
	/**
	 * Birtir gogn ur gagnagrunni a skja a listaformi.
	 */
	void showList(){
		setListAdapter(new ArrayAdapter( this,android.R.layout.simple_list_item_1, mDataSource.getAllHoptimar()));
		//ListView 
//		setListAdapter( new SimpleAdapter(this, mDataSource.getAllHoptimarr(),
//				android.R.layout.simple_list_item_1,
//                new String[] {"timi", "klukkan", "salur", "tjalfari"},
//                new int[] {android.R.id.text1,
//                           android.R.id.text2}));
	}

	/**
	 * 
	 */
	@Override
	protected void onResume() {
		mDataSource.open();
		super.onResume();
	}

	/**
	 * 
	 */
	@Override
	protected void onPause() {
		mDataSource.close();
		super.onPause();
	}
}
