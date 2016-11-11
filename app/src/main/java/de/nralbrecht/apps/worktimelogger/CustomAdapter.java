package de.nralbrecht.apps.worktimelogger;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class CustomAdapter extends BaseAdapter {
    private ArrayList data;
    private static LayoutInflater inflater = null;

    public CustomAdapter(Activity a, ArrayList d) {
        inflater = (LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        data = d;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void replaceAll(ArrayList<WorkTime> newData) {
        data.clear();
        data.addAll(newData);
    }

    private class ViewHolder{
        TextView startValue;
        TextView endValue;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView == null){
            vi = inflater.inflate(R.layout.tabitem, null);

            holder = new ViewHolder();
            holder.startValue = (TextView) vi.findViewById(R.id.startValue);
            holder.endValue = (TextView) vi.findViewById(R.id.endValue);

            vi.setTag(holder);
        }
        else
            holder = (ViewHolder) vi.getTag();

        if(data.size() > 0)
        {
            WorkTime tempValues = (WorkTime) data.get( position );

            holder.startValue.setText( tempValues.getStart() );
            holder.endValue.setText( tempValues.getEnd() );
        }
        return vi;
    }
}
