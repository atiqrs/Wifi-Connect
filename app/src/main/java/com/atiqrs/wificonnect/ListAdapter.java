package com.atiqrs.wificonnect;

import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {


    Context context;
    LayoutInflater inflater;
    List<String> wifiList;

    public ListAdapter(Context context, ArrayList<String> wifiList) {

        this.context = context;
        this.wifiList = wifiList;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return wifiList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        View view = convertView;

        if (view == null){
            view = inflater.inflate(R.layout.list_item,null);
            holder = new Holder();

            holder.tvDetails = view.findViewById(R.id.wifiName);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.tvDetails.setText("SSID :: " + wifiList.get(position));
        Log.d("wifi", "getView: "+wifiList.get(position));

        return null;
    }

    class Holder{
        TextView tvDetails;
    }
}
