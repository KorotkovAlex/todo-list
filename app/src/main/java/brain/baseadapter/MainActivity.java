package brain.baseadapter;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;



public class MainActivity extends Activity {
    ArrayList<Item> items = new ArrayList<Item>();
    ItemAdapter itemAdapter;
    EditText editText;
    EditText itemCreator;
    ListView lvMain;
    Context ctx;
    DBHelper dbHelper;
    ItemDAO itemDAO;
    SwipeDetector swipeDetector = new SwipeDetector();
    AdapterView.OnItemClickListener listener;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        itemDAO = new ItemDAO(dbHelper);
        editText = (EditText) findViewById(R.id.editText);
        itemCreator = (EditText) findViewById(R.id.itemCreator);
        itemAdapter = new ItemAdapter(this, items, dbHelper);
        // настраиваем список
        lvMain = (ListView) findViewById(R.id.lvMain);
        //lvMain.setOnTouchListener();
        lvMain.setOnTouchListener(swipeDetector);

        lvMain.setOnItemClickListener(listener);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                if (swipeDetector.swipeDetected()) {
                    if (swipeDetector.getAction() == SwipeDetector.Action.RL) {

                    } else {

                    }
                }
            }


        };
        lvMain.setAdapter(itemAdapter);
        itemCreator.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66) {
                    String text = itemCreator.getText().toString().trim();
                    if (text.isEmpty()) {
                        return true;
                    }
                    Item item = new Item();
                    item.setText(text);
                    item.setDone(false);
                    item.setTopicId(1);
                    itemDAO.persist(item);
                    items.add(item);
                    itemCreator.setText("");
                    itemAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
        //
        items.addAll(itemDAO.findAll());
        itemAdapter.notifyDataSetChanged();
    }
}