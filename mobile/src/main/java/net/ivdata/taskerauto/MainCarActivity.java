package net.ivdata.taskerauto;
/**
 * Created by MystX on 2/09/2017.
 */
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.android.apps.auto.sdk.*;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

public class MainCarActivity extends CarActivity {

    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        // Sharedprefs stores which tasks the user wants to display in-car
        SharedPreferences sharedPreferences = getSharedPreferences("enabledTasks", MODE_PRIVATE);

        adapter=new ArrayAdapter<String>(this,
                R.layout.carlistview_row,
                listItems);
        ((ListView)findViewById(R.id.list)).setAdapter(adapter);

        ((ListView)findViewById(R.id.list)).setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?>adapter,View v, int position, long something){
                String taskName = listItems.get(position);
                if ( TaskerIntent.testStatus( getApplicationContext() ).equals( TaskerIntent.Status.OK ) ) {
                    TaskerIntent i = new TaskerIntent( taskName );
                    sendBroadcast( i );
                }
            }
        });

        // Get Tasker tasks
        Cursor c = getContentResolver().query( Uri.parse( "content://net.dinglisch.android.tasker/tasks" ), null, null, null, null );

        if ( c != null ) {
            int nameCol = c.getColumnIndex( "name" );
            // Add each task to the adapter IF the user has checked it in the phone app.
            while ( c.moveToNext() )
                if (sharedPreferences.contains(c.getString(nameCol)))
                    listItems.add(c.getString(nameCol));
            c.close();

            adapter.notifyDataSetChanged();
        }
    }
}
