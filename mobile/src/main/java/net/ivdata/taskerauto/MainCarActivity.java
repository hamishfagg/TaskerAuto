package net.ivdata.taskerauto;
/**
 * Created by MystX on 2/09/2017.
 */
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
        setContentView(R.layout.activity_main);
        //startActivityForResult( TaskerIntent.getTaskSelectIntent(), 0 );


        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
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
