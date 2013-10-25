package hbv.wci.world_class_iceland;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class Expandable extends Activity {
	
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listHeader;
	HashMap<String, List<String>> listChild;
	HashMap<String, String> infoChild;
	//HashMap<String, List<String>, HashMap<String, String>> listChild;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandable);
		
		expListView = (ExpandableListView) findViewById(R.id.expandable1);
		
		DataSource mDataSource = new DataSource(this, "Fim");
		mDataSource.open();
		
		StundatofluTimi st = mDataSource.getAllStundatoflutimar();
		
		listAdapter = new ExpandableListAdapter(this, st.listHeader, st.listChild, st.infoChild);
		
		expListView.setAdapter(listAdapter);
	}
	
	private void fyllaLista(){
		listHeader = new ArrayList<String>();
        listChild = new HashMap<String, List<String>>();
        infoChild = new HashMap<String,String>();
        
        List<String> morguntimar = new ArrayList<String>();
        
        //HashMap<String, String> morgun = new HashMap<String, String>();
       // morgun.put("Morguntími 1", "bla");
        
        morguntimar.add("Morguntími 1");
        infoChild.put("Morguntími 1","bla");
        morguntimar.add("Morguntími 2");
        
        List<String> hadegistimar = new ArrayList<String>();
        hadegistimar.add("Hádegistími 1");
        hadegistimar.add("Hádegistími 2");
        
        List<String> kvoldtimar = new ArrayList<String>();
        kvoldtimar.add("Kvöldtími 1");
        kvoldtimar.add("Kvöldtími 2");
        int i = 0;
        if (!morguntimar.isEmpty()){
        	listHeader.add("Morguntímar");
        	listChild.put(listHeader.get(i++), morguntimar);
        }
        if (!hadegistimar.isEmpty()){
        	listHeader.add("Hádegistímar");
        	listChild.put(listHeader.get(i++), hadegistimar);
        	}
        if (!kvoldtimar.isEmpty()){
        	listHeader.add("Kvöldtímar");
        	listChild.put(listHeader.get(i), kvoldtimar);
        	}
	}

}
