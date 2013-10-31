package hbv.wci.world_class_iceland;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Nyskraning extends Activity {
	private DataSource mDataSource;
	public Context mContext = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nyskraning);
		
		final EditText netfang = (EditText) findViewById(R.id.netfangInntak2);
		final EditText lykilord = (EditText) findViewById(R.id.lykilordInntakNr1);
		final EditText lykilord2 = (EditText) findViewById(R.id.lykilordInntakNr2);
		final EditText kennitala = (EditText) findViewById(R.id.kennitalaInntak);
		final Button nySkra = (Button) findViewById(R.id.nySkraTakki);
		
		lykilord.setTypeface(Typeface.SANS_SERIF);
		lykilord2.setTypeface(Typeface.SANS_SERIF);
		
		nySkra.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDataSource = new DataSource(mContext);
				mDataSource.open();
				
				
				
				// TODO Validate the input to addUser, and toast if there is mismatch
				//[netfang,lykilord,kennitala,stadfest,kort],
				// TODO ef gefid upp netfang, lykilord og ytt a nyskra, baeta vid gognum inn i nyskraningar inputin
				//svo ekki turfi ad skrifa aftur
				Boolean validEmail = validate(netfang.getText().toString());
				Boolean noEmptyField = isNotEmptyNyskra(netfang.getText().toString(), lykilord.getText().toString(), kennitala.getText().toString(), lykilord2.getText().toString());
				Boolean validKennitala = isKennitala(kennitala.getText().toString());
				Boolean passwordMatch = passwordMatch(lykilord.getText().toString(), lykilord2.getText().toString());
				if (validEmail && noEmptyField && validKennitala && passwordMatch)
				{
					mDataSource.addUser(new String[]{netfang.getText().toString(),lykilord.getText().toString(),kennitala.getText().toString(),"nei","nei"});
					
					Intent j = new Intent(Nyskraning.this, Innskraning.class);
					startActivity(j);
				}			
				else{
					Toast.makeText(Nyskraning.this, R.string.rangt, Toast.LENGTH_LONG).show();
				}
									
			}
		});		
	}
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
	    return matcher.find();
	}
	public static boolean isNotEmptyNyskra(String email, String password, String kennitala, String lykilord2){
		if(email != "" && password != "" && kennitala != "" && lykilord2 != "")
			return true;
		else
			return false;		
	}
	public static boolean isKennitala(String kennitala){
		if(kennitala.length() == 10)
			return true;
		else
			return false;
	}
	public static boolean passwordMatch(String lykilord1, String lykilord2){
		if(lykilord1.equals(lykilord2)){
			return true;
		}
		else
			return false;
	}
	 
}
