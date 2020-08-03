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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.blogspot.ardulathtech.projectone.R;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Abdul latheef on 21-11-2017.
 */

public class RGBfragment extends android.support.v4.app.Fragment {
    BluetoothAdapter BA;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;
    String Address;

    IntentFilter filter;

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    ProgressDialog progress;
    boolean isBtconnected=false;
    boolean flagConnected=false;
    boolean pro=false;

    ImageView imageView;
    Button Connect_butt;
    SeekBar r,g,b;
    TextView red,green,blue,info,lock;
    short r_value=0,g_value=0,b_value=0;

    BroadcastReceiver receiver=new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            Drawable[] drawables=Connect_butt.getCompoundDrawables();
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
                    Connect_butt.setBackgroundResource(R.drawable.connect_button_background);
                    Connect_butt.setText("Connect");
                    messageShort("Disconnected " + bl.getName());
                    info.setTextColor(getResources().getColor(R.color.colorPrimary));
                    InfoSet("Disconnected " + bl.getName());
                    Connect_butt.setCompoundDrawables(Enable_d, null, null, null);
                    Connect_butt.setEnabled(true);
                    Connect_butt.setAlpha(1f);
                }

            }if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
                if(!BA.isEnabled()){
                    pauseAllProgress();
                    Connect_butt.setCompoundDrawables(Disable_d,null,null,null);
                    Connect_butt.setText("Enable Bluetooth");
                    InfoSet("Bluetooth Disabled");


                }else {
                    Connect_butt.setCompoundDrawables(Enable_d,null,null,null);
                    Connect_butt.setText("Connect");
                    InfoSet("Bluetooth Enabled");

                }

            }
            if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
                if(bl.getName().equals("HC-06")||bl.getName().equals("HC-05")||bl.getName().equals("HC-07")||bl.getName().equals("SPP-CA")) {
                    flagConnected = true;
                    Connect_butt.setCompoundDrawables(Conect_d, null, null, null);
                    InfoSet("Connected " + bl.getName());
                }
            }
        }
    };
    CommunicationRGB communicationRGB=new CommunicationRGB() {
        @Override
        public void TansferRGB(String s) {

        }
    };


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==2){
            // messageLong(String.valueOf(resultCode));
            Log.i("resultcode",String.valueOf(requestCode));
            if (resultCode==-1){
                // messageShort("Bluetooth is Activated");
                Connect_butt.setText("Connect");
            }else{
               // messageShort("You didn't  activate Bluetooth");
                Connect_butt.setText("Enable Bluetooth");
                InfoSet("Bluetooth is not Enabled");

            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            communicationRGB =(CommunicationRGB)getActivity();

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.rgb_layout,container,false);

        try {
            BA=BluetoothAdapter.getDefaultAdapter();
            info=view.findViewById(R.id.inforgb);
            lock=view.findViewById(R.id.lock);

            info.setText("");
            filter=new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
            filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

            Connect_butt=view.findViewById(R.id.conect_but);



            if(!BA.isEnabled())
            {
                Connect_butt.setText("Enable Bluetooth");
                Drawable[] drawables=Connect_butt.getCompoundDrawables();
                Drawable left=drawables[0];
                Drawable Disable_d;
                //Enable_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_black_24dp);
                //Conect_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_connected_black_24dp);
                Disable_d= Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ic_bluetooth_disabled_black_24dp);
                //Enable_d.setBounds(left.getBounds());
                //Conect_d.setBounds(left.getBounds());
                Disable_d.setBounds(left.getBounds());
                Connect_butt.setCompoundDrawables(Disable_d, null, null, null);
            }



            r = view.findViewById(R.id.red_seekbar);
            red = view.findViewById(R.id.red);
            g = view.findViewById(R.id.green_seekbar);
            green = view.findViewById(R.id.green);
            b = view.findViewById(R.id.blue_seekbar);
            blue = view.findViewById(R.id.blue);
            imageView = view.findViewById(R.id.rgb);
            green.setVisibility(View.GONE);
            red.setVisibility(View.GONE);
            blue.setVisibility(View.GONE);
            imageView.setBackgroundColor(Color.argb(255, r_value, g_value, b_value));

            try {
                Bundle bundle=getArguments();
                pro= Objects.requireNonNull(bundle).getBoolean("pro");
                Address= bundle.getString("key_address");
                if(Address!=null){
                    if(BA.isEnabled()) {
                        ConnectBT connectBT = new ConnectBT();
                        connectBT.execute();
                    }
                }

            }catch (Exception e){
                messageLong("no Address");

            }

            Connect_butt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(BA.isEnabled()) {


                        if (isBtconnected && bluetoothSocket != null) {
                            try {
                                bluetoothSocket.close();
                                isBtconnected = false;

                                Connect_butt.setEnabled(false);
                                Connect_butt.setAlpha(0.7f);
                                info.setTextColor(Color.RED);
                                InfoSet("Disconnecting....");
                                messageShort("Disconnecting....");
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
                            bundle.putString("key_fragment", "rgb");
                            Fragment fragment = new ConnectionFragment();
                            fragment.setArguments(bundle);
                            assert getFragmentManager() != null;
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.FrameFragments, fragment);
                            fragmentTransaction.commit();
                        }
                    }else{
                        if(!BA.isEnabled()) {
                            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(i, 2);
                        }
                        pauseAllProgress();
                    }
                }
            });

        }catch (Exception e){
           messageLong(e.getMessage());

        }
        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getActivity()).registerReceiver(receiver,filter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
        try {


            r.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    try {


                        r_value = (short) i;
                        imageView.setBackgroundColor(Color.argb(255, r_value, g_value, b_value));

                        red.setVisibility(View.VISIBLE);
                        red.setText("Red :- " + String.valueOf(i));

               /* try {
                    bluetoothSocket.getOutputStream().write((String.valueOf(i+1000)+"a").getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                    }catch (Exception e){
                        messageLong(e.getMessage());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {



                    try {
                        if(bluetoothSocket!=null) {
                            bluetoothSocket.getOutputStream().flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    try {
                        if(bluetoothSocket!=null) {
                            bluetoothSocket.getOutputStream().write((String.valueOf(seekBar.getProgress() + 1000) + "a").getBytes());
                            InfoSet("This Action will transmit String from \"1000a\" to \"1255a\" ");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            });
            g.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    g_value = (short) i;
                    imageView.setBackgroundColor(Color.argb(255, r_value, g_value, b_value));


                    green.setVisibility(View.VISIBLE);
                    green.setText("Green :- " + i);

              /*  try {
                    bluetoothSocket.getOutputStream().write((String.valueOf(i+1256)+"a").getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    try {
                        if(bluetoothSocket!=null) {
                            bluetoothSocket.getOutputStream().flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if(pro) {
                        try {
                            if (bluetoothSocket != null) {
                                bluetoothSocket.getOutputStream().write((String.valueOf(seekBar.getProgress() + 2000) + "a").getBytes());
                                InfoSet("This Action will transmit String from \"2000a\" to \"2255a\" ");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            b.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    b_value = (short) i;
                    imageView.setBackgroundColor(Color.argb(255, r_value, g_value, b_value));
                    blue.setVisibility(View.VISIBLE);
                    blue.setText("Blue :- " + String.valueOf(i));

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                    try {
                        if(bluetoothSocket!=null) {
                            bluetoothSocket.getOutputStream().flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if(pro) {
                        try {
                            if (bluetoothSocket != null) {
                                bluetoothSocket.getOutputStream().write((String.valueOf(seekBar.getProgress() + 3000) + "a").getBytes());
                                InfoSet("This Action will transmit String from \"3000a\" to \"3255a\" ");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }catch (Exception e){
            messageLong(e.getMessage());
        }
    }

    private void updateUI() {
        if(pro){
            lock.setVisibility(View.GONE);
        }else{
            lock.setVisibility(View.VISIBLE);
        }

        if(pro){
            InfoSet("Full version");
            g.setAlpha(1f);
            b.setAlpha(1f);
            communicationRGB.TansferRGB("RGB");

        }else{
            InfoSet("Free version");
            g.setAlpha(0.22f);
            b.setAlpha(0.22f);

        }
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

                bluetoothDevice=BA.getRemoteDevice(Address);
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
                Connect_butt.setText("Disconnect "+bluetoothDevice.getName());
                Connect_butt.setBackgroundResource(R.drawable.connect_button_afterconnection);
                Drawable[] drawables=Connect_butt.getCompoundDrawables();
                Drawable left=drawables[0];
                Drawable Conect_d;
                //Enable_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_black_24dp);
                Conect_d= Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ic_bluetooth_connected_black_24dp);
                //Disable_d=getActivity().getResources().getDrawable(R.drawable.ic_bluetooth_disabled_black_24dp);
                //Enable_d.setBounds(left.getBounds());
                Conect_d.setBounds(left.getBounds());
                //Disable_d.setBounds(left.getBounds());
                Connect_butt.setCompoundDrawables(Conect_d, null, null, null);
                communicationRGB.TansferRGB("RGB");
                r.setProgress(0);
                g.setProgress(0);
                b.setProgress(0);
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





    @SuppressLint("SetTextI18n")
    private void pauseAllProgress() {

        if (bluetoothSocket != null) {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            bluetoothSocket = null;
        }
        r.setProgress(0);
        g.setProgress(0);
        b.setProgress(0);
        isBtconnected = false;
        flagConnected=false;
        //Connect_butt.setBackgroundResource(R.drawable.connect_button_background);
        //Connect_butt.setText("Connect to Bluetooth Device");

    }
    private  void messageLong(String s){
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }
    private  void messageShort(String s){
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("SetTextI18n")
    private void InfoSet(String s){
        info.setText("Info:- "+s);
       // Toast.makeText(getActivity(), "Info:- "+s, Toast.LENGTH_SHORT).show();
    }
    public interface CommunicationRGB{
        void TansferRGB(String s);
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        try {
            super.onDestroyView();

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
}
