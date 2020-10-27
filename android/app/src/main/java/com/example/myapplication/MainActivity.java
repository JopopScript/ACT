package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkSpecifier;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static WebView mWebView;
    private boolean checkPermission;
    List<ScanResult> result = null;
    IntentFilter intentFilter = new IntentFilter();
    //WifiManager wifiManager = null;
    WifiInfo wi = null;
    public static EditText et_name, et_number, et_url;
    public static String URL = "";
    public static String name, number;
    public static boolean flag = false;
    public static String tempURL = "";
    checkThread mThread;
    Button btn_join;
    public static WifiConfiguration wifiConfig = new WifiConfiguration();
    public static WifiManager wifiManager;
    public static boolean flag2 = false;
    public static Context mContext;
    public static String wifiName;
    public static int flagCount = 0;
    public static int noflagCount = 0;
    public static boolean flag4 = false;

    @Override
    protected void onPause() {
        Log.e("kks", "onPauseStart");
//        Log.e("kks", "flag3inpause" + flag3);
//        MainActivity.flag3 = false;
        super.onPause();

//        flag2 =false;
        SharedPreferences sharedPreferences = getSharedPreferences("temperature",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", et_name.getText().toString());
        editor.putString("id", et_number.getText().toString());
        editor.commit();
        //flag = false;
    }

    @Override
    protected void onDestroy() {
        Log.e("kks", "onDestroyStart");
        super.onDestroy();

    }

    @Override
    protected void onRestart() {
        Log.e("kks", "onRestartStart");
        super.onRestart();
//        flag2 =false;
        //flag = false;
        SharedPreferences sf = getSharedPreferences("temperature",MODE_PRIVATE);
        et_name = (EditText)findViewById(R.id.et_name);
        et_number = (EditText)findViewById(R.id.et_number);
    }

    @Override
    protected void onStop() {
        Log.e("kks", "onStopStart");
//        Log.e("kks", "flag3instop" + flag3);
//        MainActivity.flag3 = false;
        super.onStop();
//        flag2 =false;
        SharedPreferences sharedPreferences = getSharedPreferences("temperature",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", et_name.getText().toString());
        editor.putString("id", et_number.getText().toString());
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.e("kks", "flag3increate" + flag3);
//        MainActivity.flag3 = false;
        Log.e("kks", "onCreateStart");
        flag2 =false;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wi = wifiManager.getConnectionInfo();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        BroadcastReceiver broadcastReceiver;

        btn_join = (Button)findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        boolean check = wifiManager.startScan();
        Log.e("kks", "check = " + check);
        SharedPreferences sf = getSharedPreferences("temperature",MODE_PRIVATE);
        et_name = (EditText)findViewById(R.id.et_name);
        et_number = (EditText)findViewById(R.id.et_number);
        et_url = (EditText)findViewById(R.id.et_url);

        et_name.setText(sf.getString("name", ""));
        et_number.setText(sf.getString("id", ""));

        Log.e("kks", "prethread");
        mThread = new checkThread();
        mThread.start();
        Log.e("kks", "nextthread");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                mContext = c;
                result = wifiManager.getScanResults();
                Log.e("kks", "intent=" + intent.getAction());
                Log.e("kks", "intent=" + intent.getExtras());

                if (MainActivity.flag4 == false && MainActivity.wifiName.contains("ctctest")) {
                    Log.e("kks", "load2inbc");

                    flagCount = 2;
                    flag4 = true;
                }

                for (int i = 0; i < result.size(); i++) {
                    Log.e("kks", "0000000");
                    Log.e("kks", "foundAP wifi : " + result.get(i).SSID);
                    Log.e("kks", "foundAP wifi : " + result.get(i).BSSID);
                    if (result.get(i).SSID.contains("ctctest") && flag2 == false) {
                        Log.e("kks", "et_name : " + MainActivity.et_name.getText().toString());
                        Log.e("kks", "et_number : " + MainActivity.et_number.getText().toString());
                        MainActivity.load2("http://35.221.83.148:8080/newAct/join_result.do?name=" + MainActivity.et_name.getText().toString() + "&school_id=" + MainActivity.et_number.getText().toString() +"&temperature=0");
                        flag2 = true;
                        Log.e("kks", "found");
//                        mWebView.loadUrl("https://www.naver.com/");
//                        load2("http://15.165.112.160:8080/newAct/join_result.do?name=임지수&school_id=01012345678&temperature=0");

//                        loadWebPage("https://www.naver.com/");
                    }

                }
            }
        };
        getApplicationContext().registerReceiver(broadcastReceiver, intentFilter);
        Log.e("kks", "111");

        if (wifiManager.isWifiEnabled()) {
//            Toast.makeText(getApplicationContext(), "wifi is enabled", 3000).show();
            Log.e("kks", "22222");
        } else {
            wifiManager.setWifiEnabled(true);
//            Toast.makeText(getApplicationContext(), "wifi is NOT enabled", 3000).show();
            Log.e("kks", "33333");
//            wifiManager.startScan();
            List<ScanResult> apList = wifiManager.getScanResults();
            for (int i = 0; i < apList.size(); i++) {
                Log.e("kks", "apList(i) : " + apList.get(i).SSID);
                Log.e("kks", "apList(i) : " + apList.get(i).BSSID);
                Log.e("kks", "apList(i) : " + apList.get(i).toString());
            }
        }

        Log.e("kks", "getBSSID = " + wi.getBSSID());
        Log.e("kks", "getSSID = " + wi.getSSID());
        Log.e("kks", "getMacAddress = " + wi.getMacAddress());
        Log.e("kks", "getBSSID = " + wi.getBSSID());
        Log.e("kks", "getIpAddress = " + wi.getIpAddress());
        Log.e("kks", "getRssi = " + wi.getRssi());


        WifiConfiguration wificonfig=new WifiConfiguration();
        //wifiManager.disconnect();
//        wifiManager.reconnect();

        WifiConfiguration conf = new WifiConfiguration();


//        wificonfig.SSID = String.format("\"%s\"", WIFI_NAME);
//        wificonfig.preSharedKey = String.format("\"%s\"", WIFI_PASSWORD);
//
//        int netId = wifiManager.addNetwork(wificonfig);
//        wifiManager.disconnect();
//        wifiManager.enableNetwork(netId, true);
//        wifiManager.reconnect();





        ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.INTERNET"}, 0);
        Log.e("kks", "222");
        mWebView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.e("kks", "loadloadload");
                return true;
            }
        });
        //mWebView.loadUrl("https://www.daum.net");
        //mWebView.loadUrl(this.URL);

    }

    public static void load2(final String url) {
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(url);
            }
        });
        Log.e("kks", "update URL : " + url);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("kks", "started!!!!!!!!!!!");
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("kks", "finsihed!!!!!!!!!!!");
            }
        });
    }

    public static boolean hasPermissions(Context context, String[] permissions)
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null)
        {
            for (String permission : permissions)
            {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static void updateWebPage(final String url) {
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(url);
            }
        });
        Log.e("kks", "update URL : " + url);
        flag = true;
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("kks", "started!!!!!!!!!!!");
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("kks", "finsihed!!!!!!!!!!!");
            }
        });
    }

    public static void connectWifi() {
        MainActivity.wifiConfig.SSID = String.format("\"%s\"", "ctctest");
        MainActivity.wifiConfig.preSharedKey = String.format("\"%s\"", "1234567890");

        int netId = MainActivity.wifiManager.addNetwork(wifiConfig);
        //MainActivity.wifiManager.disconnect();
        MainActivity.wifiManager.enableNetwork(netId, true);
        MainActivity.wifiManager.reconnect();

        //Log.e("kks", "connect");
        //MainActivity.wifiManager.reconnect();
    }

    public static void loadWebPage(final String url) {
        //mWebView.loadUrl(url);
        Log.e("kks", "loadweb : " + url);
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                Log.e("kks", "222223");
                mWebView.destroy();
                mWebView.reload();
                mWebView.onResume();
                mWebView.loadUrl(url);
                Log.e("kks", "222224");
                mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Log.e("kks", "in222224");
                        view.loadUrl(url);
                        Log.e("kks", "inn222224");
                        return true;
                    }
                });
