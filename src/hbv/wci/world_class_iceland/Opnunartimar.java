package hbv.wci.world_class_iceland;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class Opnunartimar extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opnunartimar);

		Button spong_takki = (Button) this.findViewById(R.id.spong_opnun);
		Button kringlan_takki = (Button) this.findViewById(R.id.kringlan_opnun);
		Button laugar_takki = (Button) this.findViewById(R.id.laugar_opnun);
		Button egils_takki = (Button) this.findViewById(R.id.egils_opnun);
		Button hfj_takki = (Button) this.findViewById(R.id.hfj_opnun);
		Button nes_takki = (Button) this.findViewById(R.id.nes_opnun);
		Button mosfells_takki = (Button) this.findViewById(R.id.mosfells_opnun);
		Button ogur_takki = (Button) this.findViewById(R.id.ogur_opnun);
		Button kpv_takki = (Button) this.findViewById(R.id.kop_opnun);
		Button hr_takki = (Button) this.findViewById(R.id.hr_opnun);
		
	 
		spong_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","Sp�ng");
				startActivity(j);
			}
		});
		
		kringlan_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","Kringlan");
				startActivity(j);
			}
		});
		
		laugar_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","Laugar");
				startActivity(j);
			}
		});
		
		egils_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","Egilsh�ll");
				startActivity(j);
			}
		});
		
		hfj_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","Hafnarfj�r�ur");
				startActivity(j);
			}
		});
		
		nes_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","Seltjarnarnes");
				startActivity(j);
			}
		});
		
		mosfells_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","Mosfellsb�r");
				startActivity(j);
			}
		});
		
		ogur_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","�gurhvarf");
				startActivity(j);
			}
		});
		
		kpv_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","K�pavogur");
				startActivity(j);
			}
		});
		
		hr_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","H�sk�linn � Reykjav�k");
				startActivity(j);
			}
		});
		
	}

}