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
				//[netfang,lykilord,kennitala,stadfest,kort]
				mDataSource.addUser(new String[]{netfang.getText().toString(),lykilord.getText().toString(),kennitala.getText().toString(),"nei","nei"});
				
				Intent j = new Intent(Nyskraning.this, Innskraning.class);
				startActivity(j);
			}
		});
		
	}
}
 