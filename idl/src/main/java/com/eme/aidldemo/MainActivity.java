package com.eme.aidldemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="AIDL";

    @BindView(R.id.btn_01)
    Button btn01;
    @BindView(R.id.btn_02)
    Button btn02;
    @BindView(R.id.btn_03)
    Button btn03;
    private BookManager bookManager;

    private boolean isBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setAction("com.ymtz.aidl");
        intent.setPackage("com.eme.idlserver");
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
        super.onStop();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookManager = BookManager.Stub.asInterface(service);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @OnClick({R.id.btn_01, R.id.btn_02, R.id.btn_03})
    void click(View v) {
        switch (v.getId()) {
            case R.id.btn_01:
                Book book01 = new Book();
                book01.setName("in_from_client");
                book01.setPrice(1);
                try {
                    Book rbook01 = bookManager.addBookIn(book01);
                    Log.e(TAG,"CLIENT的btn_01接收到的book内容为:"+rbook01.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_02:
                Book book02 = new Book();
                book02.setName("out_from_client");
                book02.setPrice(2);
                try {
                    Book rbook02 = bookManager.addBookOut(book02);
                    Log.e(TAG,"CLIENT的btn_02接收到的book内容为:"+rbook02.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_03:
                Book book03 = new Book();
                book03.setName("in_out_from_client");
                book03.setPrice(3);
                try {
                    Book rbook03 = bookManager.addBookInOut(book03);
                    Log.e(TAG,"CLIENT的btn_03接收到的book内容为:"+rbook03.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
