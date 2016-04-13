package brain.baseadapter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Саня on 28.03.2016.
 */
public class ItemDAO {
    private final DBHelper dbHelper;

    public ItemDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    public void persist(Item item){//Сохранят в базу данных insert
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_TOPIC_ID, item.getTopicId());
        contentValues.put(DBHelper.KEY_TEXTEDITOR, item.getText());
        contentValues.put(DBHelper.KEY_DONE, item.isDone());
        long newId = database.insert(DBHelper.TABLE_NAME_ITEMS, null, contentValues);
        item.setId(newId);
    }
    public void merge(Item item){//Сохранят в базу данных update
        //ArrayList<Item> list =new ArrayList<Item>();
        //list.add(item);
        //arrayname.set(1, "");
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_DONE, item.isDone());
        contentValues.put(DBHelper.KEY_TEXTEDITOR, item.getText());
        database.update(DBHelper.TABLE_NAME_ITEMS,contentValues,
                "_id = ?",
                new String[] {String.valueOf(item.getId())});
    }
    public ArrayList<Item> findAll(){//select
        //класс singlton
        ArrayList<Item> list =new ArrayList<Item>();
        //list.add(item);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //DBHelper dbHelpe = new BDHelper();
        Cursor cursor = database.query(DBHelper.TABLE_NAME_ITEMS,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int topicIndex = cursor.getColumnIndex(DBHelper.KEY_TOPIC_ID);
            int textIndex = cursor.getColumnIndex(DBHelper.KEY_TEXTEDITOR);
            int doneIndex = cursor.getColumnIndex(DBHelper.KEY_DONE);
            do{
                Item item = new Item();
                item.setId(cursor.getInt(idIndex));
                item.setText(cursor.getString(textIndex));
                item.setDone(cursor.getInt(doneIndex) != 0);
                item.setTopicId(cursor.getInt(topicIndex));
                list.add(item);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}