package hbv.wci.world_class_iceland.data;


import hbv.wci.world_class_iceland.Global;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

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
		
		String b = "";
		
		String x="", y="";
		if(a[0].length()==4) { x = "0" + a[0]; }
		else { x = a[0]; }
		
		if(a[1].length()==4) { y = "0" + a[1]; }
		else { y = a[1]; }
		
		if( x!="" || y!="" ) { b = x + " - " + y; }
		
		
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
	
	/**
	 * Kannar hvort timi se til i Stundatoflu notendans.
	 * 
	 * @param userID, audkenni notendans
	 * @param hoptimiID, audkenni hoptimans
	 * @return boolean hvort timinn se til stadar
	 */
	public boolean notendatimiExists(int userID, int hoptimiID){
		String sql = "SELECT uid FROM notendatimar WHERE uid = ? AND htid = ? AND isEinkatimi = 'false'";
		Cursor c = mSQLiteDatabase.rawQuery(sql,  new String[] {Integer.toString(userID), Integer.toString(hoptimiID)});
		return c.moveToFirst();
	}
	
	/**
	 * Baetir vid einkatima sem notandinn bjo til i gagnagrunn
	 * 
	 * @param name, nafn timans sem notandinn skirdi
	 * @param time, timasetning timans sem notandinn valdi af spinner
	 * @param weekday, vikudagur sem var valinn i Man, Tri, Mid o.s.frv. formati.
	 * @param descr, lysing timans ef hun er til stadar sem er sett i 'stod' dalkinn
	 */
	public void addEinkatimi(String name, String time, String weekday, String descr){
		//Byrjum a ad finna staersta idid og baetum einum vid tad
		String sql = "select max(htid) from notendatimar where isEinkatimi = 'true'";
		Cursor c = mSQLiteDatabase.rawQuery(sql,null);
		int htid;
		if (!c.moveToFirst())
			htid = 0;
		else
			htid = (int)(long)c.getLong(0)+1;
		ContentValues values = new ContentValues();
		values.put("uid", Global.getUsersID(this.ctx));
		values.put("htid", htid);
		values.put(MySQLiteHelper.NAFN, name);
		values.put(MySQLiteHelper.STOD, descr);
		values.put(MySQLiteHelper.SALUR, "");
		values.put(MySQLiteHelper.TJALFARI, "");
		values.put(MySQLiteHelper.TEGUND, "");
		values.put(MySQLiteHelper.KLUKKAN, time);
		values.put(MySQLiteHelper.TIMI, "");
		values.put(MySQLiteHelper.DAGUR, weekday);
		values.put(MySQLiteHelper.AMINNING, "false");
		values.put(MySQLiteHelper.ISEINKA, "true");
		mSQLiteDatabase.insert(MySQLiteHelper.TABLE_NOTENDATIMAR, null, values);
	}
	
	/**
	 * Baetir voldum tima ur almennum tima i mina stundatoflu 
	 * 
	 * @param userID, audkenni notendans
	 * @param hoptimiID, audkenni hoptimans
	 */
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
		values.put(MySQLiteHelper.AMINNING, "false");
		values.put(MySQLiteHelper.ISEINKA, "false");
		mSQLiteDatabase.insert(MySQLiteHelper.TABLE_NOTENDATIMAR, null, values);
	}
	
	/**
	 * Uppfaerir aminningu i true eda false i gagnagrunni
	 * 
	 * @param aminning
	 * @param id
	 */
	public void updateAminning(String aminning, String id){
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.AMINNING, aminning);
		mSQLiteDatabase.update(MySQLiteHelper.TABLE_NOTENDATIMAR, values, "htid="+ id, null);
	}
	
	/**
	 * Finnur ut hvort aminnning se true eda false i gagnagrunni
	 * @param id
	 * @return Strengur um hvort aminningin se sett a true eda false
	 */
	public String getAminning(String id){
		String sql = "SELECT * FROM notendatimar WHERE htid = ?";
		Cursor c = mSQLiteDatabase.rawQuery(sql,  new String[] {id});
		c.moveToFirst();
		String a = c.getString(10);
		c.close();
		return a;
	}
	
	/**
	 * Naer i upplysingar um hoptima fyrir valid ID og strengjafylki
	 * @param htid, audkenni hoptimans
	 * @return strengjafylki med upplysingum ur gagnagrunni
	 */
	public String[] getHoptimarInfo (int htid){
		String sql = "SELECT * FROM hoptimar WHERE _id = ?";
		Cursor c = mSQLiteDatabase.rawQuery(sql,  new String[] {Integer.toString(htid)});
		try{
			c.moveToFirst();
			String[] a = new String[]{
					c.getString(1),//nafn
					c.getString(2),//stod
					c.getString(3),//salur
					c.getString(4),//tjalfari
					c.getString(5),//tegund
					c.getString(6),//klukkan
					c.getString(7),//timi, morgun, hadegi o.s.frv.
					c.getString(8) //dagur
			};
			c.close();
			return a;
		}
		catch(Exception e){
			c.close();
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
	
	/**
	 * Naer i alla timana sem hafa verid skradir i Stundatoflu notendans.
	 * 
	 * @param mContext
	 * @return Listi og tvaer hakktoflur i formi StundatofluTima
	 */
	public StundatofluTimi getAllStundataflanMinTimi(Context mContext){
		listHeader = new ArrayList<String>();
		listChild = new HashMap<String, List<String>>();
		infoChild = new HashMap<String,String>();
		
		String sql = "SELECT * FROM notendatimar WHERE uid = ? ORDER BY klukkan ASC";
		Cursor c = mSQLiteDatabase.rawQuery(sql,  new String[] {Integer.toString(Global.getUsersID(mContext))});
		
		List<List<String>> dagar = new ArrayList<List<String>>();
		for (int i = 0; i < 7; i++)
			dagar.add(i, new ArrayList<String>());
		
		HashMap<String, Integer> map = Global.mapIS;
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Hoptimar hoptimi = cursorToMinnTimi(c);
			
			int d = map.get(hoptimi.getDagur());
			String isEinka = "";
			if(hoptimi.getIsEinka().equals("true")) isEinka = "e";
			
			String ID = "$id"+hoptimi.getmId()+isEinka;
			dagar.get(d).add(hoptimi.getNafn()+ID);
			
			ID = "id"+hoptimi.getmId()+isEinka;
			infoChild.put(ID, hoptimi.toString());
			
			c.moveToNext();
		}
		c.close();
		
		String[] weekdays = Global.weekdayArray;
		
		int j = 0;
		for (int k = 0; k < 7; k++){
			if (!dagar.get(k).isEmpty()){
				listHeader.add(weekdays[k]);
				listChild.put(listHeader.get(j++), dagar.get(k));
			}
		}
		
		if (j == 0){
			List<String> nothingFound = new ArrayList<String>();
			listHeader.add("Enginn tími fannst");
			nothingFound.add("Þú hefur ekki bætt við neinum tímum$id-1");
			infoChild.put("id-1", "Ýttu á tímana í Almennu stundatöflunni til að bæta við hérna.");
			listChild.put(listHeader.get(0), nothingFound);
		}
		
		return new StundatofluTimi(listHeader, listChild,infoChild);
	}
	
	/**
	 * Eydir voldum notendatima ur stundatoflu notendans. Inntok eru allar eindir frumlykils(primary key)
	 * 
	 * @param uid, audkenni notendans
	 * @param htid, audkenni hoptima
	 * @param isEinka, hvort timinn se buinn til af notenda eda ekki
	 */
	public void deleteNotendatimi(int uid, String htid, boolean isEinka){
		String isEinkatimi = String.valueOf(isEinka);
		String userID = String.valueOf(uid);

		mSQLiteDatabase.delete(MySQLiteHelper.TABLE_NOTENDATIMAR, "uid = ? AND htid = ? AND isEinkatimi = ?",
													new String[] {userID,		htid,		isEinkatimi});
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
		
		HashMap<String, Integer> timiIndexMap = Global.timiDags;
		
		List<List<String>> timiDags = new ArrayList<List<String>>();
		for (int i = 0; i < 4; i++)
			timiDags.add(i, new ArrayList<String>());
		
		Cursor cursor = mSQLiteDatabase.query(MySQLiteHelper.TABLE_HOPTIMAR, hoptimarAllColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//viljum ekki fa tofluheaderinn
		   if(cursor.getLong(0) == 0) cursor.moveToNext();
			
			Hoptimar hoptimi = cursorToHoptimar(cursor);
			if(hoptimi == null) {
				cursor.moveToNext();
				continue; 
			}
			
			int index = timiIndexMap.get(hoptimi.getTimi());
			timiDags.get(index).add(hoptimi.getNafn()+"$id"+hoptimi.getmId());	
			infoChild.put("id"+hoptimi.getmId(), hoptimi.toString());
				
			cursor.moveToNext();	
		}
		cursor.close();
		
		String[] timiDagsArray = Global.timiDagsArray;
		
		int j = 0;
		for (int k = 0; k < 4; k++){
			if (!timiDags.get(k).isEmpty()){
				listHeader.add(timiDagsArray[k]);
				listChild.put(listHeader.get(j++), timiDags.get(k));
			}
		}
		
		if (noHoptimar(timiDags.get(0),timiDags.get(1),timiDags.get(2),timiDags.get(3))){
			List<String> nothingFound = new ArrayList<String>();
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
			if(c.moveToFirst())	
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
			hoptimi.setDagur(cursor.getString(8));
			hoptimi.setTimi(cursor.getString(7));
			hoptimi.setLokad(cursor.getString(9));
			return hoptimi;
		}	
		return null;
	}
	
	/**
	 * Skilar nyjum minum tima a lesanlegra formi
	 * @param cursor
	 * @return timi sem notandi hefur valid ser
	 */
	private Hoptimar cursorToMinnTimi(Cursor cursor){
		return new Hoptimar(cursor.getLong(1), cursor.getString(2), cursor.getString(3), 
				cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), 
				cursor.getString(8), cursor.getString(9), cursor.getString(11));
	}

}
