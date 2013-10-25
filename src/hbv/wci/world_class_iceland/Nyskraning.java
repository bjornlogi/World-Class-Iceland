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
		// TODO bæta við on change listener og athuga hvort lykilorðin séu eins
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nyskraning);
		
		final EditText netfang = (EditText) findViewById(R.id.netfangInntak2);
		final EditText lykilord = (EditText) findViewById(R.id.lykilordInntakNr1);
		lykilord.setTypeface(Typeface.SANS_SERIF);
		final EditText lykilord2 = (EditText) findViewById(R.id.lykilordInntakNr2);
		lykilord2.setTypeface(Typeface.SANS_SERIF);
		final EditText kennitala = (EditText) findViewById(R.id.kennitalaInntak);
		
		
		final Button nySkra = (Button) findViewById(R.id.nySkraTakki);
		nySkra.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDataSource = new DataSource(mContext);
				mDataSource.open();
				
				
				//[netfang,lykilord,kennitala,stadfest,kort]
				mDataSource.addUser(new String[]{netfang.getText().toString(),lykilord.getText().toString(),kennitala.getText().toString(),"nei","nei"});
				
				Intent j = new Intent(Nyskraning.this, Innskraning.class);
				startActivity(j);
			}
		});
		
		
	}
}
 