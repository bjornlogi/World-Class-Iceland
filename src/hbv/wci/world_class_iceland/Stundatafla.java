package hbv.wci.world_class_iceland;

import hbv.wci.world_class_iceland.TestDatabaseActivity.AsyncExecution;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * 
 */
public class Stundatafla extends Activity{
	  private ListView mainListView ;
	  private ArrayAdapter<String> listAdapter ;
	  private DataSource mDataSource;
	  
	  /**
	   * Called when the activity is first created.
	   */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.stundatafla);
	    
	    // Find the ListView resource. 
	    mainListView = (ListView) findViewById( R.id.mainListView );
	    
	    mDataSource = new DataSource(this);
	    mDataSource.open();
	    
	    if (mDataSource.getAllHoptimarr().isEmpty())
	    	new AsyncExecution().execute("http://www.worldclass.is/heilsuraekt/stundaskra");
	    else
	    	synaLista();
	    
	    
	  }// loka onCreate
	  void synaLista(){
		  	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		    
		    list = mDataSource.getAllHoptimarr();
		    
		    SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, 
		    		new String[] {"timi", "klukkan"}, 
		    		new int[] {android.R.id.text1, android.R.id.text2}) ;
		    
		    // Set the ArrayAdapter as the ListView's adapter.
		    mainListView.setAdapter( adapter );
		    
		    mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		        @Override
		        public void onItemClick(AdapterView<?> parnet, android.view.View view,
		                int position, long id) {
		        	String msg = "Svona muntu geta skráð þig í tima!";
		        	Toast.makeText(Stundatafla.this, msg, 100000).show();
		        }
		    });//loka onclick
	  	}
	  
		public class AsyncExecution extends AsyncTask<String, Integer, String>{
			
			/**
			 * 
			 */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}
			
			/**
			 * @param params
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
				synaLista();
				super.onPostExecute(result);
			}
		}
	}//loka Stundatafla
