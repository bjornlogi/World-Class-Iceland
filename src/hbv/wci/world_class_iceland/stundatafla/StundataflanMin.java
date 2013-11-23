package hbv.wci.world_class_iceland.stundatafla;

import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.data.DataSource;
import hbv.wci.world_class_iceland.data.StundatofluTimi;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class StundataflanMin extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stundataflanmin);
		
		DataSource data = new DataSource(this);
		data.open();
		
		StundatofluTimi st = data.getAllStundataflanMinTimi(this);
		
		ExpandableListView expListView = (ExpandableListView) findViewById(R.id.stundataflan);
		ExpandableListAdapter listAdapter;
		listAdapter = new ExpandableListAdapter(this, st.listHeader, st.listChild, st.infoChild);
		expListView.setAdapter(listAdapter);
	}
}
