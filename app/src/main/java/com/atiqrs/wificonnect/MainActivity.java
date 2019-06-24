package com.atiqrs.wificonnect;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    WifiReceiver wifiReceiver;
    ListAdapter listAdapter;
    ListView wifiListView;
    List<ScanResult> mywifiList;
    ArrayList<String> arrayList = new ArrayList<>();
    Button Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiListView = findViewById(R.id.myListView);

        Search = findViewById(R.id.search);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanWifiList();
            }
        });
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "WiFi is disabled ... We need to enable it", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }

        wifiReceiver = new WifiReceiver();

        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);

        } else {
            wifiListView.setAdapter(listAdapter);
             scanWifiList();
        }

//        if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)) !=
//        PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
//        }
//        else {
//            scanWifiList();
//        }

    }

    private void scanWifiList() {
        wifiManager.startScan();
        Toast.makeText(this, "Scanning WiFi ...", Toast.LENGTH_SHORT).show();
        mywifiList = wifiManager.getScanResults();

        Log.d("wifi1", "scanWifiList: "+wifiManager.getScanResults());
//        Log.d("list", "man is: "+ mywifiList);
        setAdapter();
    }

    private void setAdapter() {

        listAdapter = new ListAdapter(this,arrayList);


    }

    class WifiReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            unregisterReceiver(this);

            for (ScanResult scanResult : mywifiList){
                arrayList.add(scanResult.SSID);
                listAdapter.notifyDataSetChanged();
                Toast.makeText(context, scanResult.SSID+"\n", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
