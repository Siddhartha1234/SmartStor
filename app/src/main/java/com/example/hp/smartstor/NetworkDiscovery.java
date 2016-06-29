package com.example.hp.smartstor;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cz.msebera.android.httpclient.Header;

public class NetworkDiscovery extends BaseActivity implements NetworkCardAdapter.onFabClickedListener {
    RecyclerView recyclerView;
     LinearLayoutManager linearLayoutManager;
     NetworkCardAdapter cardAdapter;
    final static List<String> info=new List<String>() {
        @Override
        public void add(int location, String object) {

        }

        @Override
        public boolean add(String object) {
            return false;
        }

        @Override
        public boolean addAll(int location, Collection<? extends String> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends String> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean contains(Object object) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public String get(int location) {
            return null;
        }

        @Override
        public int indexOf(Object object) {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @NonNull
        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @Override
        public int lastIndexOf(Object object) {
            return 0;
        }

        @Override
        public ListIterator<String> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<String> listIterator(int location) {
            return null;
        }

        @Override
        public String remove(int location) {
            return null;
        }

        @Override
        public boolean remove(Object object) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public String set(int location, String object) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @NonNull
        @Override
        public List<String> subList(int start, int end) {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(T[] array) {
            return null;
        }
    };
    String TAG="ip";
    Context context;
    int i_max=255;
    static ProgressBar progressBar;
    static clientSearch client;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        recyclerView = (RecyclerView) findViewById(R.id.network_recycler);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        cardAdapter = new NetworkCardAdapter(getApplicationContext());
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        if(devices!=null)
        {
            for(int i=0;i<devices.size();i++)
            {
                cardAdapter.items.add(devices.get(i));

            }
            recyclerView.setAdapter(cardAdapter);
        }


        if(client ==null)
            getpeers();



    }



    public  static  void showinfo(Context context)
    {
        for(int j=0;j< info.size();j++)
        {
            Toast.makeText(context,info.get(j),Toast.LENGTH_LONG).show();
        }
    }
    public void setURL(String link)
    {
        rooturl="http://"+link+":3000/";
        url="http://"+link+":3000/upload/multipart";
    }



    public void getpeers() {

        String ip = getRootfromIP(getIPAddr());
        Log.i(TAG, ip);
        int i=1 ;

        //String URL = "http://" + ip + String.valueOf(i) + ":3000/upload/multipart";
        client = new clientSearch("http://" + ip);

        devinfo(client,i);


    }

    public void  devinfo(final clientSearch client, final int i)
    {

        client.get(String.valueOf(i)+":3000/get-info", null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                if(i>254)
                    return;
                float x=(float) i/i_max;
                int z=(int) (x *100);
                progressBar.setProgress(z);
                if(!askedToPause)
                    devinfo(client,i+1);
                else {
                    index =i+1;
                    client.client.cancelAllRequests(true);
                }


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    //Toast.makeText(getApplicationContext(),responseString,Toast.LENGTH_LONG).show();

                    float x=(float) i/i_max;
                    int z=(int) (x *100);
                    progressBar.setProgress(z);
                    JsonToData(responseString);
                    devinfo(client,i+1);



            }
        });
        return;
    }
    public void JsonToData(String response)
    {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject data = jsonArray.getJSONObject(i);
                String devicename = data.getString("devicename");
                String deviceip = data.getString("ip");
                rowItem test=new rowItem(devicename,deviceip);
                devices.add(test);
                cardAdapter.items.add(test);
                if(cardAdapter.getItemCount()==1)
                    recyclerView.setAdapter(cardAdapter);
                cardAdapter.notifyDataSetChanged();


            }

            } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getIPAddr() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e("WIFIIP", "Unable to get host address.");
            ipAddressString = null;
        }

        return ipAddressString;

    }
    public String  getRootfromIP(String ipaddr)
    {
        StringBuilder ip = new StringBuilder(ipaddr);
        int i;
        for(i=ip.length()-1;ip.charAt(i) != '.';i--)
        {
            ip.deleteCharAt(i);

        }
        return ip.toString();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_peers:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void searchpause(View v)
    {
        FloatingActionButton fab =(FloatingActionButton) v;
        if(!askedToPause) {
            askedToPause = true;

            fab.setImageResource(R.drawable.ic_play_arrow_white_24dp);

        }
        else
        {
            askedToPause=false;
            devinfo(client,index);
            fab.setImageResource(R.drawable.ic_pause_black_24dp);

        }
    }
    public void searchcancell(View v)
    {
        FloatingActionButton fab =(FloatingActionButton) v.findViewById(R.id.pausesearch);
        client.client.cancelAllRequests(true);
        progressBar.setProgress(0);
        index=0;
        fab.setImageResource(R.drawable.ic_play_arrow_white_24dp);

    }

}
