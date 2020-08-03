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
 * Created by Abdul latheef on 03-12-2017.
 */

public class IncandescentFrag extends android.support.v4.app.Fragment {
    Button Connection,zero,twenty5,fifty,seventy5,hundred;
    TextView info;
    BluetoothAdapter BA2;
    String Address;
    boolean isBtconnected=false;
    boolean flagConnected=false;
    boolean fzero,f25,f50,f75,f100;
    boolean pro=false;

    IntentFilter filter;

    BluetoothDevice bluetoothDevice;
    BluetoothSocket bluetoothSocket;

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    ProgressDialog progress;

    BroadcastReceiver receiver=new BroadcastReceiver() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @SuppressLint("SetTextI18n")
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
                    info.setTextColor(getResources().getColor(R.color.colorPrimary));
                    info.setTextColor(getResources().getColor(R.color.colorPrimary));
                    Connection.setEnabled(true);
                    Connection.setAlpha(1f);
                    Connection.setBackgroundResource(R.drawable.connect_button_background);
                    Connection.setText("Connect");
                }

            }if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
                if(!BA2.isEnabled()){
                    pauseAllProgress();
                    Connection.setCompoundDrawables(Disable_d,null,null,null);
                    Connection.setText("Enable Bluetooth");
                    infoSet("Bluetooth Disabled");


                }else {
                    Connection.setCompoundDrawables(Enable_d,null,null,null);
                    Connection.setText("Connect");
                    infoSet("Bluetooth Enabled");
                }

            }
            if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
                if(bl.getName().equals("HC-06")||bl.getName().equals("HC-05")||bl.getName().equals("HC-07")||bl.getName().equals("SPP-CA")) {
                    flagConnected = true;
                    Connection.setCompoundDrawables(Conect_d, null, null, null);
                    Connection.setText("Disconnect "+bl.getName());
                    infoSet("Connected to " + bl.getName());
                }
            }
        }


    };
    CommunicationIn communicationIn=new CommunicationIn() {
        @Override
        public void transferInString(String s) {

        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            communicationIn=(CommunicationIn)getActivity();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.incandesent_layout,container,false);
        BA2=BluetoothAdapter.getDefaultAdapter();
        communicationIn.transferInString("Incandescent");

        filter=new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

        Connection=view.findViewById(R.id.connect);

        if(!BA2.isEnabled())
        {
         Connection.setText("Enable Bluetooth");
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
        zero=view.findViewById(R.id.zero);
        twenty5=view.findViewById(R.id.twentyfive);
        fifty=view.findViewById(R.id.fifty);
        seventy5=view.findViewById(R.id.sevetyfive);
        hundred=view.findViewById(R.id.hundred);
        zero.setBackgroundColor(Color.argb(255,224,224,224));
        fzero=false;
        twenty5.setBackgroundColor(Color.argb(255,224,224,224));
        f25=false;
        fifty.setBackgroundColor(Color.argb(255,224,224,224));
        f50=false;
        seventy5.setBackgroundColor(Color.argb(255,224,224,224));
        f75=false;
        hundred.setBackgroundColor(Color.argb(255,224,224,224));
        f100=false;
        info=view.findViewById(R.id.infoin);
        info.setText("");
        try {
            Bundle bundle=getArguments();
            assert bundle != null;
            pro=bundle.getBoolean("pro",true);
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
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if(BA2.isEnabled()) {


                    if (isBtconnected && bluetoothSocket != null) {
                        try {
                            bluetoothSocket.close();
                            isBtconnected = false;
                            Connection.setAlpha(0.7f);
                            Connection.setEnabled(false);
                            // Connection.setText("Connect to Bluetooth Device");
                            messageShort("Disconnecting...");
                            infoSet("Disconnecting...");
                            info.setTextColor(Color.RED);
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
                        bundle.putString("key_fragment", "Incandescent");
                       // Fragment fragment = new ConnectionFragment();
                        Fragment fragment=new ConnectionFragment();
                        fragment.setArguments(bundle);
                       // FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        assert getFragmentManager() != null;
                        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
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

    @SuppressLint("StaticFieldLeak")
    private class ConnectBT extends AsyncTask<Void,Void,Void> {
        private boolean connectSucces = true;


        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), "connecting....", "Please Wait!!!");

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                bluetoothDevice = BA2.getRemoteDevice(Address);
                bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(myUUID);
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                bluetoothSocket.connect();

            } catch (IOException e) {
                e.printStackTrace();
                connectSucces = false;
            }

            return null;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!connectSucces) {
                messageShort("Try again,  can't connect");
            } else {
                isBtconnected = true;
                //Connection.setText("Disconnect Bluetooth Device");
                Connection.setBackgroundResource(R.drawable.connect_button_afterconnection);
               communicationIn.transferInString("Incandescent");
                Drawable[] drawables = Connection.getCompoundDrawables();
                Drawable left = drawables[0];
                Drawable  Conect_d;
                //Enable_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_black_24dp);
                Conect_d = Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ic_bluetooth_connected_black_24dp);
                //Disable_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_disabled_black_24dp);
                //Enable_d.setBounds(left.getBounds());
                Conect_d.setBounds(left.getBounds());
                //Disable_d.setBounds(left.getBounds());
                Connection.setCompoundDrawables(Conect_d, null, null, null);
                if (bluetoothSocket != null) {
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


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2){
            // messageLong(String.valueOf(resultCode));
            Log.i("resultcode",String.valueOf(requestCode));
            if (resultCode==-1){
                // messageShort("Bluetooth is Activated");
                Connection.setText("Connect");
            }else{
                //messageShort("You didn't  activate Bluetooth");
                Connection.setText("Enable Bluetooth");

            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    if(bluetoothSocket!=null){

                        bluetoothSocket.getOutputStream().write("300a".getBytes());
                        infoSet("This action will transmit String \"300a\"");
                        fzero=!fzero;
                        if(fzero){
                            zero.setBackgroundColor(Color.argb(255,255,170,0));

                        }else{
                            zero.setBackgroundColor(Color.argb(255,224,224,224));
                            twenty5.setBackgroundColor(Color.argb(255,224,224,224));
                            f25=false;
                            fifty.setBackgroundColor(Color.argb(255,224,224,224));
                            f50=false;
                            seventy5.setBackgroundColor(Color.argb(255,224,224,224));
                            f75=false;
                            hundred.setBackgroundColor(Color.argb(255,224,224,224));
                            f100=false;
                        }
                    }else {
                        messageLong("No Connection");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        twenty5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(bluetoothSocket!=null){


                        f25=!f25;
                        if(f25){
                            bluetoothSocket.getOutputStream().write("301a".getBytes());
                            infoSet("This action will transmit String \"301a\"");
                            twenty5.setBackgroundColor(Color.argb(255,255,170,0));
                            zero.setBackgroundColor(Color.argb(255,255,170,0));
                            fzero=true;

                        }else{
                            bluetoothSocket.getOutputStream().write("300a".getBytes());
                            infoSet("This action will transmit String \"300a\"");
                            twenty5.setBackgroundColor(Color.argb(255,224,224,224));
                            fifty.setBackgroundColor(Color.argb(255,224,224,224));
                            f50=false;
                            seventy5.setBackgroundColor(Color.argb(255,224,224,224));
                            f75=false;
                            hundred.setBackgroundColor(Color.argb(255,224,224,224));
                            f100=false;

                        }

                    }else {
                        messageLong("No Connection");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        fifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(bluetoothSocket!=null){


                        f50=!f50;
                        if(f50){
                            bluetoothSocket.getOutputStream().write("302a".getBytes());
                            infoSet("This action will transmit String \"302a\"");
                            fifty.setBackgroundColor(Color.argb(255,255,170,0));
                            twenty5.setBackgroundColor(Color.argb(255,255,170,0));
                            zero.setBackgroundColor(Color.argb(255,255,170,0));
                            fzero=true;
                            f25=true;

                        }else{
                            bluetoothSocket.getOutputStream().write("301a".getBytes());
                            infoSet("This action will transmit String \"301a\"");
                            fifty.setBackgroundColor(Color.argb(255,224,224,224));
                            seventy5.setBackgroundColor(Color.argb(255,224,224,224));
                            f75=false;
                            hundred.setBackgroundColor(Color.argb(255,224,224,224));
                            f100=false;

                        }
                    }else {
                        messageLong("No Connection");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        seventy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(bluetoothSocket!=null){


                        f75=!f75;
                        if(f75){
                            bluetoothSocket.getOutputStream().write("303a".getBytes());
                            infoSet("This action will transmit String \"303a\"");
                            seventy5.setBackgroundColor(Color.argb(255,255,170,0));
                            fifty.setBackgroundColor(Color.argb(255,255,170,0));
                            twenty5.setBackgroundColor(Color.argb(255,255,170,0));
                            zero.setBackgroundColor(Color.argb(255,255,170,0));
                            fzero=true;
                            f25=true;
                            f50=true;

                        }else{
                            bluetoothSocket.getOutputStream().write("302a".getBytes());
                            infoSet("This action will transmit String \"302a\"");
                            seventy5.setBackgroundColor(Color.argb(255,224,224,224));
                            hundred.setBackgroundColor(Color.argb(255,224,224,224));
                            f100=false;
                        }
                    }else {
                        messageLong("No Connection");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(bluetoothSocket!=null){

                        f100=!f100;
                        if(f100){
                            bluetoothSocket.getOutputStream().write("304a".getBytes());
                            infoSet("This action will transmit String \"304a\"");
                            hundred.setBackgroundColor(Color.argb(255,255,170,0));
                            seventy5.setBackgroundColor(Color.argb(255,255,170,0));
                            fifty.setBackgroundColor(Color.argb(255,255,170,0));
                            twenty5.setBackgroundColor(Color.argb(255,255,170,0));
                            zero.setBackgroundColor(Color.argb(255,255,170,0));
                            fzero=true;
                            f25=true;
                            f50=true;
                            f75=true;

                        }else{
                            bluetoothSocket.getOutputStream().write("303a".getBytes());
                            infoSet("This action will transmit String \"303a\"");
                            hundred.setBackgroundColor(Color.argb(255,224,224,224));

                        }
                    }else {
                        messageLong("No Connection");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getActivity()).registerReceiver(receiver,filter);
    }
    @SuppressLint("SetTextI18n")
    private void pauseAllProgress(){
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
            //Connection.setText("Connect to Bluetooth Device");
            zero.setBackgroundColor(Color.argb(255,224,224,224));
            fzero=false;
            twenty5.setBackgroundColor(Color.argb(255,224,224,224));
            f25=false;
            fifty.setBackgroundColor(Color.argb(255,224,224,224));
            f50=false;
            seventy5.setBackgroundColor(Color.argb(255,224,224,224));
            f75=false;
            hundred.setBackgroundColor(Color.argb(255,224,224,224));
            f100=false;


        }catch (Exception e){
            e.printStackTrace();
        }

    }
   private  void messageShort(String s){
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }
    private  void messageLong(String s){
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }

    @SuppressLint("SetTextI18n")
    private void infoSet(String s) {
        info.setText("Info:- "+s);
    }

    public interface CommunicationIn{
        void transferInString(String s);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        try {


            super.onDestroyView();
            //super.onDestroyView();

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
            LogDebugging(e.getMessage());
        }
    }

    private void LogDebugging(String message) {
        Log.i("DebuggIncandesent",message);
    }
}
