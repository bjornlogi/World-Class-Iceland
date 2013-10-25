package hbv.wci.world_class_iceland;

import hbv.wci.world_class_iceland.Stundatafla.AsyncExecution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
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
	private ListView mainListView ;
	private DataSource mDataSource;
	private Spinner spinner1, spinner2;
	private Button btnSubmit;
	private String stod = "";
	private String tegund = "";
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listHeader;
	HashMap<String, List<String>> listChild;
	HashMap<String, String> infoChild;
	ViewGroup rootView;
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 	int position = getArguments().getInt("position");
		 	stod = getArguments().getString("stod");
		 	tegund = getArguments().getString("tegund");
	        rootView = (ViewGroup) inflater.inflate(
	                R.layout.expandable, container, false);
	        TextView t = (TextView) rootView.findViewById(R.id.opnun_header);
	        expListView = (ExpandableListView) rootView.findViewById(R.id.expandable1);
	        System.out.println(position);
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
	        mDataSource.open();
	       
	        if (mDataSource.getAllHoptimar().isEmpty())
	        	new AsyncExecution().execute("http://www.worldclass.is/heilsuraekt/stundaskra");
	        else
	        	synaLista();
	        return rootView;
	    }
	 
	 void synaLista(){
		 if (stod != "" && tegund != "")
			 mDataSource.filter(stod, tegund);
		 StundatofluTimi st = mDataSource.getAllStundatoflutimar();
	     listAdapter = new ExpandableListAdapter(getActivity(), st.listHeader, st.listChild, st.infoChild);
		 expListView.setAdapter(listAdapter);
		 
		 //uncommenta tetta svo ad allir listsar byrja opnadir
//		 for (int position = 1; position <= listAdapter.getGroupCount(); position++)
//			 expListView.expandGroup(position - 1);
		 
			expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parnet, android.view.View view,
						int position, long id) {
					String msg = "Svona muntu geta skráð þig í tíma!";
					Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
				}
			});//loka onclick
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
				//progressDialog= ProgressDialog.show(getActivity(), "Hleðsla í gangi","Erum að sækja gögn, hinkraðu smá", true);
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
					catch ( Exception e ) {
						System.out.println("error: " + e);
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
				//progressDialog.dismiss();
				synaLista();
				super.onPostExecute(result);
			}
		}
}