//                mWebView.loadUrl(url);
                mWebView.onResume();

                Log.e("kks", "222225");
            }
        });
    }
}

class checkThread extends Thread {
    @Override
    public void run() {
        super.run();
        while(true) {

            String sSSID = "";
            String sBSSID = "";
            if (MainActivity.mContext != null) {
                WifiManager manager = (WifiManager) MainActivity.mContext.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = (WifiInfo) manager.getConnectionInfo();
                sSSID = wifiInfo.getSSID();
                sBSSID = wifiInfo.getBSSID();
                Log.e("kks", "sSSID=" + sSSID);
                Log.e("kks", "sSSID=" + sBSSID);
            }
            MainActivity.wifiName = sSSID;
            if (MainActivity.flagCount == 0 && sSSID.contains("ctctest")) {
                Log.e("kks", "doif");
                MainActivity.flagCount = 1;
//                MainActivity.load2("http://192.168.0.11:8080/newAct/join_result.do?name=임지수&school_id=01012345678&temperature=0");

            } else {
                Log.e("kks", "doelse");
//                MainActivity.connectWifi();
                MainActivity.noflagCount++;
            }
//            if (MainActivity.noflagCount == 10) {
//                MainActivity.flagCount = 0;
//            }



            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e("kks", "startScanWork");
            MainActivity.wifiManager.startScan();
            MainActivity.name = MainActivity.et_name.getText().toString();
            MainActivity.number = MainActivity.et_number.getText().toString();
            MainActivity.URL = MainActivity.et_url.getText().toString();
//            MainActivity.connectWifi();

//            if (MainActivity.flag == false && MainActivity.et_name != null && MainActivity.et_number != null && MainActivity.et_name.length() == 3 && MainActivity.et_number.length() > 4) {
//                //MainActivity.tempURL = "http://192.168.0.11:8081/act/join_result.do?name=" + MainActivity.et_name.getText().toString() + "&school_id=" + MainActivity.et_number.getText().toString() + "&temperature=0";
//                MainActivity.tempURL = "https://www.daum.net";
//                try {
//                    if (MainActivity.flag == false) {
//                        Log.e("kks", "############################");
//                        Log.e("kks", "load URL!!!!!!!!!!!!!!!!!!!");
//                        Log.e("kks", "############################");
//                        MainActivity.updateWebPage(MainActivity.tempURL);
//                        MainActivity.flag = true;
//                    }
//                    Log.e("kks", "total -> " + MainActivity.tempURL);
//                    Log.e("kks", "check -> " + MainActivity.name + MainActivity.number + MainActivity.URL);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
