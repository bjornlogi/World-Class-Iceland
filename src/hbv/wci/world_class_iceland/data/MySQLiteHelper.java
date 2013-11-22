package hbv.wci.world_class_iceland.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Gerir okkur kleyft ad gera SQLite skipanir i Android forritinu.
 * 
 * @author Maria og Sonja
 * @see SQLiteOpenHelper
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_HOPTIMAR = "hoptimar";
	public static final String COLUMN_ID = "_id";
	public static final String NAFN = "nafn";
	public static final String STOD = "stod";
	public static final String SALUR = "salur";
	public static final String TJALFARI = "tjalfari";
	public static final String TEGUND = "tegund";
	public static final String KLUKKAN = "klukkan";
	public static final String TIMI = "timi";
	public static final String DAGUR = "dagur";
	public static final String LOKAD = "lokad";
	
	public static final String TABLE_NOTENDUR = "notendur";
	public static final String NETFANG = "netfang";
	public static final String LYKILORD = "lykilord";
	public static final String KENNITALA = "kennitala";
	public static final String STADFEST = "stadfest";
	public static final String KORT = "kort";
	
	public static final String TABLE_NOTENDATIMAR = "notendatimar";
	public static final String USERID = "uid";
	public static final String HOPTIMIID = "htid";
	
	
	private static final String DATABASE_NAME = "worldclass.db";
	private static final int DATABASE_VERSION = 64;
	
	/**
	 * Tengir gagnagrunninn vid forritid
	 * 
	 * @param context
	 * @see Context
	 */
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/**
	 * Byr til toflu inni i db
	 * 
	 * @param db gagnagrunnur
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db, TABLE_HOPTIMAR);
		createTable(db, TABLE_NOTENDUR);
		createTable(db, TABLE_NOTENDATIMAR);
	}
	
	private void createTable(SQLiteDatabase db, String name) {
		// Database creation sql statement
		String CREATE_TABLE;
		if (name == TABLE_HOPTIMAR) {
			CREATE_TABLE = "create table " + name + "( " 
					+ COLUMN_ID		+  	" integer primary key autoincrement, "
					+ NAFN        	+	" TEXT, "
					+ STOD          +	" TEXT, "
		            + SALUR         +	" TEXT, "
		            + TJALFARI      +	" TEXT, "
		            + TEGUND        +	" TEXT, "
		            + KLUKKAN       +	" TEXT, "
		            + TIMI          +	" TEXT, "
		            + DAGUR         +	" TEXT, "
		            + LOKAD		    +	" TEXT ) ";
			db.execSQL(CREATE_TABLE);
		} else if (name == TABLE_NOTENDUR) {
			CREATE_TABLE = "create table if not exists " + name + "( " 
					+ COLUMN_ID	+	" integer primary key autoincrement, "
					+ NETFANG	+	" TEXT, "
					+ LYKILORD	+	" TEXT, "
		            + STADFEST	+	" TEXT, "
		            + KENNITALA +	" TEXT, "
		            + KORT		+	" TEXT ) ";
			db.execSQL(CREATE_TABLE);
		} else if (name == TABLE_NOTENDATIMAR) {
			CREATE_TABLE = "create table if not exists " + name + "( "
					+ USERID + " integer,"
					+ HOPTIMIID + " integer," +
					" PRIMARY KEY("+USERID+", "+HOPTIMIID+") )";
			db.execSQL(CREATE_TABLE);
		}
		
		
	}
	
	/**
	 * Uppfaerir toflu i db
	 * 
	 * @param db 
	 * @param oldVersion
	 * @param newVersion
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_HOPTIMAR);
		onCreate(db);
	}
	
}
