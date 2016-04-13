package brain.baseadapter;
import java.util.ArrayList;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

// что такое BaseAdapter http://startandroid.ru/ru/uroki/vse-uroki-spiskom/99-urok-47-obzor-adapterov
public class ItemAdapter extends BaseAdapter {
    //что такое контекст http://developer.alexanderklimov.ru/android/theory/context.php
    Context ctx;
    //private item;
    //что такое LayoutInflater http://startandroid.ru/ru/uroki/vse-uroki-spiskom/80-urok-40-layoutinflater-uchimsja-ispolzovat
    LayoutInflater lInflater;
    ArrayList<Item> objects;
    ItemDAO itemDao ;
    DBHelper dbHelper;

    ItemAdapter(Context context, ArrayList<Item> items, DBHelper dbHelper) {
        ctx = context;
        objects = items;
        lInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dbHelper = dbHelper;
        itemDao = new ItemDAO(dbHelper);

        //CompoundButton CB = new CompoundButton.OnCheckedChangeListener();

    }



    // кол-во элементов  // обязательно для реализации getView, getItemId, getItem, getCount
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
       //itemDao = new ItemDAO(android.content.Context);
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        final Item p = getTaskListItem(position);
        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
        cbBuy.setChecked(p.isDone());
        cbBuy.setTag(p);
        cbBuy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {//Как это сделать чтоб new создался один раз   OnCheckedChangeListener - это интерфейс, который надо реализовать
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Item item = (Item) buttonView.getTag();
                item.setDone(isChecked);//Item
                itemDao.merge(item);
                //notifyDataSetInvalidated();
            }
        });
        //Как это сделать чтоб new создался один раз   OnCheckedChangeListener - это интерфейс, который надо реализовать

        final EditText etName = (EditText) view.findViewById(R.id.tvDescr);
        etName.setText(p.getText());
        //Ананимный класс
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    textIsEmpty(etName, p);
                }
            }
        });

        etName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66 )
                {
                    ListView lv = getParentListView(v);
                    int totalCount = lv.getCount();
                    if (position < totalCount - 1)
                    {
                        final View viewById = getViewByPosition(position + 1, lv)
                                .findViewById(R.id.tvDescr);
                        viewById.requestFocus();//etName.getText().length()
                        //viewById.requestFo
                        textIsEmpty(etName, p);

                    }
                    else
                    {
                        //  TODO Создать новый + выбрать его
                    }

                    return true;
                }
                return false;
            }
            public View getViewByPosition(int pos, ListView listView) {
                final int firstListItemPosition = listView.getFirstVisiblePosition();
                final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

                if (pos < firstListItemPosition || pos > lastListItemPosition ) {
                    return listView.getAdapter().getView(pos, null, listView);
                } else {
                    final int childIndex = pos - firstListItemPosition;
                    return listView.getChildAt(childIndex);
                }
            }
            private ListView getParentListView(View v) {
                ViewParent parent = v.getParent();
                while (parent != null)
                {
                    if (parent instanceof ListView)
                    {
                        return (ListView) parent;
                    }
                    parent = parent.getParent();
                }
                return null;
            }
        });
        return view;
    }

    // товар по позиции
    Item getTaskListItem(int position) {
        return ((Item)getItem(position));
    }
    public void textIsEmpty(EditText etName, Item p ){
        String text = etName.getText().toString().trim();
        if(text.isEmpty()){
            etName.setText(p.getText());
        }
        else{
            p.setText(text);
            itemDao.merge(p);
        }

    }
}