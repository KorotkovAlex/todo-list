package brain.baseadapter;

/**
 * Created by Саня on 06.04.2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Саня on 28.03.2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactDb";

    public static final String TABLE_NAME_ITEMS = "Items";

    public static final String KEY_ID = "_id";
    public static final String KEY_TOPIC_ID = "topic";
    public static final String KEY_TEXTEDITOR = "item";
    public static final String KEY_DONE = "done";

    public static final String TABLE_NAME_TOPICS = "Topics";
    public static final String KEY_ID_TOPIC = "_id";
    public static final String KEY_NAME_TOPICS = "name";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + TABLE_NAME_ITEMS + "(" + KEY_ID
                + " integer primary key not NULL," + KEY_TOPIC_ID + " integer not NULL," + KEY_TEXTEDITOR + " text," + KEY_DONE + " integer not NULL" + ")");
        db.execSQL("create table " + TABLE_NAME_TOPICS + "(" + KEY_ID_TOPIC
                + " integer primary key not NULL," + KEY_NAME_TOPICS + " text not NULL" + ")");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists " + TABLE_NAME_ITEMS);
        db.execSQL("drop table if exists " + TABLE_NAME_TOPICS);
        onCreate(db);
    }
}
