package hbv.wci.world_class_iceland;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Les ur gagnagrunni.
 * 
 * @author Maria og Sonja
 */
public class DataSource {
	
	private SQLiteDatabase mSQLiteDatabase;
	private MySQLiteHelper mSQLiteHelper;
	
	private String[] mAllColumns = {
			MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.NAFN,
			MySQLiteHelper.STOD,
			MySQLiteHelper.SALUR,
			MySQLiteHelper.TJALFARI,
			MySQLiteHelper.TEGUND,
			MySQLiteHelper.KLUKKAN,
			MySQLiteHelper.TIMI,
			MySQLiteHelper.DAGUR,
			MySQLiteHelper.LOKAD};
	
	/**
	 * 
	 * 
	 * @param context
	 */
	public DataSource (Context context){
		mSQLiteHelper = new MySQLiteHelper(context);
	}
	
	/**
	 * 
	 * @throws SQLiteException
	 */
	public void open() throws SQLiteException {
		mSQLiteDatabase = mSQLiteHelper.getWritableDatabase();
	}
	
	public void close() {
		mSQLiteHelper.close();
	}
	
	/**
	 * 
	 * @param hoptimi
	 */
	public void addHoptimi(String []hoptimi){
		ContentValues values = new ContentValues();
		
		values.put(MySQLiteHelper.NAFN, hoptimi[0]);
		values.put(MySQLiteHelper.STOD, hoptimi[1]);
		values.put(MySQLiteHelper.SALUR, hoptimi[2]);
		values.put(MySQLiteHelper.TJALFARI, hoptimi[3]);
		values.put(MySQLiteHelper.TEGUND, hoptimi[4]);
		values.put(MySQLiteHelper.KLUKKAN, hoptimi[5]);
		values.put(MySQLiteHelper.TIMI, hoptimi[6]);
		values.put(MySQLiteHelper.DAGUR, hoptimi[7]);
		values.put(MySQLiteHelper.LOKAD, hoptimi[8]);
		mSQLiteDatabase.insert(MySQLiteHelper.TABLE_HOPTIMAR, null, values);		
	}
	
	/**
	 * Naer i alla timana ur gagnagrunninum
	 * 
	 * @return List
	 */
	public List<Map<String, String>> getAllHoptimarr(){
		
		List<Map<String, String>> hoptimar = new ArrayList<Map<String, String>>();
		
		Cursor cursor = mSQLiteDatabase.query(MySQLiteHelper.TABLE_HOPTIMAR,
				mAllColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			//viljum ekki fa tofluheaderinn
			if(cursor.getLong(0) != 0){
				Hoptimar hoptimi = cursorToHoptimar(cursor);
				if(hoptimi !=null)
				{
					Map<String, String> map = new HashMap<String, String>(2);
			    	map.put("timi", cursor.getString(1));
			    	map.put("klukkan", hoptimi.toString());
			    	hoptimar.add(map);
				}
				cursor.moveToNext();
			}		
		}
		cursor.close();
		return hoptimar;
	}
	
	/**
	 * Naer i alla timana ur gagnagrunninum
	 * 
	 * @return List
	 */
	public List<Hoptimar> getAllHoptimar(){
		
		List <Hoptimar> hoptimar = new ArrayList<Hoptimar>();
		
		Cursor cursor = mSQLiteDatabase.query(MySQLiteHelper.TABLE_HOPTIMAR,
				mAllColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			//viljum ekki fa tofluheaderinn
			if(cursor.getLong(0) != 0){
				Hoptimar hoptimi = cursorToHoptimar(cursor);
				if(hoptimi !=null)
					hoptimar.add(hoptimi);
				cursor.moveToNext();
			}		
		}
		cursor.close();
		return hoptimar;
	}

	/**
	 * Lesin er ein rod i gagnagrunninum.
	 * 
	 * @param cursor
	 * @return Hoptimar geymir eina rod ur gagnagrunninum
	 */
	private Hoptimar cursorToHoptimar(Cursor cursor) {
		Hoptimar hoptimi = new Hoptimar();
		if (cursor.getString(2).equals("Laugar") && cursor.getString(8).equals("Fim")){			 
			hoptimi.setmId(cursor.getLong(0));		
			hoptimi.setNafn(cursor.getString(1));
			hoptimi.setStod(cursor.getString(2));
			hoptimi.setSalur(cursor.getString(3));
			hoptimi.setTjalfari(cursor.getString(4));
			hoptimi.setTegund(cursor.getString(5));
			hoptimi.setKlukkan(cursor.getString(6));
			hoptimi.setTimi(cursor.getString(7));
			hoptimi.setDagur(cursor.getString(8));
			hoptimi.setLokad(cursor.getString(9));
			return hoptimi;
		}	
		return null;
	}

}
