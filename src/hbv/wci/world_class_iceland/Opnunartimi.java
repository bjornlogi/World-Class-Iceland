package hbv.wci.world_class_iceland;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Opnunartimi extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opnunartimi);
		
		Intent myIntent= getIntent();
		String stod = myIntent.getStringExtra("stod");
		TextView titill = (TextView)findViewById(R.id.otimi_titill);
		titill.setText(stod);
		
	}
	
	
}
