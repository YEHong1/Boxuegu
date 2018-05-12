package cn.edu.gdmec.android.boxuegu.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 3;
    public static String DB_Name = "bxg.db";
    public static final String U_USER_INFO = "userInfo";
    public static final String U_VIDEO_PLAY_LIST = "videoplaylist";

    public SQLiteHelper(Context context) {
        super(context, DB_Name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 当这个SQLiteOpenHelper的子类类被实例化时会创建指定名的数据库，在onCreate中创建个人信息表
         * **/
        db.execSQL("CREATE TABLE IF NOT EXISTS " + U_USER_INFO + "( "
                + "_id  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "userName VARCHAR, "
                + "nickName VARCHAR, "
                + "sex VARCHAR, "
                + "signature VARCHAR, "
                + "qq VARCHAR"
                + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + U_VIDEO_PLAY_LIST + "( "
                + "_id  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "userName VARCHAR, "
                + "chapterId INT, "
                + "videoId INT, "
                + "videoPath VARCHAR, "
                + "title VARCHAR,"
                + "secondTitle VARCHAR"
                + ")");
    }

    /**
     * 当数据库版本号增加才会调用此方法
     **/
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + U_USER_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + U_VIDEO_PLAY_LIST);
        onCreate(db);
    }
}
