package com.br.robot_app.activity;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.br.robot_app.R;
import com.br.robot_app.connect.Connector;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsable for all connection with the server side
 */
public class WifiConnection extends AppCompatActivity {

    private WifiManager wifiManager;
    private List<String> connections = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection);

        Connector conn = Connector.getConnector();
    }

    public void setWifiList(){
        List<ScanResult> wifiList = wifiManager.getScanResults();
        for(int i=0; i<wifiList.size(); i++)
            connections.add(wifiList.get(i).SSID);

        int listType = android.R.layout.simple_list_item_1;
        ListAdapter wifiListAdapter = new ArrayAdapter<String>(this,listType,connections);
        ListView wifiListView = (ListView)findViewById(R.id.wifi_list);
        wifiListView.setAdapter(wifiListAdapter);
    }
}