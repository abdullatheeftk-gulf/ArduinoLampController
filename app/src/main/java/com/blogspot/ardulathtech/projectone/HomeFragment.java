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
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


import java.util.Objects;
import java.util.UUID;



/**
 * Created by Abdul latheef on 14-11-2017.
 */

public class HomeFragment extends android.support.v4.app.Fragment {
    BluetoothAdapter BA;

    boolean pro=false;

    ConstraintLayout c1;
    String address=null;
    Button Connection;
    android.support.v4.app.Fragment fragment=null;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    ProgressDialog progress;
    boolean isBtconnected=false;
    //boolean blue_watcher;

    TextView textView,Brightness;
    boolean flagl1,flagl2,flagl3,flagAll;
    boolean flagConnection=false;


    BluetoothDevice bluetoothDevice;
    IntentFilter filter;
    Button l1,l2,l3,switch_on_all;
    SeekBar seekBar;
    BluetoothSocket bluetoothSocket;
   ///*
   Communication communication=new Communication() {
       @Override
       public void PassBluetoothSocket(String Vdress) {

       }
   };
   ///*
    BroadcastReceiver receiver=new BroadcastReceiver() {
       @RequiresApi(api = Build.VERSION_CODES.KITKAT)
       @SuppressLint("SetTextI18n")
       @Override
       public void onReceive(Context context, Intent intent) {
           String action=intent.getAction();
           Drawable[] drawables=Connection.getCompoundDrawables();
           Drawable left=drawables[0];
           Drawable Enable_d,Conect_d,Disable_d;
           Enable_d=Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ic_bluetooth_black_24dp);
           Conect_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_connected_black_24dp);
           Disable_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_disabled_black_24dp);
           Enable_d.setBounds(left.getBounds());
           Conect_d.setBounds(left.getBounds());
           Disable_d.setBounds(left.getBounds());

           BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
           if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
               Log.e("key",action);
               //int btState=intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR);
               if(BA.isEnabled()){
                   infoSet("Bluetooth is enabled");
                   Connection.setCompoundDrawables(Enable_d,null,null,null);
                   Connection.setText("Connect");
                  // messageShort("Bluetooth on");
               }else {
                  // messageShort("Bluetooth off");
                   pauseAllprogress();
                   infoSet("Bluetooth is disabled");
                   Connection.setCompoundDrawables(Disable_d,null,null,null);
                   Connection.setText("Enable Bluetooth");
               }
           }
           if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
               messageShort("connected "+device.getName());
               if(device.getName().equals("HC-06")||device.getName().equals("HC-05")||device.getName().equals("HC-07")||device.getName().equals("SPP-CA")) {

                   infoSet("Bluetooth Connected to " + device.getName());
                   Connection.setCompoundDrawables(Conect_d,null,null,null);

               }
           }if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
               messageShort("Disconnected " + device.getName());

                 if(device.getName().equals("HC-06")||device.getName().equals("HC-05")||device.getName().equals("HC-07")||device.getName().equals("SPP-CA")) {
                   // textView.setTextSize(getActivity().getResources().getDimension(R.dimen.info));
                     textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                     infoSet("Disconnected " + device.getName());
                     Connection.setCompoundDrawables(Enable_d,null,null,null);
                     Connection.setEnabled(true);
                     Connection.setAlpha(1f);
                     pauseAllprogress();
                 }
           }
       }
   };

 private void log(String s){
  Log.i("key",s);
}

   // @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view;

        log("h141");

    view = inflater.inflate(R.layout.home_fragment, container, false);

log("h144");

