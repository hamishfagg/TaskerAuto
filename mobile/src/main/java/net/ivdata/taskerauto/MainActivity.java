package net.ivdata.taskerauto;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        ((ListView)findViewById(R.id.list)).setAdapter(adapter);

        Cursor c = getContentResolver().query( Uri.parse( "content://net.dinglisch.android.tasker/tasks" ), null, null, null, null );

        if ( c != null ) {
            int nameCol = c.getColumnIndex( "name" );
            int projNameCol = c.getColumnIndex( "project_name" );

            while ( c.moveToNext() ) {

                Log.d("LALALALALALALA", c.getString(projNameCol) + "/" + c.getString(nameCol));
                listItems.add(c.getString(nameCol));
            }
            c.close();
            adapter.notifyDataSetChanged();
        }
    }
}
