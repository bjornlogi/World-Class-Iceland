package hbv.wci.world_class_iceland.stundatafla;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hbv.wci.world_class_iceland.Global;
import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.data.DataSource;
import hbv.wci.world_class_iceland.data.StundatofluTimi;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Styrir hvad birtist a hverri sidu fyrir sig
 * 
 * @author Bjorn
 *
 */
public class StundataflaFragment extends Fragment implements StundatofluButur{
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
		 	
		 	
	        rootView = (ViewGroup) inflater.inflate(
	                R.layout.expandable, container, false);
	        
	        
	        akvedaDag();
	        mDataSource.open();
	        if (mDataSource.isEmpty() && getArguments().getString("update")=="0")
	        	Toast.makeText(getActivity(),"Tengstu netinu til að sjá stundatöfluna", Toast.LENGTH_LONG)
	        	.show();
	        else{
	        	birtaToflu();
	        }
	     	
	        return rootView;
	    }
	 
	 /**
	  * Finnur ut hvada dag a birta a voldu sidunni
	  */
	 public void akvedaDag(){
		 int position = getArguments().getInt("position");
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
	 /**
	  * Birtir videigandi stundatoflu
	  */

	 public void birtaToflu(){
		 expListView = (ExpandableListView) rootView.findViewById(R.id.expandable1);
		 stod = getArguments().getString("stod");
		 tegund = getArguments().getString("tegund"); 
		 mDataSource.filter(stod, tegund);
		 
		 st = mDataSource.getAllStundatoflutimar();
		 if (st.isEmpty()){
			 
			 listAdapter = new ExpandableListAdapter(getActivity(), st.listHeader, st.listChild, st.infoChild){
		    	 @Override
		    	 public boolean isChildSelectable(int groupPosition, int childPosition){
		    		 return false;
		    	 }
		     };
		     expListView.setAdapter(listAdapter);
		 }
		 else{
			 listAdapter = new ExpandableListAdapter(getActivity(), st.listHeader, st.listChild, st.infoChild);
			 expListView.setAdapter(listAdapter);
			 expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
				 
		            public boolean onChildClick(ExpandableListView parent, View v,
		                    int groupPosition, int childPosition, long id) {
		                final String selected = (String) listAdapter.getChild(
		                        groupPosition, childPosition);
		                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("login", 0);
		                Long userID = pref.getLong("_id", -1);
		                Toast.makeText(getActivity(), selected + "\nUserID is " + userID, Toast.LENGTH_LONG)
		                        .show();
		 
		                return true;
		            }
		        });
		 }
		 /*
		  * uncommenta tetta svo ad allir listsar byrja opnadir
		  * 
		  */
//		 for (int position = 1; position <= listAdapter.getGroupCount(); position++)
//			 expListView.expandGroup(position - 1);
		}
	 
	 private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
		 private StundatofluTimi st;

	     protected Void doInBackground(Void... params) {
	    	 mDataSource.filter(stod, tegund);
			 st = mDataSource.getAllStundatoflutimar();
	    	 
	    	 return null;
	     }  
	     
	     @Override
	     protected void onPostExecute(Void results) {
	    	 //synaLista(st);
	    	 //birtaLista();
	     }
	}
}
