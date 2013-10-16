package hbv.wci.world_class_iceland;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * bla
 * 
 * @see SQLiteOpenHelper
 *
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_HOPTIMAR = "users";
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
	
	private static final String DATABASE_NAME = "worldclass.db";
	private static final int DATABASE_VERSION = 45;
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Database creation sql statement
		String CREATE_TABLE = "create table "+ TABLE_HOPTIMAR + "( " 
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
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_HOPTIMAR);
		onCreate(db);
		
	}
	
}