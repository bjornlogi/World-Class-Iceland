package hbv.wci.world_class_iceland.stundatafla;

//import hbv.wci.world_class_iceland.Stundataflagamalt.AsyncExecution;

import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.R.id;
import hbv.wci.world_class_iceland.R.layout;
import hbv.wci.world_class_iceland.data.DataSource;
import hbv.wci.world_class_iceland.data.StundatofluTimi;
import hbv.wci.world_class_iceland.skraning.Innskraning;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StundataflaFragment extends Fragment {
	private DataSource mDataSource;
	private String stod = "";
	private String tegund = "";
	private ExpandableListAdapter listAdapter;
	private ExpandableListView expListView;
	private ViewGroup rootView;
	private StundatofluTimi st;
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 	int position = getArguments().getInt("position");
		 	stod = getArguments().getString("stod");
		 	tegund = getArguments().getString("tegund");
	        rootView = (ViewGroup) inflater.inflate(
	                R.layout.expandable, container, false);
	        
	        expListView = (ExpandableListView) rootView.findViewById(R.id.expandable1);
	        
	        akvedaDag(position);
	        mDataSource.open();
	        if (mDataSource.isEmpty() && getArguments().getString("update")=="0")
	        	Toast.makeText(getActivity(),"Tengstu netinu til að sjá stundatöfluna", Toast.LENGTH_LONG)
	        	.show();
	        else
	        	synaLista();
	     	
	        return rootView;
	    }
	 
	 private void akvedaDag(int position){
		 TextView t = (TextView) rootView.findViewById(R.id.opnun_header);
		 switch(position){
	        case 0:
	        	t.setText("Mánudagur");
	        	mDataSource = new DataSource(getActivity(), "Man");
	        	break;
	        case 1:
	        	t.setText("Þriðjudagur");
	        	mDataSource = new DataSource(getActivity(), "Tri");
	        	break;
	        case 2:
	        	t.setText("Miðvikudagur");
	        	mDataSource = new DataSource(getActivity(), "Mid");
	        	break;
	        case 3:
	        	t.setText("Fimmtudagur");
	        	mDataSource = new DataSource(getActivity(), "Fim");
	        	break;
	        case 4:
	        	t.setText("Föstudagur");
	        	mDataSource = new DataSource(getActivity(), "Fos");
	        	break;
	        case 5:
	        	t.setText("Laugardagur");
	        	mDataSource = new DataSource(getActivity(), "Lau");
	        	break;
	        case 6:
	        	t.setText("Sunnudagur");
	        	mDataSource = new DataSource(getActivity(), "Sun");
	        	break;
	        }
	 }
	 
	 void synaLista(){
		 mDataSource.filter(stod, tegund);
		 st = mDataSource.getAllStundatoflutimar();
	     listAdapter = new ExpandableListAdapter(getActivity(), st.listHeader, st.listChild, st.infoChild);
		 expListView.setAdapter(listAdapter);
		 expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			 
	            public boolean onChildClick(ExpandableListView parent, View v,
	                    int groupPosition, int childPosition, long id) {
	                final String selected = (String) listAdapter.getChild(
	                        groupPosition, childPosition);
	                Toast.makeText(getActivity(), selected, Toast.LENGTH_LONG)
	                        .show();
	 
	                return true;
	            }
	        });
		 //uncommenta tetta svo ad allir listsar byrja opnadir
//		 for (int position = 1; position <= listAdapter.getGroupCount(); position++)
//			 expListView.expandGroup(position - 1);
		}
	 
}
