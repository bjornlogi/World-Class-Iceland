package hbv.wci.world_class_iceland.skraning;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Dialog;
import android.content.res.Configuration;

import hbv.wci.world_class_iceland.Global;
import hbv.wci.world_class_iceland.R;
import hbv.wci.world_class_iceland.data.DataSource;
import hbv.wci.world_class_iceland.opnunartimar.Opnunartimar;
import hbv.wci.world_class_iceland.stundatafla.AlmennStundatafla;
import hbv.wci.world_class_iceland.stundatafla.StundataflanMin;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Nyskraning extends Activity {
	private DataSource mDataSource;
	public Context mContext = this;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	EditText netfang;
	EditText lykilord;
	EditText lykilord2;
	EditText kennitala;
	Button nySkra;
	Context context = this;
	
	private Boolean validEmail;
	private Boolean noEmptyField;
	private Boolean validKennitala;
	private Boolean passwordMatch;
	private Boolean emailAvailable;
	
	private Map<String,Integer> map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nyskraning);
		initialiseFields();
		checkUserInput();
		setDrawer();
	}
	
	private void initialiseFields(){
		lykilord = (EditText) findViewById(R.id.lykilordInntakNr1);
		lykilord2 = (EditText) findViewById(R.id.lykilordInntakNr2);
		lykilord.setTypeface(Typeface.SANS_SERIF);
		lykilord2.setTypeface(Typeface.SANS_SERIF);
		netfang = (EditText) findViewById(R.id.netfangInntak2);
		kennitala = (EditText) findViewById(R.id.kennitalaInntak);
	}
	
	/**
	 * Athugar inntak notendans i innskraningunni
	 * 
	 */
	private void checkUserInput(){
		
		
		nySkra = (Button) findViewById(R.id.nySkraTakki);
		nySkra.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDataSource = new DataSource(mContext);
				mDataSource.open();

				// TODO ef gefid upp netfang, lykilord og ytt a nyskra, baeta vid gognum inn i nyskraningar inputin
				//svo ekki turfi ad skrifa aftur
				if (validateInput()){
					createSuccessDialog();
				}		
				else createFailureDialog();				
			}
		});
	}
	
	/**
	 * Kannar hvort netfang-lykilord comboid se til i gagnagrunni og skilar boolean
	 * @return boolean um hvort notandi se til
	 */
	private boolean validateInput(){
		validEmail = validate(netfang.getText().toString());
		noEmptyField = isNotEmptyNyskra(netfang.getText().toString(), lykilord.getText().toString(), kennitala.getText().toString(), lykilord2.getText().toString());
		validKennitala = isKennitala(kennitala.getText().toString());
		passwordMatch = passwordMatch(lykilord.getText().toString(), lykilord2.getText().toString());
		emailAvailable = !mDataSource.userExists(netfang.getText().toString());
		return validEmail && noEmptyField && validKennitala && passwordMatch && emailAvailable;
	}
	
	/**
	 * Byr til dialog sem birtir upplysingar um ad innskraning hafi tekist
	 * 
	 */
	private void createSuccessDialog(){
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_nyskra);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setTitle("Til hamingju");

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		String info ="Þú ert nú skráður í kerfið. Upplýsingar þínar:";
		info += "\nnetfang: " + netfang.getText().toString();
		info += "\nkennitala: " + kennitala.getText().toString();
		info += "\nVinsamlegast ýttu á 'OK' til að halda áfram.";
								
		text.setText(info);
		
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Global.currentUser = netfang.getText().toString();
				mDataSource.addUser(new String[]{netfang.getText().toString(),lykilord.getText().toString(),kennitala.getText().toString(),"nei","nei"}, mContext);
				Intent j = new Intent(Nyskraning.this, AlmennStundatafla.class);
				startActivity(j);
			}
		});

		dialog.show();
	}
	
	/**
	 * Byr til dialog sem birtir upplysingar um ad innskraning hafi ekki tekist
	 * 
	 */
	private void createFailureDialog(){
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_nyskra);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setTitle("Villa");

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		String villa ="Vinsamlegast lagaðu eftirfarandi:";
		if(!emailAvailable)
			villa += "\nNetfang er þegar skráð.";
		else {
			if(!validEmail)
				villa += "\nNetfang er ekki á réttu formi.";
			if(!validKennitala)
				villa += "\nKennitala er ekki á réttu formi.";
			if(!passwordMatch)
				villa += "\nLykilorð stemma ekki.";
			if(!noEmptyField)
				villa += "\nFylla verður út í alla reiti.";
		}
					
		text.setText(villa);
		
		
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
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
	    return matcher.find();
	}
	public static boolean isNotEmptyNyskra(String email, String password, String kennitala, String lykilord2){
		return (!email.isEmpty() && !password.isEmpty() && !kennitala.isEmpty() && !lykilord2.isEmpty());	
	}
	public static boolean isKennitala(String kennitala){
		return(kennitala.length() == 10);
	}
	public static boolean passwordMatch(String lykilord1, String lykilord2){
		return(lykilord1.equals(lykilord2));
	}
	
	public void setDrawer()	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_nyskraning);
		mDrawerList = (ListView) findViewById(R.id.left_drawer_nyskraning);
		mDrawerToggle = Global.setDrawer(mContext, mDrawerLayout, mDrawerList, this);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggle
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, Global.determineListItems(mContext)));
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) return true;
		return super.onOptionsItemSelected(item);
	}
	 
}
