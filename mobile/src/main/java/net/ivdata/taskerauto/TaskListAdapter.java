package net.ivdata.taskerauto;

/**
 * Created by MystX on 21/10/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.resource;

public class TaskListAdapter extends ArrayAdapter<TaskListItem>{
        ArrayList<TaskListItem> modelItems = null;
        Context context;
        View.OnClickListener listener;
    public TaskListAdapter(Context context, ArrayList<TaskListItem> resource, View.OnClickListener listener) {
        super(context,R.layout.listview_row,resource);
        this.context = context;
        this.modelItems = resource;
        this.listener = listener;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.listview_row, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.textView1);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
        cb.setOnClickListener(listener);
        name.setText(modelItems.get(position).getName());
        if(modelItems.get(position).getValue() == 1)
            cb.setChecked(true);
        else
            cb.setChecked(false);
        return convertView;
    }
}
