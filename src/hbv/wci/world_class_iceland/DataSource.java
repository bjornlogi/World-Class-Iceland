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
import android.util.Log;

/*
 * Test import
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
 
import net.sourceforge.jtds.jdbc.*;

/**
 * Er notadur til tess ad lesa ur gagnagrunni.
 * 
 * @author Maria og Sonja
 */
public class DataSource {
	
	private SQLiteDatabase mSQLiteDatabase;
	private MySQLiteHelper mSQLiteHelper;
	private String dagur;
	private String[] filter;
	List<String> listHeader;
	HashMap<String, List<String>> listChild;
	HashMap<String, String> infoChild;
	
	private String[] hoptimarAllColumns = {
		MySQLiteHelper.COLUMN_ID,
		MySQLiteHelper.NAFN,
		MySQLiteHelper.STOD,
		MySQLiteHelper.SALUR,
		MySQLiteHelper.TJALFARI,
		MySQLiteHelper.TEGUND,
		MySQLiteHelper.KLUKKAN,
		MySQLiteHelper.TIMI,
		MySQLiteHelper.DAGUR,
		MySQLiteHelper.LOKAD
	};
	
	private String[] notendurAllColumns = {
		MySQLiteHelper.COLUMN_ID,
		MySQLiteHelper.NETFANG,
		MySQLiteHelper.LYKILORD,
		MySQLiteHelper.STADFEST,
		MySQLiteHelper.KORT
	};
	
	
	public DataSource (Context context, String dagurInntak){
		mSQLiteHelper = new MySQLiteHelper(context);
		dagur = dagurInntak;
	}
	
	/**
	 * Upphafsstillir
	 * 
	 * @param context
	 */
	public DataSource (Context context) {
		mSQLiteHelper = new MySQLiteHelper(context);
	}
	
	/**
	 * Opnar gagnagrunninn
	 * 
	 * @throws SQLiteException
	 */
	public void open() throws SQLiteException {
		mSQLiteDatabase = mSQLiteHelper.getWritableDatabase();
	}
	
	/**
	 * Lokar gagnagrunninum
	 */
	public void close() {
		mSQLiteHelper.close();
	}
	
	/**
	 * Baetir vid einum hoptima vid gagnagrunninn
	 * 
	 * @param hoptimi
	 */
	public void addHoptimi(String[] hoptimi){
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
	
	public boolean isEmpty(){
		try{
		 Cursor cursor = mSQLiteDatabase.query(MySQLiteHelper.TABLE_HOPTIMAR, hoptimarAllColumns, null, null, null, null, null);
		 boolean empty = !cursor.moveToFirst();
		 cursor.close();
		 return empty;
		}
		catch (Exception e){
			System.out.println("Error: " + e);
			return true;
		}
	}
	
	public StundatofluTimi getAllStundatoflutimar(){
		listHeader = new ArrayList<String>();
        listChild = new HashMap<String, List<String>>();
        infoChild = new HashMap<String,String>();
        
        List<String> morguntimar = new ArrayList<String>();
        List<String> hadegistimar = new ArrayList<String>();
        List<String> siddegistimar = new ArrayList<String>();
        List<String> kvoldtimar = new ArrayList<String>();
        
        Cursor cursor = mSQLiteDatabase.query(MySQLiteHelper.TABLE_HOPTIMAR, hoptimarAllColumns, null, null, null, null, null);
		cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
			//viljum ekki fa tofluheaderinn
			if(cursor.getLong(0) != 0) {
				Hoptimar hoptimi = cursorToHoptimar(cursor);
				if(hoptimi !=null) {
					if (cursor.getString(7).equals("morgun"))
						morguntimar.add(cursor.getString(1)+"$id"+cursor.getString(0));
					else if (cursor.getString(7).equals("hadegi"))
						hadegistimar.add(cursor.getString(1)+"$id"+cursor.getString(0));
					else if (cursor.getString(7).equals("siddegi"))
						siddegistimar.add(cursor.getString(1)+"$id"+cursor.getString(0));
					else
						kvoldtimar.add(cursor.getString(1)+"$id"+cursor.getString(0));
					
					infoChild.put("id"+cursor.getString(0), hoptimi.toString());
				}
				cursor.moveToNext();
			}		
		}
		
		int i = 0;
        if (!morguntimar.isEmpty()){
        	listHeader.add("Morguntímar");
        	listChild.put(listHeader.get(i++), morguntimar);
        }
        if (!hadegistimar.isEmpty()){
        	listHeader.add("Hádegistímar");
        	listChild.put(listHeader.get(i++), hadegistimar);
        	}
        if (!siddegistimar.isEmpty()){
        	listHeader.add("Síðdegistímar");
        	listChild.put(listHeader.get(i++), siddegistimar);
        	}
        if (!kvoldtimar.isEmpty()){
        	listHeader.add("Kvöldtímar");
        	listChild.put(listHeader.get(i), kvoldtimar);
        	}
        
        cursor.close();
		return new StundatofluTimi(listHeader, listChild,infoChild);
	}
	
