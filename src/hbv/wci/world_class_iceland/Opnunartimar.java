package hbv.wci.world_class_iceland;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

/**
 * Activity sem synir valmynd til tess ad sja opnunartima stodva. 
 * 
 * @author Bjorn
 * @see Activity
 */
public class Opnunartimar extends Activity {

	/**
	 * Byr til skjainn, bindur layout ur opnunartimar.xml við skjainn. Tengir virkni við takkana ur layout-i.
	 *
	 * @param savedInstanceState a Bundle which does something
	 * @return none
	 * @see Bundle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opnunartimar);

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
				j.putExtra("stod","Spöngin");
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
				j.putExtra("stod","Egilshöll");
				startActivity(j);
			}
		});
		
		hfj_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","Hafnarfjörður");
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
				j.putExtra("stod","Mosfellsbær");
				startActivity(j);
			}
		});
		
		ogur_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","Ögurhvarf");
				startActivity(j);
			}
		});
		
		kpv_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","Kópavogur");
				startActivity(j);
			}
		});
		
		hr_takki.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{ 
				Intent j = new Intent(Opnunartimar.this, Opnunartimi.class); 
				j.putExtra("stod","Háskólinn í Reykjavík");
				startActivity(j);
			}
		});
		
	}

}