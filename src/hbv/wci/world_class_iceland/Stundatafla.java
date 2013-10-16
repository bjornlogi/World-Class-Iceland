package hbv.wci.world_class_iceland;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
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
	  Timi [] timar = new Timi[10];
	  private ListView mainListView ;
	  private ArrayAdapter<String> listAdapter ;
	  
	  /**
	   * Called when the activity is first created.
	   */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.stundatafla);
	    
	    // Find the ListView resource. 
	    mainListView = (ListView) findViewById( R.id.mainListView );

	    // Create and populate a List of planet names.
	    //List<? extends Map<String, String>> timar = new ArrayList<Map<String, String>>();
	    
	    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	    
	    
	    Timi testTimi = new Timi("Spinning", "8:00-9:00", "Salur 1", "Jón Jónsson");
	    Timi testTimi2 = new Timi("Hot Yoga", "8:30-9:40", "Salur 3", "Jóna Jónsdóttir");
	    timar[0] = testTimi;
	    timar[1] = testTimi2;
	    for (int i=0;i<2;i++){
	    	Map<String, String> map = new HashMap<String, String>(2);
	    	map.put("timi", timar[i].nafn);
	    	map.put("klukkan", timar[i].klukkan);
	    	map.put("salur", timar[i].salur);
	    	map.put("tjalfari", timar[i].tjalfari);
	    	list.add(map);
	    }
	    		//new String[] { "Spinning", "Zumba", "Hot Yoga", "Kross Fit",
	              //                        "Buttlift", "Quickspinning", "Þol", "Jóga"};  
	    
	    SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, new String[] {"timi", "klukkan", "salur", "tjalfari"}, new int[] {android.R.id.text1, android.R.id.text2}) ;
	    
//	    listAdapter.add( "Ceres" );
	    
	    // Set the ArrayAdapter as the ListView's adapter.
	    mainListView.setAdapter( adapter );
	    
	    mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parnet, android.view.View view,
	                int position, long id) {
	        	//System.out.println(id);
	        	String msg = "Staðsetning: " + timar[(int) (id)].salur+ "\nÞjálfari: " + timar[(int)(id)].tjalfari;
	        	Toast.makeText(Stundatafla.this, msg, 100000).show();
	        }

	    });
	  }
	}