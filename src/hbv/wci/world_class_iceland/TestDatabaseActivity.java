package hbv.wci.world_class_iceland;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * bla
 * 
 * @see ListActivity
 */
public class TestDatabaseActivity extends ListActivity {
	Spinner sp;
	private DataSource mDataSource;
	private Context mContext;
	private ArrayAdapter<Users> mAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = this;
		mDataSource = new DataSource(this);
		mDataSource.open();
		
		mAdapter = new ArrayAdapter( this,android.R.layout.simple_list_item_1, mDataSource.getAllUsers());
		if (mDataSource.getAllUsers().isEmpty())
			new AsyncExecution().execute("http://www.worldclass.is/heilsuraekt/stundaskra");
		else
			setListAdapter(mAdapter);		
	}
	
	public class AsyncExecution extends AsyncTask<String, Integer, String>{
		@Override
		   protected void onPreExecute() {
		      super.onPreExecute();
		   }
		 
		   @Override
		   protected String doInBackground(String... params) {
				   System.out.println("NULLLLLLLLLLL");
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
				            		
									mDataSource.addUser(hopTimi);
									
				            	}
				            }
			            }
				         
				         System.out.println("til hamingju");
					} 
					catch ( Exception e ) {
						 System.out.println("error: " + e);
			    	}
					
					System.out.println(mAdapter);
			  // }
					     		      
					return "All Done!";
		   }
		 
		   @Override
		   protected void onProgressUpdate(Integer... values) {
		      super.onProgressUpdate(values);
		   }
		 
		   @Override
		   protected void onPostExecute(String result) {
			  showList();
		      super.onPostExecute(result);
		      
		   }
	}
	
	void showList(){
		setListAdapter(new ArrayAdapter( this,android.R.layout.simple_list_item_1, mDataSource.getAllUsers()));
	}

	@Override
	protected void onResume() {
		mDataSource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		mDataSource.close();
		super.onPause();
	}
}
