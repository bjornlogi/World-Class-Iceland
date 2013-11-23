package hbv.wci.world_class_iceland.data;


import hbv.wci.world_class_iceland.Global;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/*
 * Test import
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	private Context ctx;
	
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
	
	private String[] notendatimarAllColumns = {
			MySQLiteHelper.USERID,
			MySQLiteHelper.HOPTIMIID,
			MySQLiteHelper.NAFN,
			MySQLiteHelper.STOD,
			MySQLiteHelper.SALUR,
			MySQLiteHelper.TJALFARI,
			MySQLiteHelper.TEGUND,
			MySQLiteHelper.KLUKKAN,
			MySQLiteHelper.TIMI,
			MySQLiteHelper.DAGUR,
			MySQLiteHelper.AMINNING
		};
	
	/**
	 * Upphafsstillir sem tekur baedi context og dag a strengjaformi
	 * 
	 * @param context
	 * @param dagurInntak
	 */
	public DataSource (Context context, String dagurInntak){
		this.ctx = context;
		mSQLiteHelper = new MySQLiteHelper(context);
		dagur = dagurInntak;
	}
	
	/**
	 * Upphafsstillir sem tekur bara context
	 * 
	 * @param context
	 */
	public DataSource (Context context) {
		this.ctx = context;
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
	 * 
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
		
		String[] a = hoptimi[5].split(" - ");
		String b = hoptimi[5];
		
		String x="", y="";
		if(a[0].length()==4) {
			// t.d. ef a[0]=="6:30"
			x = "0" + a[0];
		}
		if(a[1].length()==4) {
			y = "0" + a[1];
		}
		if( x!="" || y!="" ) {
			b = x + " - " + y; 
		}
		
		
		values.put(MySQLiteHelper.NAFN, hoptimi[0]);
		values.put(MySQLiteHelper.STOD, hoptimi[1]);
		values.put(MySQLiteHelper.SALUR, hoptimi[2]);
		values.put(MySQLiteHelper.TJALFARI, hoptimi[3]);
		values.put(MySQLiteHelper.TEGUND, hoptimi[4]);
		values.put(MySQLiteHelper.KLUKKAN, b);
		values.put(MySQLiteHelper.TIMI, hoptimi[6]);
		values.put(MySQLiteHelper.DAGUR, hoptimi[7]);
		values.put(MySQLiteHelper.LOKAD, hoptimi[8]);
		mSQLiteDatabase.insert(MySQLiteHelper.TABLE_HOPTIMAR, null, values);		
	}
	
	public boolean notendatimiExists(int userID, int hoptimiID){
		String sql = "SELECT uid FROM notendatimar WHERE uid = ? AND htid = ?";
		Cursor c = mSQLiteDatabase.rawQuery(sql,  new String[] {Integer.toString(userID), Integer.toString(hoptimiID)});
		return !c.moveToFirst();
	}
	
	public void addNotendatimi(int userID, int hoptimiID){
		ContentValues values = new ContentValues();
		String[] info = getHoptimarInfo(hoptimiID);
		
		values.put("uid", userID);
		values.put("htid", hoptimiID);
		values.put(MySQLiteHelper.NAFN, info[0]);
		values.put(MySQLiteHelper.STOD, info[1]);
		values.put(MySQLiteHelper.SALUR, info[2]);
		values.put(MySQLiteHelper.TJALFARI, info[3]);
		values.put(MySQLiteHelper.TEGUND, info[4]);
		values.put(MySQLiteHelper.KLUKKAN, info[5]);
		values.put(MySQLiteHelper.TIMI, info[6]);
		values.put(MySQLiteHelper.DAGUR, info[7]);
		mSQLiteDatabase.insert(MySQLiteHelper.TABLE_NOTENDATIMAR, null, values);
	}
	
	public String[] getHoptimarInfo (int htid){
		String sql = "SELECT * FROM hoptimar WHERE _id = ?";
		Cursor c = mSQLiteDatabase.rawQuery(sql,  new String[] {Integer.toString(htid)});
		try{
			c.moveToFirst();
			return new String[]{
					c.getString(1),
					c.getString(2),
					c.getString(3),
					c.getString(4),
					c.getString(5),
					c.getString(6),
					c.getString(7),
					c.getString(8)
			};
		}
		catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Eydir tofluinni hoptimar ef hun er til. Tad er kallad a tetta fall ef asyncid sem naer i gognin
	 * haettir af einhverjum astaedum
	 * 
	 */
	public void dropTable(){
		mSQLiteDatabase.execSQL("DROP table if exists hoptimar");
	}
	
	/**
	 * Kostnadarlitid fall sem athugar hvort ad hoptimar se til eda ekki
	 * 
	 * @return true if taflan er tom, false annars
	 */
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
	
	public StundatofluTimi getAllStundataflanMinTimi(Context mContext){
		listHeader = new ArrayList<String>();
		listChild = new HashMap<String, List<String>>();
		infoChild = new HashMap<String,String>();
		
		//List<List<String>> dagar = new ArrayList<List<String>>(Context cotx);
		//dagar.add(0, new ArrayList<String>());
		//dagar.get(0);
		//Listi fyrir hvern dag
		List<String> man = new ArrayList<String>();
		List<String> tri = new ArrayList<String>();
		List<String> mid = new ArrayList<String>();
		List<String> fim = new ArrayList<String>();
		List<String> fos = new ArrayList<String>();
		List<String> lau = new ArrayList<String>();
		List<String> sun = new ArrayList<String>();
		
		//String sql = "SELECT * FROM notendatimar WHERE uid = ?";
		//Cursor cursor = mSQLiteDatabase.rawQuery(sql,  new String[] {Integer.toString(Global.getUsersID(mContext))});
		//HashMap<String, Integer> map = Global.mapIS;
		
		Cursor cursor = mSQLiteDatabase.query(MySQLiteHelper.TABLE_NOTENDATIMAR, notendatimarAllColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//TODO gera tetta med lykkju ef haegt, t.d. bua til fylki af listum
			//Hoptimar hoptimi = cursorToHoptimar(cursor);
			//dagar.get(map.get(cursor.getString(9))).add(cursor.getString(2)+"$id"+cursor.getString(1));
			//dagar[map.get(cursor.getString(9))].add(cursor.getString(2)+"$id"+cursor.getString(1));
			if (cursor.getString(9).equals("Man"))
				man.add(cursor.getString(2)+"$id"+cursor.getString(1));
			else if (cursor.getString(9).equals("Tri"))
				tri.add(cursor.getString(2)+"$id"+cursor.getString(1));
			else if (cursor.getString(9).equals("Mid"))
				mid.add(cursor.getString(2)+"$id"+cursor.getString(1));
			else if (cursor.getString(9).equals("Fim"))
				fim.add(cursor.getString(2)+"$id"+cursor.getString(1));
			else if (cursor.getString(9).equals("Fos"))
				fos.add(cursor.getString(2)+"$id"+cursor.getString(1));
			else if (cursor.getString(9).equals("Lau"))
				lau.add(cursor.getString(2)+"$id"+cursor.getString(1));
			else
				sun.add(cursor.getString(2) +"$id"+cursor.getString(1));
			
			infoChild.put("id"+cursor.getString(1), "Kemur bradum");
			cursor.moveToNext();
		}
		cursor.close();
		
		int i = 0;
		if (!man.isEmpty()){
			listHeader.add("Mánudagur");
			listChild.put(listHeader.get(i++), man);
		}
		if (!tri.isEmpty()){
			listHeader.add("Þriðjudagur");
			listChild.put(listHeader.get(i++), tri);
		}
		if (!mid.isEmpty()){
			listHeader.add("Miðvikudagur");
			listChild.put(listHeader.get(i++), mid);
		}
		if (!fim.isEmpty()){
			listHeader.add("Fimmtudagur");
			listChild.put(listHeader.get(i), fim);
		}
		if (!fos.isEmpty()){
			listHeader.add("Föstudagur");
			listChild.put(listHeader.get(i++), fos);
		}
		if (!lau.isEmpty()){
			listHeader.add("Laugardagur");
			listChild.put(listHeader.get(i++), lau);
		}
		if (!sun.isEmpty()){
			listHeader.add("Sunnudagur");
			listChild.put(listHeader.get(i), sun);
		}
		
		return new StundatofluTimi(listHeader, listChild,infoChild);
	}
	
	/**
	 * Skilar Object sem hysir lista og tvaer hakktoflur svo haegt se ad vinna ur gognunum
	 * og setja i stundatofluna
	 * 
	 * @return StundatofluTimi med videigandi timum
	 */
	public StundatofluTimi getAllStundatoflutimar(){
		listHeader = new ArrayList<String>();
		listChild = new HashMap<String, List<String>>();
		infoChild = new HashMap<String,String>();
		
		List<String> morguntimar = new ArrayList<String>();
		List<String> hadegistimar = new ArrayList<String>();
		List<String> siddegistimar = new ArrayList<String>();
		List<String> kvoldtimar = new ArrayList<String>();
		List<String> nothingFound = new ArrayList<String>();
		
		Cursor cursor = mSQLiteDatabase.query(MySQLiteHelper.TABLE_HOPTIMAR, hoptimarAllColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//viljum ekki fa tofluheaderinn
			if(cursor.getLong(0) != 0) {
				Hoptimar hoptimi = cursorToHoptimar(cursor);
				if(hoptimi !=null) {
					if (hoptimi.getTimi().equals("morgun"))
						morguntimar.add(cursor.getString(1)+"$id"+cursor.getString(0));
					else if (hoptimi.getTimi().equals("hadegi"))
						hadegistimar.add(cursor.getString(1)+"$id"+cursor.getString(0));
					else if (hoptimi.getTimi().equals("siddegi"))
						siddegistimar.add(cursor.getString(1)+"$id"+cursor.getString(0));
					else
						kvoldtimar.add(cursor.getString(1)+"$id"+cursor.getString(0));
					
					infoChild.put("id"+cursor.getString(0), hoptimi.toString());
				}
				cursor.moveToNext();
			}		
		}
		cursor.close();
		
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
		
		if (noHoptimar(morguntimar,hadegistimar,siddegistimar,kvoldtimar)){
			listHeader.add("Enginn tími fannst");
			nothingFound.add("Því miður fannst enginn tími undir gefnum leitarskilyrðum$id-1");
			infoChild.put("id-1", "Þú getur prófað að breyta þeim eða skoða annan dag.");
			listChild.put(listHeader.get(0), nothingFound);
		}
		
		
		return new StundatofluTimi(listHeader, listChild,infoChild);
	}
	
	public boolean noHoptimar(List<String> mt, List<String> ht, List<String> st, List<String> kt)
	{
		return mt.isEmpty() && ht.isEmpty() && st.isEmpty() && kt.isEmpty();
	}
	
	/**
	 * Bætir við einum notanda í gagnagrunninn.
	 * 
	 * @param user 
	 */
	public void addUser(String[] user, Context ctx) {
		ContentValues values = new ContentValues();
		
		values.put(MySQLiteHelper.NETFANG, user[0]);
		values.put(MySQLiteHelper.LYKILORD, user[1]);
		values.put(MySQLiteHelper.KENNITALA, user[2]);
		values.put(MySQLiteHelper.STADFEST, user[3]);
		values.put(MySQLiteHelper.KORT, user[4]);
		mSQLiteDatabase.insert(MySQLiteHelper.TABLE_NOTENDUR, null, values);	
		checkUser(user[0], user[1], ctx);
	}
	
	/**
	 * Athugar hvort ad user-lykilord comboid se til inni gagnagrunni
	 * 
	 * @param netfang
	 * @param lykilord
	 * @return boolean um hvort rett lykilord var gefid fyrir netfangid
	 */
	public boolean checkUser(String netfang, String lykilord, Context ctx) {
		
		try{
			String sql = "SELECT _id,netfang,lykilord FROM notendur WHERE netfang = ? AND lykilord = ?";
			Cursor c = mSQLiteDatabase.rawQuery(sql,  new String[] {netfang, lykilord});
			c.moveToFirst();	
			Global.setUser(ctx, (int)(long)c.getLong(0), c.getString(1));
		} catch (Exception e){
			return false;
		}
		return true;
	}
	
	/**
	 * Athugar hvort ad username se til i nyskraningunni tar sem tveir geta ekki verid med tad sama
	 * 
	 * @param netfang
	 * @return hvort user se til i kerfinu nutegar
	 */
	public boolean userExists(String netfang){
		try{
			String sql = "SELECT _id FROM notendur WHERE netfang = ?";
			Cursor c = mSQLiteDatabase.rawQuery(sql,  new String[] {netfang});
			//System.out.println();
			return c.moveToFirst();	
		} catch (Exception e){
			return false;
		}
	}
	
	/**
	 * Nidurstodur ur vali notendans
	 * @param stod sem var valin
	 * @param tegund sem var valin
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
	 * Skilar nyjum notenda ef allt gengur upp
	 * @param cursor
	 * @return gildur notandi
	 */
	private Notandi cursorToNotandi(Cursor cursor) {
		return new Notandi(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
	}

}
