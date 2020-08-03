package com.blogspot.ardulathtech.projectone;

import android.annotation.SuppressLint;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Abdul latheef on 16-11-2017.
 */

public class LampControllFragment8 extends android.support.v4.app.Fragment {
    boolean pro;
    BluetoothAdapter BA2;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;


    Button l1,l2,l3,l4,l5,l6,l7,l8,lAll,Connection;
    TextView info2,lock;
    String Address;
    IntentFilter filter;

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    ProgressDialog progress;
    boolean isBtconnected=false;
    boolean flagConnected=false;
    boolean fl1,fl2,fl3,fl4,fl5,fl6,fl7,fl8,flall;
    BroadcastReceiver receiver=new BroadcastReceiver() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            Drawable[] drawables=Connection.getCompoundDrawables();
            Drawable left=drawables[0];
            Drawable Enable_d,Conect_d,Disable_d;
            Enable_d= Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ic_bluetooth_black_24dp);
            Conect_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_connected_black_24dp);
            Disable_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_disabled_black_24dp);
            Enable_d.setBounds(left.getBounds());
            Conect_d.setBounds(left.getBounds());
            Disable_d.setBounds(left.getBounds());
            BluetoothDevice bl=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
                if(bl.getName().equals("HC-06")||bl.getName().equals("HC-05")||bl.getName().equals("HC-07")||bl.getName().equals("SPP-CA")) {
                    pauseAllProgress();
                    messageLong("Disconnected " + bl.getName());
                    Connection.setCompoundDrawables(Enable_d, null, null, null);
                    infoSet("Disconnected " + bl.getName());
                    info2.setTextColor(getResources().getColor(R.color.colorPrimary));
                    Connection.setEnabled(true);
                    Connection.setBackgroundResource(R.drawable.connect_button_background);
                    Connection.setText(getResources().getString(R.string.connect));
                    Connection.setAlpha(1f);
                }

            }if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
                if(!BA2.isEnabled()){
                    pauseAllProgress();
                    Connection.setCompoundDrawables(Disable_d,null,null,null);
                    Connection.setText(getResources().getString(R.string.enable_bluetooth));
                    infoSet("Bluetooth Disabled");


                }else {
                    Connection.setCompoundDrawables(Enable_d,null,null,null);
                    Connection.setText(getResources().getString(R.string.connect));
                    infoSet("Bluetooth Enabled");
                }

            }
            if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
                if(bl.getName().equals("HC-06")||bl.getName().equals("HC-05")||bl.getName().equals("HC-07")||bl.getName().equals("SPP-CA")) {
                    flagConnected = true;
                    Connection.setCompoundDrawables(Conect_d, null, null, null);
                    infoSet("Connected to " + bl.getName());
                }
            }
        }
    };
    Communication8 communication8=new Communication8() {
        @Override
        public void transferString8(String s) {

        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2){
            // messageLong(String.valueOf(resultCode));
            Log.i("resultcode",String.valueOf(requestCode));
            if (resultCode==-1){
                // messageShort("Bluetooth is Activated");
                Connection.setText(getResources().getString(R.string.connect));
            }else{
                //messageShort("You didn't  activate Bluetooth");
                Connection.setText(getResources().getString(R.string.enable_bluetooth));

            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            communication8=(Communication8)getActivity();
        }catch (Exception e){
            Log.d("attach",e.getMessage());
        }

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.eightbutton_fragment_layout,container,false);
        BA2=BluetoothAdapter.getDefaultAdapter();
        filter=new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        info2=view.findViewById(R.id.info2);
        lock=view.findViewById(R.id.lock8);
        Connection=view.findViewById(R.id.conn_eightButton);
        if(!BA2.isEnabled())
        {
            Connection.setText(getResources().getString(R.string.enable_bluetooth));
            Drawable[] drawables=Connection.getCompoundDrawables();
            Drawable left=drawables[0];
            Drawable Disable_d;
            //Enable_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_black_24dp);
            //Conect_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_connected_black_24dp);
            Disable_d= Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ic_bluetooth_disabled_black_24dp);
            //Enable_d.setBounds(left.getBounds());
            //Conect_d.setBounds(left.getBounds());
            Disable_d.setBounds(left.getBounds());
            Connection.setCompoundDrawables(Disable_d, null, null, null);
        }
        l1=view.findViewById(R.id.el1);
        l2=view.findViewById(R.id.el2);
        l3=view.findViewById(R.id.el3);
        l4=view.findViewById(R.id.el4);
        l5=view.findViewById(R.id.el5);

        l6=view.findViewById(R.id.el6);
        l7=view.findViewById(R.id.el7);
        l8=view.findViewById(R.id.el8);
        lAll=view.findViewById(R.id.eAll);

        try {
            Bundle bundle=getArguments();
            assert bundle != null;
            pro=bundle.getBoolean("pro");
            Address= bundle.getString("key_address");
            if(Address!=null){
                if(BA2.isEnabled()) {
                    ConnectBT connectBT = new ConnectBT();
                    connectBT.execute();
                }
            }

        }catch (Exception e){
            messageLong("no Address");

        }
        Connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BA2.isEnabled()) {


                    if (isBtconnected && bluetoothSocket != null) {
                        try {
                            bluetoothSocket.close();
                            isBtconnected = false;

                            //Connection.setBackgroundResource(R.drawable.connect_button_background);
                           // Connection.setText(getResources().getString(R.string.connect));
                            Connection.setEnabled(false);
                            Connection.setAlpha(0.7f);
                            // Connection.setText("Connect to Bluetooth Device");
                            messageShort("Disconnecting...");
                            infoSet("Disconnecting...");

                            info2.setTextColor(Color.RED);
                            pauseAllProgress();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else {
                        if(isBtconnected){
                            messageShort("warning");
                           isBtconnected=false;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("pro",pro);
                        bundle.putString("key_fragment", "lamp");
                        Fragment fragment = new ConnectionFragment();
                        fragment.setArguments(bundle);

                        assert getFragmentManager() != null;
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.FrameFragments, fragment);
                        fragmentTransaction.commit();
                    }
                }else{
                    if(!BA2.isEnabled()) {
                        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(i, 2);
                    }
                    pauseAllProgress();
                }

            }
        });


        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controll_L1();
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controll_L2();
            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controll_L3();
            }
        });
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controll_L4();
            }
        });
        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controll_L5();
            }
        });
        l6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controll_L6();
            }
        });
        l7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controll_L7();
            }
        });
        l8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controll_L8();
            }
        });
        lAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controll_All();
            }
        });


        //Toast.makeText(getActivity(),String.valueOf(pro),Toast.LENGTH_LONG).show();
        updateUI();

    }

    private void updateUI() {
        if(pro){
            lock.setVisibility(View.GONE);
            l5.setAlpha(1f);
            l6.setAlpha(1f);
            l7.setAlpha(1f);
            l8.setAlpha(1f);
            lAll.setAlpha(1f);
            communication8.transferString8("8lamp");

            infoSet("Full version");
        }else{
            lock.setVisibility(View.VISIBLE);
            l5.setAlpha(0.22f);
            l6.setAlpha(0.22f);
            l7.setAlpha(0.22f);
            l8.setAlpha(0.22f);
            lAll.setAlpha(0.22f);

            infoSet("Free version");
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getActivity()).registerReceiver(receiver,filter);

    }



    @SuppressLint("StaticFieldLeak")
    private class ConnectBT extends AsyncTask<Void,Void,Void> {
        private boolean connectSucces = true;


        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), "connecting....", "Please Wait!!!");

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                bluetoothDevice=BA2.getRemoteDevice(Address);
                bluetoothSocket=bluetoothDevice.createInsecureRfcommSocketToServiceRecord(myUUID);
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                bluetoothSocket.connect();

            } catch (IOException e) {
                e.printStackTrace();
                connectSucces=false;
            }

            return null;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(!connectSucces){
                messageShort("Try again,  can't connect");
            }else {
                isBtconnected=true;
                Connection.setText("Disconnect "+bluetoothDevice.getName());
                Connection.setBackgroundResource(R.drawable.connect_button_afterconnection);
                communication8.transferString8("8lamp");
                Drawable[] drawables=Connection.getCompoundDrawables();
                Drawable left=drawables[0];
                Drawable Conect_d;
                //Enable_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_black_24dp);
                Conect_d= Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ic_bluetooth_connected_black_24dp);
                //Disable_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_disabled_black_24dp);
                //Enable_d.setBounds(left.getBounds());
                Conect_d.setBounds(left.getBounds());
                //Disable_d.setBounds(left.getBounds());
                Connection.setCompoundDrawables(Conect_d, null, null, null);
                if(bluetoothSocket!=null){
                    try {
                        bluetoothSocket.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            progress.dismiss();

        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {



            if (bluetoothSocket != null) {
                try {
                    bluetoothSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Objects.requireNonNull(getActivity()).unregisterReceiver(receiver);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void messageShort(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }

    private void messageLong(String address) {
        Toast.makeText(getActivity(),address,Toast.LENGTH_LONG).show();
    }
    private void controll_L1(){

            if (bluetoothSocket != null) {
                if (fl1) {
                    try {
                        bluetoothSocket.getOutputStream().write(String.valueOf("502a").getBytes());//l1 off
                        infoSet("L1  transmitted String = \"502a\"" + "\n" +
                                "(Download sample Arduino program by using fab button)");
                        fl1 = !fl1;
                        l1.setBackgroundResource(R.drawable.light_background);
                        l1.setTextColor(Color.argb(255, 255, 255, 255));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    try {
                        bluetoothSocket.getOutputStream().write(String.valueOf("501a").getBytes());//l1 on
                        infoSet("L1  transmitted String = \"501a\"" + "\n" +
                                "(Download sample Arduino program by using fab button)");
                        fl1 = !fl1;
                        l1.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l1.setTextColor(Color.argb(255, 255, 119, 0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }else{
                messageLong("No Connection");
            }


    }
    private void controll_L2(){
        if(bluetoothSocket!=null){
            if(fl2){
                try {
                    bluetoothSocket.getOutputStream().write(String.valueOf("504a").getBytes());//l2 off
                    infoSet("L2  transmitted String = \"504a\""+"\n" +
                            "(Download sample Arduino program by using fab button)");
                    fl2=!fl2;
                    l2.setBackgroundResource(R.drawable.light_background);
                    l2.setTextColor(Color.argb(255,255,255,255));
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }else{
                try {
                    bluetoothSocket.getOutputStream().write(String.valueOf("503a").getBytes());//l2 on
                    infoSet("L2  transmitted String = \"503a\""+"\n" +
                            "(Download sample Arduino program by using fab button)");
                    fl2=!fl2;
                    l2.setBackgroundResource(R.drawable.ligt_afterpressed);
                    l2.setTextColor(Color.argb(255, 255, 119, 0));
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }else{
            messageLong("No Connection");
        }

    }
    private void controll_L3(){
        if(bluetoothSocket!=null){
            if(fl3){
                try {
                    bluetoothSocket.getOutputStream().write(String.valueOf("506a").getBytes());//l3 off
                    infoSet("L3  transmitted String = \"506a\""+"\n" +
                            "(Download sample Arduino program by using fab button)");
                    fl3=!fl3;
                    l3.setBackgroundResource(R.drawable.light_background);
                    l3.setTextColor(Color.argb(255,255,255,255));
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }else{
                try {
                    bluetoothSocket.getOutputStream().write(String.valueOf("505a").getBytes());//l3 on
                    infoSet("L3  transmitted String = \"505a\""+"\n" +
                            "(Download sample Arduino program by using fab button)");
                    fl3=!fl3;
                    l3.setBackgroundResource(R.drawable.ligt_afterpressed);
                    l3.setTextColor(Color.argb(255, 255, 119, 0));
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }else {
            messageLong("No Connection");
        }

    }
    private void controll_L4(){
        if(bluetoothSocket!=null){
            if(fl4){
                try {
                    bluetoothSocket.getOutputStream().write(String.valueOf("508a").getBytes());//l4 off
                    infoSet("L4  transmitted String = \"508a\""+"\n" +
                            "(Download sample Arduino program by using fab button)");
                    fl4=!fl4;
                    l4.setBackgroundResource(R.drawable.light_background);
                    l4.setTextColor(Color.argb(255,255,255,255));
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }else{
                try {
                    bluetoothSocket.getOutputStream().write(String.valueOf("507a").getBytes());//l4 on
                    infoSet("L4  transmitted String = \"507a\""+"\n" +
                            "(Download sample Arduino program by using fab button)");
                    fl4=!fl4;
                    l4.setBackgroundResource(R.drawable.ligt_afterpressed);
                    l4.setTextColor(Color.argb(255, 255, 119, 0));
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }else{
            messageLong("No Connection");
        }

    }
    private void controll_L5(){
        if(pro) {
            if (bluetoothSocket != null) {
                if (fl5) {
                    try {
                        bluetoothSocket.getOutputStream().write(String.valueOf("510a").getBytes());//l5 off
                        infoSet("L5  transmitted String = \"510a\"" + "\n" +
                                "(Download sample Arduino program by using fab button)");
                        fl5 = !fl5;
                        l5.setBackgroundResource(R.drawable.light_background);
                        l5.setTextColor(Color.argb(255, 255, 255, 255));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    try {
                        bluetoothSocket.getOutputStream().write(String.valueOf("509a").getBytes());//l5 on
                        infoSet("L5  transmitted String = \"509a\"" + "\n" +
                                "(Download sample Arduino program by using fab button)");
                        fl5 = !fl5;
                        l5.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l5.setTextColor(Color.argb(255, 255, 119, 0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }else {
                messageLong("No Connection");
            }
        }else {
            messageLong("Unlock it by purchasing Full vesion");
        }

    }
    private void controll_L6(){
        if(pro) {
            if (bluetoothSocket != null) {
                if (fl6) {
                    try {
                        bluetoothSocket.getOutputStream().write(String.valueOf("512a").getBytes());//l6 off
                        infoSet("L6 transmitted String = \"512a\"" + "\n" +
                                "(Download sample Arduino program by using fab button)");
                        fl6 = !fl6;
                        l6.setBackgroundResource(R.drawable.light_background);
                        l6.setTextColor(Color.argb(255, 255, 255, 255));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    try {
                        bluetoothSocket.getOutputStream().write(String.valueOf("511a").getBytes());//l6 on
                        infoSet("L6  transmitted String = \"511a\"" + "\n" +
                                "(Download sample Arduino program by using fab button)");
                        fl6 = !fl6;
                        l6.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l6.setTextColor(Color.argb(255, 255, 119, 0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }else {
                messageLong("No Connection");
            }
        }else {
            messageLong("Unlock it by purchasing Full vesion");
        }

    }
    private void controll_L7(){
        if(pro) {
            if (bluetoothSocket != null) {
                if (fl7) {
                    try {
                        bluetoothSocket.getOutputStream().write(String.valueOf("514a").getBytes());//l7 off
                        infoSet("L7  transmitted String = \"514a\"" + "\n" +
                                "(Download sample Arduino program by using fab button)");
                        fl7 = !fl7;
                        l7.setBackgroundResource(R.drawable.light_background);
                        l7.setTextColor(Color.argb(255, 255, 255, 255));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    try {
                        bluetoothSocket.getOutputStream().write(String.valueOf("513a").getBytes());//l7 on
                        infoSet("L7  transmitted String = \"513a\"" + "\n" +
                                "(Download sample Arduino program by using fab button)");
                        fl7 = !fl7;
                        l7.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l7.setTextColor(Color.argb(255, 255, 119, 0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }else {
                messageLong("No Connection");
            }
        }else {
            messageLong("Unlock it by purchasing Full vesion");
        }

    }
    private void controll_L8(){
        if(pro) {
            if (bluetoothSocket != null) {
                if (fl8) {
                    try {
                        bluetoothSocket.getOutputStream().write(String.valueOf("516a").getBytes());//l8 off
                        infoSet("L8  transmitted String = \"516a\"" + "\n" +
                                "(Download sample Arduino program by using fab button)");
                        fl8 = !fl8;
                        l8.setBackgroundResource(R.drawable.light_background);
                        l8.setTextColor(Color.argb(255, 255, 255, 255));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    try {
                        bluetoothSocket.getOutputStream().write(String.valueOf("515a").getBytes());//l8 on
                        infoSet("L8  transmitted String = \"515a\"" + "\n" +
                                "(Download sample Arduino program by using fab button)");
                        fl8 = !fl8;
                        l8.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l8.setTextColor(Color.argb(255, 255, 119, 0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }else {
                messageLong("No Connection");
            }
        }else {
            messageLong("Unlock it by purchasing Full vesion");
        }

    }
    @SuppressLint("SetTextI18n")
    private  void controll_All(){
        if(pro) {
            if (bluetoothSocket != null) {
                if (flall) {
                    try {
                        bluetoothSocket.getOutputStream().write(String.valueOf("518a").getBytes());
                        infoSet("This action transmitted String = \"518a\"" + "\n" +
                                "(Download sample Arduino program by using fab button)");
                        flall = !flall;
                        fl1 = false;
                        fl2 = false;
                        fl3 = false;
                        fl4 = false;
                        fl5 = false;
                        fl6 = false;
                        fl7 = false;
                        fl8 = false;
                        l1.setBackgroundResource(R.drawable.light_background);
                        l2.setBackgroundResource(R.drawable.light_background);
                        l3.setBackgroundResource(R.drawable.light_background);
                        l4.setBackgroundResource(R.drawable.light_background);
                        l5.setBackgroundResource(R.drawable.light_background);
                        l6.setBackgroundResource(R.drawable.light_background);
                        l7.setBackgroundResource(R.drawable.light_background);
                        l8.setBackgroundResource(R.drawable.light_background);
                        lAll.setBackgroundResource(R.drawable.light_background);
                        l1.setTextColor(Color.argb(255, 255, 255, 255));
                        l2.setTextColor(Color.argb(255, 255, 255, 255));
                        l3.setTextColor(Color.argb(255, 255, 255, 255));
                        l4.setTextColor(Color.argb(255, 255, 255, 255));
                        l5.setTextColor(Color.argb(255, 255, 255, 255));
                        l6.setTextColor(Color.argb(255, 255, 255, 255));
                        l7.setTextColor(Color.argb(255, 255, 255, 255));
                        l8.setTextColor(Color.argb(255, 255, 255, 255));
                        lAll.setTextColor(Color.argb(255, 255, 255, 255));
                        lAll.setText("switch on all");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        bluetoothSocket.getOutputStream().write(String.valueOf("517a").getBytes());
                        infoSet("This action transmitted String = \"517a\"" + "\n" +
                                "(Download sample Arduino program by using fab button)");
                        flall = !flall;
                        fl1 = true;
                        fl2 = true;
                        fl3 = true;
                        fl4 = true;
                        fl5 = true;
                        fl6 = true;
                        fl7 = true;
                        fl8 = true;
                        l1.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l2.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l3.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l4.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l5.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l6.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l7.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l8.setBackgroundResource(R.drawable.ligt_afterpressed);
                        lAll.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l1.setTextColor(Color.argb(255, 255, 119, 0));
                        l2.setTextColor(Color.argb(255, 255, 119, 0));
                        l3.setTextColor(Color.argb(255, 255, 119, 0));
                        l4.setTextColor(Color.argb(255, 255, 119, 0));
                        l5.setTextColor(Color.argb(255, 255, 119, 0));
                        l6.setTextColor(Color.argb(255, 255, 119, 0));
                        l7.setTextColor(Color.argb(255, 255, 119, 0));
                        l8.setTextColor(Color.argb(255, 255, 119, 0));
                        lAll.setTextColor(Color.argb(255, 255, 119, 0));
                        lAll.setText("switch off all");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }else {
                messageLong("No Connection");
            }
        }else {
            messageLong("Unlock it by purchasing Full vesion");
        }

    }
    @SuppressLint("SetTextI18n")
    private void infoSet(String s){
        info2.setText("Info:- "+s);
    }
    public interface Communication8{
        void transferString8(String s);
    }

    private void pauseAllProgress() {
        try {


            if (bluetoothSocket != null) {
                try {
                    bluetoothSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                bluetoothSocket = null;
            }
            isBtconnected = false;
            flagConnected=false;
            //Connection.setBackgroundResource(R.drawable.connect_button_background);
            //Connection.setText(getResources().getString(R.string.connect));
            fl1 = false;
            fl2 = false;
            fl3 = false;
            fl4 = false;
            fl5 = false;
            fl6 = false;
            fl7 = false;
            fl8 = false;
            flall = false;
            l1.setBackgroundResource(R.drawable.light_background);
            l2.setBackgroundResource(R.drawable.light_background);
            l3.setBackgroundResource(R.drawable.light_background);
            l4.setBackgroundResource(R.drawable.light_background);
            l5.setBackgroundResource(R.drawable.light_background);
            l6.setBackgroundResource(R.drawable.light_background);
            l7.setBackgroundResource(R.drawable.light_background);
            l8.setBackgroundResource(R.drawable.light_background);
            lAll.setBackgroundResource(R.drawable.light_background);
            l1.setTextColor(Color.argb(255, 255, 255, 255));
            l2.setTextColor(Color.argb(255, 255, 255, 255));
            l3.setTextColor(Color.argb(255, 255, 255, 255));
            l4.setTextColor(Color.argb(255, 255, 255, 255));
            l5.setTextColor(Color.argb(255, 255, 255, 255));
            l6.setTextColor(Color.argb(255, 255, 255, 255));
            l7.setTextColor(Color.argb(255, 255, 255, 255));
            l8.setTextColor(Color.argb(255, 255, 255, 255));
            lAll.setTextColor(Color.argb(255,255,255,255));
            lAll.setText(getResources().getString(R.string.switch_on_all));

        }catch (Exception e){
            messageLong(e.getMessage());
        }


    }

}
