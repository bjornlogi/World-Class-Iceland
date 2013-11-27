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
		 	//Buum til viewid fyrir fragmentinn
			rootView = (ViewGroup) inflater.inflate(
					R.layout.expandable, container, false);
				
			akvedaDag();
			determineNextAction();
			
			return rootView;
		}
	
	/**
	 * Akvedur hvad a ad gera, ef gagnagrunnurinn er tomur og tetta er fyrsta kallid, ta hefur hun ekki hladist
	 * i startUpScreen og tvi er notandinn liklegast ekki nettengdur. Tvi birtist toast um ad tengjast turfi netinu
	 * Annars birta toflu
	 */
	private void determineNextAction(){
		mDataSource.open();
		if (mDataSource.isEmpty() && getArguments().getString("update")=="0")
			Toast.makeText(getActivity(),"Tengstu netinu til að sjá stundatöfluna", Toast.LENGTH_LONG)
			.show();
		else birtaToflu();
	}
	 
	/**
	 * Finnur ut hvada dag a birta a voldu sidunni
	 * 
	 */
	public void akvedaDag(){
		int position = getArguments().getInt("position");
		TextView t = (TextView) rootView.findViewById(R.id.opnun_header);
		String[] weekdays = Global.weekdayArray;
		String weekday = weekdays[position];
		String weekdayFormatForDB = Global.weekdayFormatForDB(weekdays[position]);
		t.setText(weekday);
		mDataSource = new DataSource(getActivity(), weekdayFormatForDB);
	}
	
	/**
	 * Birtir videigandi stundatoflu
	 * 
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
	
	/**
	 * Gerum listaitemid ekki clickable tegar listinn er notadur til ad birta ekkert er valid
	 * 
	 */
	private void makeUnclickable(){
		listAdapter = new ExpandableListAdapter(getActivity(), st.listHeader, st.listChild, st.infoChild){
			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition){
				return false;
			}
		};
		expListView.setAdapter(listAdapter);
	}
	
	/**
	 * Stillum listener tegar klikkad er a hlut i stundatoflunni
	 */
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
				if(mDataSource.notendatimiExists(uid, htid) && Global.isUserLoggedIn(getActivity()))
					info = "Tíminn er þegar til staðar í Mín Stundatafla.";
				else if (!mDataSource.notendatimiExists(uid, htid) && Global.isUserLoggedIn(getActivity()))
					info = "Tímanum var bætt í Mín Stundatafla.";
				else
					info = "Skráðu þig inn til að bæta tímum við í stundatöflu";
				
				
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
	
	/**
	 * Expandum morguntima ef tad er morgun, o.s.frv.
	 * 
	 */
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
