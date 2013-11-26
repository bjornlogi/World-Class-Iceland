package hbv.wci.world_class_iceland.stundatafla;


import hbv.wci.world_class_iceland.Global;
import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.data.DataSource;
import hbv.wci.world_class_iceland.data.StundatofluTimi;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
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
		expListView = (ExpandableListView) rootView.findViewById(R.id.stundataflan);
		stod = getArguments().getString("stod");
		tegund = getArguments().getString("tegund"); 
		mDataSource.filter(stod, tegund);
		
		st = mDataSource.getAllStundatoflutimar();
		if (st.isEmpty()){
			makeUnclickable();
		} else {
			setListViewListener();
		}
		determineWhichAreExpanded();
	}
	
	private void makeUnclickable(){
		listAdapter = new ExpandableListAdapter(getActivity(), st.listHeader, st.listChild, st.infoChild){
			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition){
				return false;
			}
		};
		expListView.setAdapter(listAdapter);
	}
	
	private void setListViewListener(){
		listAdapter = new ExpandableListAdapter(getActivity(), st.listHeader, st.listChild, st.infoChild);
		expListView.setAdapter(listAdapter);
		expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
				 
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				
				final String selected = (String) listAdapter.getChild(groupPosition, childPosition);
				int getMoney = selected.indexOf("$");
				int uid = Global.getUsersID(getActivity());
				int htid = Integer.parseInt(selected.substring(getMoney+3));
				
				String info;
				if(mDataSource.notendatimiExists(uid, htid))
					info = "Tíminn er þegar til staðar í Mín Stundatafla.";
				else
					info = "Tímanum var bætt í Mín Stundatafla.";
				
				if (Global.isUserLoggedIn(getActivity()) && !mDataSource.notendatimiExists(uid, htid))
					mDataSource.addNotendatimi(uid, htid);
				
				createDialog(selected, info, getMoney);
				
				return true;
			}
		});
	}
	
	private void createDialog(String selected, String info, int getMoney){
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.dialog_nyskra);
		dialog.setCanceledOnTouchOutside(false);
		
		dialog.setTitle( selected.substring(0, getMoney) );
	 
		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
									
		text.setText(info);
		
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	private void determineWhichAreExpanded(){
		String klukkanNuna = Global.timeRightNow();
		for (int position = 0; position < listAdapter.getGroupCount(); position++){
			if(listAdapter.getGroup(position).equals("Morguntímar") && Global.isBetween("05:00", klukkanNuna, "11:59")){
				expListView.expandGroup(position);
				break;
			}
			else if (listAdapter.getGroup(position).equals("Hádegistímar") && Global.isBetween("12:00", klukkanNuna, "14:29")){
				expListView.expandGroup(position);
				break;
			}
			else if (listAdapter.getGroup(position).equals("Síðdegistímar") && Global.isBetween("14:30", klukkanNuna, "17:29")){
				expListView.expandGroup(position);
				break;
			}
			else if (listAdapter.getGroup(position).equals("Kvöldtímar"))
				expListView.expandGroup(position);
		}
	}

}