	/**
	 * Naer i alla timana ur gagnagrunninum og skilar teim i lista af hakktoflum
	 * 
	 * @return List
	 */
//	public List<Map<String, String>> getAllHoptimar(){
//		
//		List<Map<String, String>> hoptimar = new ArrayList<Map<String, String>>();
//		
//		Cursor cursor = mSQLiteDatabase.query(MySQLiteHelper.TABLE_HOPTIMAR, hoptimarAllColumns, null, null, null, null, null);
//		cursor.moveToFirst();
//		while (!cursor.isAfterLast()) {
//			//viljum ekki fa tofluheaderinn
//			if(cursor.getLong(0) != 0) {
//				
//				Hoptimar hoptimi = cursorToHoptimar(cursor);
//				if(hoptimi !=null) {
//					Map<String, String> map = new HashMap<String, String>(2);
//			    	map.put("timi", cursor.getString(1));
//			    	map.put("klukkan", hoptimi.toString());
//			    	hoptimar.add(map);
//				}
//				cursor.moveToNext();
//			}		
//		}
//		cursor.close();
//		return hoptimar;
//	}
	
	/**
	 * Bætir við einum notanda í gagnagrunninn.
	 * 
	 * @param user 
	 */
	public void addUser(String[] user) {
		ContentValues values = new ContentValues();
		
		values.put(MySQLiteHelper.NETFANG, user[0]);
		values.put(MySQLiteHelper.LYKILORD, user[1]);
		values.put(MySQLiteHelper.KENNITALA, user[2]);
		values.put(MySQLiteHelper.STADFEST, user[3]);
		values.put(MySQLiteHelper.KORT, user[4]);
		
		mSQLiteDatabase.insert(MySQLiteHelper.TABLE_NOTENDUR, null, values);	
	}
	
	/**
	 * 
	 * @param netfang
	 * @param lykilord
	 * @return
	 */
	public boolean checkUser(String netfang, String lykilord) {
		Cursor cursor = mSQLiteDatabase.query(MySQLiteHelper.TABLE_NOTENDUR, notendurAllColumns, null, null, null, null, null);
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			Notandi notandi = cursorToNotandi(cursor);
			if(notandi.getLykilord().equals(lykilord) && notandi.getNetfang().equals(netfang)) {
				return true;
			}
			cursor.moveToNext();
		}
		cursor.close();
		
		return false;
	}
	
	/**
	 * Nidurstodur ur vali notendans
	 * @param stod
	 * @param tegund
	 */
	public void filter(String stod, String tegund){
		filter = new String[2];
		filter[0] = stod;
		filter[1] = tegund;
	}
	
	/**
	 * Lesin er ein rod i gagnagrunninum.
	 * 
	 * @param cursor
	 * @return Hoptimar geymir eina rod ur gagnagrunninum
	 */
	private Hoptimar cursorToHoptimar(Cursor cursor) {
		Hoptimar hoptimi = new Hoptimar();
		
		String stod = "Allar stöðvar";
		String tegund = "Allar tegundir";
		if(filter != null){
			stod = filter[0];
			tegund = filter[1];
		}
		
		
		if ((stod=="Allar stöðvar" || cursor.getString(2).equals(stod)) 
				&& (tegund=="Allar tegundir"|| cursor.getString(5).equals(tegund)) 
				&& cursor.getString(8).equals(this.dagur))
		{			 
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
	
	/**
	 * 
	 * @param cursor
	 * @return
	 */
	private Notandi cursorToNotandi(Cursor cursor) {
		return new Notandi(cursor.getLong(0), 
							cursor.getString(1), 
							cursor.getString(2), 
							cursor.getString(3), 
							cursor.getString(4));
	}

}
