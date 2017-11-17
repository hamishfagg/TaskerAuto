package net.ivdata.taskerauto;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<TaskListItem> listItems=new ArrayList<TaskListItem>();
    TaskListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("enabledTasks", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        adapter = new TaskListAdapter(this, listItems, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = ((TextView)((View)v.getParent()).findViewById(R.id.textView1)).getText().toString();
                if (((CheckBox)v).isChecked())
                    editor.putBoolean(taskName, true);
                else
                    editor.remove(taskName);
                editor.commit();
            }
        });
        /*adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);*/
        ((ListView)findViewById(R.id.list)).setAdapter(adapter);
        /*((ListView)findViewById(R.id.list)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //((TaskListItem)parent.getItemAtPosition(position)).toggle();
                CheckBox c = (CheckBox)view.findViewById(R.id.checkBox1);
                c.toggle();
                adapter.notifyDataSetChanged();
            }
        });*/

        Cursor c = getContentResolver().query( Uri.parse( "content://net.dinglisch.android.tasker/tasks" ), null, null, null, null );

        if ( c != null ) {
            int nameCol = c.getColumnIndex( "name" );

            while ( c.moveToNext() ) {
                if (sharedPreferences.contains(c.getString(nameCol)))
                    listItems.add(new TaskListItem(c.getString(nameCol), 1));
                else
                    listItems.add(new TaskListItem(c.getString(nameCol), 0));
            }
            c.close();
            adapter.notifyDataSetChanged();
        }
    }
}