try {
    Connection = view.findViewById(R.id.Connection_button);

    BA = BluetoothAdapter.getDefaultAdapter();
    if (!BA.isEnabled()) {
        Connection.setText(getResources().getString(R.string.enable_bluetooth));
        Drawable[] drawables = Connection.getCompoundDrawables();
        Drawable left = drawables[0];
        Drawable Disable_d;
        //Enable_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_black_24dp);
        //Conect_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_connected_black_24dp);
        Disable_d = Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ic_bluetooth_disabled_black_24dp);
        //Enable_d.setBounds(left.getBounds());
        //Conect_d.setBounds(left.getBounds());
        Disable_d.setBounds(left.getBounds());
        Connection.setCompoundDrawables(Disable_d, null, null, null);
    }

    c1 = view.findViewById(R.id.c1);
    filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
    filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);




}catch (Exception e){
log(e.getMessage());
}

        try {
            Bundle bundle=getArguments();
            assert bundle != null;
            pro=bundle.getBoolean("pro");
            log("h192");
            address=bundle.getString("key_address");

            //Toast.makeText(getActivity(),address,Toast.LENGTH_SHORT).show();
            if(address!=null){
                try {

                    connectBT onnectBT = new connectBT();
                    onnectBT.execute();
                }catch (Exception e){
                    messageLong(e.getMessage());
                }
            }



        }catch (Exception e){
           // messageLong(e.getMessage());

        }
        Connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(BA.isEnabled()) {


                    if (!flagConnection) {
                        fragment = new ConnectionFragment();
                        Bundle bu=new Bundle();
                        bu.putBoolean("pro",pro);
                        bu.putString("key_fragment","home");
                        fragment.setArguments(bu);
                        fragmentTransition(fragment);
                    } else {
                        if (bluetoothSocket != null & isBtconnected) {
                            try {
                                bluetoothSocket.close();
                                seekBar.setProgress(0);
                                flagConnection = false;
                                isBtconnected = false;
                                bluetoothSocket = null;
                                flagl1 = false;
                                flagl2 = false;
                                flagl3 = false;
                                flagAll = false;
                                Connection.setEnabled(false);
                                Connection.setAlpha(0.7f);
                               // Connection.setText("Connect to Bluetooth Device");
                                messageShort("Disconnecting...");
                                infoSet("Disconnecting...");
                                textView.setTextColor(Color.RED);
                                //textView.setTextSize(18);
                                //Connection.setBackgroundResource(R.drawable.connect_button_background);
                                l1.setBackgroundResource(R.drawable.light_background);
                                l2.setBackgroundResource(R.drawable.light_background);
                                l3.setBackgroundResource(R.drawable.light_background);
                                switch_on_all.setTextColor(Color.argb(255, 255, 255, 255));
                                l3.setTextColor(Color.argb(255, 255, 255, 255));
                                l1.setTextColor(Color.argb(255, 255, 255, 255));
                                l2.setTextColor(Color.argb(255, 255, 255, 255));
                                switch_on_all.setBackgroundResource(R.drawable.light_background);
                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        }

                    }
                }else {
                    //messageLong("First Enable Bluetooth");
                    pauseAllprogress();
                    Intent intent2=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent2,2);
                }

            }
        });




        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        log("h281");
        textView = Objects.requireNonNull(getActivity()).findViewById(R.id.info);
        updatUI();
        try {
            log("h285");
            Brightness = getActivity().findViewById(R.id.bright);
            l1 = getActivity().findViewById(R.id.l1);
            l2 = getActivity().findViewById(R.id.l2);
            l3 = getActivity().findViewById(R.id.l3);
            seekBar = view.findViewById(R.id.seekBar);
            switch_on_all = getActivity().findViewById(R.id.switchAll);



            if (bluetoothSocket == null && !isBtconnected) {
                l1.setEnabled(false);
                l2.setEnabled(false);
                l3.setEnabled(false);
                seekBar.setEnabled(false);
                switch_on_all.setEnabled(false);

            } else {
                l1.setEnabled(true);
                l2.setEnabled(true);
                l3.setEnabled(true);
                switch_on_all.setEnabled(true);
                seekBar.setEnabled(true);

            }

            l1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ControlL1();
                }
            });
            l2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ControlL2();
                }
            });
            l3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Controll3();
                }
            });
            switch_on_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ControlAll();
                }
            });
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (bluetoothSocket != null && isBtconnected) {
                        try {
                            BrightnessSet(i);

                        } catch (Exception e) {
                            log("h343 "+e.getMessage());
                        }
                    } else {
                        seekBar.setProgress(0);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                    if (bluetoothSocket != null) {
                        try {
                            infoSet("Brightness Controller  transmit String from \"0a\" to \"255a\" ");
                            // bluetoothSocket.getOutputStream().write("Start".getBytes());
                            //bluetoothSocket.getOutputStream().flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (bluetoothSocket != null) {
                        try {

                            //bluetoothSocket.getOutputStream().write((byte)400);
                            bluetoothSocket.getOutputStream().write((String.valueOf(seekBar.getProgress()) + "a").getBytes());
                            // bluetoothSocket.getOutputStream().flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }catch (Exception e){
           // messageLong(e.getMessage());
        }



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPause() {
        super.onPause();
        try {

           log("h394");
           // Objects.requireNonNull(getActivity()).unregisterReceiver(receiver);
        }catch (Exception e){
         log(e.getMessage());
        }
    }

    private void updatUI() {
     try {
         if (pro) {
             log("h404");
             infoSet("Full version");
             communication.PassBluetoothSocket("HomeFragment");
         } else {
             infoSet("Free Version");

         }
     }catch (Exception e){
         log("h412"+e.getMessage());
     }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        try {
            Objects.requireNonNull(getActivity()).registerReceiver(receiver, filter);
            log("h421 "+"onStart");
        }catch (Exception e){
           log("h423 "+e.getMessage());
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==2){
           // messageLong(String.valueOf(resultCode));
            log("resultcode "+String.valueOf(requestCode));
            if (resultCode== -1){
               // messageShort("Bluetooth is Activated");
                Connection.setText(getResources().getString(R.string.connect));
            }else{
                //messageShort("You didn't  activate Bluetooth");
                Connection.setText(getResources().getString(R.string.enable_bluetooth));

            }
        }

    }

    ///*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            communication = (Communication) getActivity();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(bluetoothSocket!=null&&isBtconnected){
            try {
                bluetoothSocket.close();
               // messageLong("connection Disconnected going to destroy");
            } catch (IOException e) {
                e.printStackTrace();
                messageLong(e.getMessage());
            }
        }
        Objects.requireNonNull(getActivity()).unregisterReceiver(receiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }




    @SuppressLint("StaticFieldLeak")
    private class connectBT extends AsyncTask<Void,Void,Void> {
        private boolean connectSucces=true;

        @Override
        protected void onPreExecute() {
            progress=ProgressDialog.show(getActivity(),"connecting....","Please Wait!!!");

        }

        @Override
        protected Void doInBackground(Void...voids) {
            try{
            BA=BluetoothAdapter.getDefaultAdapter();
            bluetoothDevice=BA.getRemoteDevice(address);
            bluetoothSocket=bluetoothDevice.createRfcommSocketToServiceRecord(myUUID);
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
            bluetoothSocket.connect();

            } catch (IOException e) {
                //e.printStackTrace();
               // Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                connectSucces=false;
            }
            return null;
        }



       // @SuppressLint("SetTextI18n")
       @RequiresApi(api = Build.VERSION_CODES.KITKAT)
       @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(!connectSucces){
                messageShort("Try again,  can't connect");
            }else {
                l1.setAlpha(1f);
                l2.setAlpha(1f);
                l3.setAlpha(1f);
                switch_on_all.setAlpha(1f);
                seekBar.setAlpha(1f);

                isBtconnected=true;
                Connection.setText("Disconnect "+bluetoothDevice.getName());
                Connection.setBackgroundResource(R.drawable.connect_button_afterconnection);
                Drawable[] drawables=Connection.getCompoundDrawables();
                Drawable left=drawables[0];
                Drawable Conect_d;
                //Enable_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_black_24dp);
                Conect_d=Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ic_bluetooth_connected_black_24dp);
                //Disable_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_disabled_black_24dp);
                //Enable_d.setBounds(left.getBounds());
                Conect_d.setBounds(left.getBounds());
                //Disable_d.setBounds(left.getBounds());
                Connection.setCompoundDrawables(Conect_d, null, null, null);
                flagConnection=true;
                l1.setEnabled(true);
                l2.setEnabled(true);
                l3.setEnabled(true);
                switch_on_all.setEnabled(true);
                seekBar.setEnabled(true);
                if(bluetoothSocket!=null){
                    try {
                        bluetoothSocket.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ///*
               communication.PassBluetoothSocket("HomeFragment");
                ///*
            }
            progress.dismiss();
            log("connected559; "+bluetoothSocket.isConnected());

        }
    }

    private void fragmentTransition(android.support.v4.app.Fragment fragment) {
        assert getFragmentManager() != null;
        android.support.v4.app.FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FrameFragments,fragment);
        fragmentTransaction.commit();
    }

    private void messageShort(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }
    private void messageLong(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }

    private void ControlL1() {
        if (isBtconnected) {


            if ( bluetoothSocket != null) {
                if (flagl1) {
                    try {
                        bluetoothSocket.getOutputStream().write("402a".getBytes());//switch off l1
                        infoSet("L1  transmitted String = \"402a\""+"\n" +
                                "(Download sample Arduino program by using fab button)");
                        l1.setBackgroundResource(R.drawable.light_background);
                        l1.setTextColor(Color.argb(255, 255, 255, 255));
                        flagl1 = !flagl1;
                    } catch (Exception e) {
                        messageLong(e.getMessage());
                    }

                } else {
                    try {
                        bluetoothSocket.getOutputStream().write("401a".getBytes());//switch on l1
                        infoSet("L1  transmitted String = \"401a\""+"\n" +
                                "(Download sample Arduino program by using fab button)");
                        l1.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l1.setTextColor(Color.argb(255, 255, 119, 0));
                        flagl1 = !flagl1;
                    } catch (Exception e) {
                        messageLong(e.getMessage());

                    }

                }

                ConditionAllCheck();

            }else{
                messageLong("No Connection");
            }
        }else {
            pauseAllprogress();
            messageLong("No Connection");


        }
    }



    private void ControlL2() {
        if(isBtconnected) {


            if ( bluetoothSocket != null) {
                if (flagl2) {
                    try {
                        bluetoothSocket.getOutputStream().write("404a".getBytes());//switch off l2
                        infoSet("L2  transmitted String = \"404a\""+"\n" +
                                "(Download sample Arduino program by using fab button)");
                        l2.setBackgroundResource(R.drawable.light_background);
                        l2.setTextColor(Color.argb(255, 255, 255, 255));
                        flagl2 = !flagl2;
                    } catch (Exception e) {
                        messageLong(e.getMessage());

                    }

                } else {
                    try {
                        bluetoothSocket.getOutputStream().write("403a".getBytes());//switch on l2
                        infoSet("L2  transmitted String = \"403a\""+"\n" +
                                "(Download sample Arduino program by using fab button)");
                        l2.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l2.setTextColor(Color.argb(255, 255, 119, 0));
                        flagl2 = !flagl2;
                    } catch (Exception e) {
                        messageLong(e.getMessage());
                    }


                }

                ConditionAllCheck();
            }
        }else {

            pauseAllprogress();
            messageLong("No Connection");

        }
    }
    private void Controll3() {
        if(isBtconnected) {


            if ( bluetoothSocket != null) {
                if (flagl3) {
                    try {
                        bluetoothSocket.getOutputStream().write("406a".getBytes());//switch off l3
                        infoSet("L3 transmitted String = \"406a\""+"\n(Dow" +
                                "nload sample Arduino program by using fab button)");
                        l3.setBackgroundResource(R.drawable.light_background);
                        l3.setTextColor(Color.argb(255, 255, 255, 255));
                        flagl3 = !flagl3;
                    } catch (Exception e) {
                        messageLong(e.getMessage());
                    }

                } else {
                    try {
                        bluetoothSocket.getOutputStream().write("405a".getBytes());//switch on l3
                        infoSet("L3  transmitted String = \"405a\""+"\n" +
                                "(Download sample Arduino program by using fab button)");
                        l3.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l3.setTextColor(Color.argb(255, 255, 119, 0));
                        flagl3 = !flagl3;
                    } catch (Exception e) {
                        messageLong(e.getMessage());
                    }

                }

                ConditionAllCheck();
            }else{

                    messageLong("warning");
                    infoSet("Bluetooth is not Enabled");

            }
        }else {
            pauseAllprogress();
            messageLong("No Connection");
        }
    }
    //@SuppressLint("SetTextI18n")
    private void ControlAll() {
        if(isBtconnected) {


            if ( bluetoothSocket != null) {
                if (flagAll) {
                    try {
                        bluetoothSocket.getOutputStream().write("408a".getBytes());//switch off all
                        infoSet("This action transmitted String = \"408a\""+"\n" +
                                "(Download sample Arduino program by using fab button)");
                        switch_on_all.setText(getResources().getString(R.string.switch_on_all));
                        flagl1 = false;
                        flagl2 = false;
                        flagl3 = false;
                        flagAll = !flagAll;
                        l1.setBackgroundResource(R.drawable.light_background);
                        l2.setBackgroundResource(R.drawable.light_background);
                        l3.setBackgroundResource(R.drawable.light_background);
                        switch_on_all.setTextColor(Color.argb(255, 255, 255, 255));
                        l3.setTextColor(Color.argb(255, 255, 255, 255));
                        l1.setTextColor(Color.argb(255, 255, 255, 255));
                        l2.setTextColor(Color.argb(255, 255, 255, 255));
                        switch_on_all.setBackgroundResource(R.drawable.light_background);
                    } catch (Exception e) {
                        messageLong(e.getMessage());
                    }

                } else {
                    try {
                        bluetoothSocket.getOutputStream().write("407a".getBytes());//switch on all
                        infoSet("This action transmitted String = \"407a\""+"\n" +
                                "(Download sample Arduino program by using fab button)");
                        switch_on_all.setText(getResources().getString(R.string.switchoffall));
                        flagl1 = true;
                        flagl2 = true;
                        flagl3 = true;
                        flagAll = !flagAll;
                        switch_on_all.setTextColor(Color.argb(255, 255, 119, 0));
                        l1.setTextColor(Color.argb(255, 255, 119, 0));
                        l2.setTextColor(Color.argb(255, 255, 119, 0));
                        l3.setTextColor(Color.argb(255, 255, 119, 0));
                        l1.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l2.setBackgroundResource(R.drawable.ligt_afterpressed);
                        l3.setBackgroundResource(R.drawable.ligt_afterpressed);
                        switch_on_all.setBackgroundResource(R.drawable.ligt_afterpressed);
                    } catch (Exception e) {
                        messageLong(e.getMessage());
                    }

                }

            }else{

                    messageLong("warning");

            }
        }else {
            pauseAllprogress();
            messageLong("No Connection");
        }
    }
   // @SuppressLint("SetTextI18n")
    private  void ConditionAllCheck(){
        if(flagl1&&flagl2&&flagl3){
            flagAll=true;
            switch_on_all.setBackgroundResource(R.drawable.ligt_afterpressed);
            switch_on_all.setText(getResources().getString(R.string.switchoffall));
            switch_on_all.setTextColor(Color.argb(255,255,119,0));

        }else {
            flagAll=false;
            switch_on_all.setBackgroundResource(R.drawable.light_background);
            switch_on_all.setText(getResources().getString(R.string.switch_on_all));
            switch_on_all.setTextColor(Color.argb(255,255,255,255));

        }

    }

   // @SuppressLint("SetTextI18n")
    private void pauseAllprogress() {
        try {
            seekBar.setProgress(0);
            flagConnection = false;
            isBtconnected = false;
            bluetoothSocket = null;
            flagl1 = false;
            flagl2 = false;
            flagl3 = false;
            flagAll = false;
            Connection.setText(getResources().getString(R.string.connect));
            Connection.setBackgroundResource(R.drawable.connect_button_background);
            l1.setBackgroundResource(R.drawable.light_background);
            l2.setBackgroundResource(R.drawable.light_background);
            l3.setBackgroundResource(R.drawable.light_background);
            switch_on_all.setTextColor(Color.argb(255, 255, 255, 255));
            l3.setTextColor(Color.argb(255, 255, 255, 255));
            l1.setTextColor(Color.argb(255, 255, 255, 255));
            l2.setTextColor(Color.argb(255, 255, 255, 255));
            switch_on_all.setBackgroundResource(R.drawable.light_background);
        }catch (Exception e){
            messageLong(e.getMessage());
        }

    }

    public interface Communication{
        void PassBluetoothSocket(String Vdress);
    }


    @SuppressLint("SetTextI18n")
    private void infoSet(String s){
        textView.setText("Info:- "+s);
    }


    @SuppressLint("SetTextI18n")
    private  void BrightnessSet(int i){
        int value=(i*100)/255;
        Brightness.setText("Brightness : "+value+"%");


    }



}
