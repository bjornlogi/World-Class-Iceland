package hbv.wci.world_class_iceland;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

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
	
	public DataSource (Context context){
		mSQLiteHelper = new MySQLiteHelper(context);
	}
	
	public void open() throws SQLiteException {
		mSQLiteDatabase = mSQLiteHelper.getWritableDatabase();
	}
	
	public void close() {
		mSQLiteHelper.close();
	}
	
	public void addUser(String []hoptimi){
		ContentValues values = new ContentValues();
		System.out.println("addUser: " + hoptimi);
		
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
	
	public List<Users> getAllUsers(){
		
		List <Users> users = new ArrayList<Users>();
		
		Cursor cursor = mSQLiteDatabase.query(MySQLiteHelper.TABLE_HOPTIMAR,
				mAllColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			//viljum ekki fa tofluheaderinn
			if(cursor.getLong(0) != 0){
				Users user = cursorToUser(cursor);
				users.add(user);
				cursor.moveToNext();
			}		
		}
		cursor.close();
		System.out.println("getAllUsers" + users);
		return users;
	}

	private Users cursorToUser(Cursor cursor) {
		Users user = new Users(); 
		user.setmId(cursor.getLong(0));
		System.out.println("cursortoUser: " + cursor.getLong(0));
		user.setNafn(cursor.getString(1));
		System.out.println("cursortoUser: " + cursor.getString(1));
		user.setStod(cursor.getString(2));
		System.out.println("cursortoUser: " + cursor.getString(2));
		user.setSalur(cursor.getString(3));
		System.out.println("cursortoUser: " + cursor.getString(3));
		user.setTjalfari(cursor.getString(4));
		System.out.println("cursortoUser: " + cursor.getString(4));
		user.setTegund(cursor.getString(5));
		System.out.println("cursortoUser: " + cursor.getString(5));
		user.setKlukkan(cursor.getString(6));
		System.out.println("cursortoUser: " + cursor.getString(6));
		user.setTimi(cursor.getString(7));
		System.out.println("cursortoUser: " + cursor.getString(7));
		user.setDagur(cursor.getString(8));
		System.out.println("cursortoUser: " + cursor.getString(8));
		user.setLokad(cursor.getString(9));
		System.out.println("cursortoUser: " + cursor.getString(9));
		return user;
	}

}
