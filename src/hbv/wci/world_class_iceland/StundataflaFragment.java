package hbv.wci.world_class_iceland;

//import hbv.wci.world_class_iceland.Stundataflagamalt.AsyncExecution;

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
	        
	        if (getArguments().getInt("update") ==0 && mDataSource.isEmpty())
	        	new AsyncExecution().execute("http://www.worldclass.is/heilsuraekt/stundaskra");
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
				progressDialog= ProgressDialog.show(getActivity(), "Hleðsla í gangi","Erum að sækja gögn, hinkraðu smá", true);
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
					catch ( UnknownHostException e ) {
						TextView t = (TextView) rootView.findViewById(R.id.opnun_header);
						t.setTextColor(Color.RED);
						t.setText("Ekki náðist samband við vefþjón");
					}
					catch ( Exception e){
						TextView t = (TextView) rootView.findViewById(R.id.opnun_header);
						t.setTextColor(Color.RED);
						t.setText("Villa kom upp við að ná tengingu við vefþjón");
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
				mDataSource.dropTable();
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
}
