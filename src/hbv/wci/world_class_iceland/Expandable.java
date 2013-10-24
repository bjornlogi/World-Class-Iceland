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
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandable);
		
		expListView = (ExpandableListView) findViewById(R.id.expandable1);
		
		fyllaLista();
		
		listAdapter = new ExpandableListAdapter(this, listHeader, listChild);
		
		expListView.setAdapter(listAdapter);
	}
	
	private void fyllaLista(){
		listHeader = new ArrayList<String>();
        listChild = new HashMap<String, List<String>>();
        
        listHeader.add("Morguntímar");
        listHeader.add("Hádegistímar");
        listHeader.add("Kvöldtímar");
        
        List<String> morguntimar = new ArrayList<String>();
        morguntimar.add("Morguntími 1");
        morguntimar.add("Morguntími 2");
        
        List<String> hadegistimar = new ArrayList<String>();
        hadegistimar.add("Hádegistími 1");
        hadegistimar.add("Hádegistími 2");
        
        List<String> kvoldtimar = new ArrayList<String>();
        kvoldtimar.add("Kvöldtími 1");
        kvoldtimar.add("Kvöldtími 2");
        
        listChild.put(listHeader.get(0), morguntimar);
        listChild.put(listHeader.get(1), hadegistimar);
        listChild.put(listHeader.get(2), kvoldtimar);
	}

}
