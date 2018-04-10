package cn.edu.gdmec.android.boxuegu.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by apple on 18/4/10.
 */

public class SQLiteHelper extends SQLiteOpenHelper{
    private static final int DB_VERSION = 1;
    public static String DB_NAME = "bxg.db";
    public static final String U_USERINFO = "userinfo";

    public SQLiteHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS" + U_USERINFO + "("
        + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
        + "userName VARCHAR"
        + "nickName VARCHAR"
        + "sex VARCHAR"
        + "signature VARCHAR"
        + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + U_USERINFO);
        onCreate(sqLiteDatabase);
    }
}
