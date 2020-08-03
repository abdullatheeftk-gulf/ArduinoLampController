package com.blogspot.ardulathtech.projectone;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
//import android.content.DialogInterface;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.blogspot.ardulathtech.projectone.R;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Abdul latheef on 14-11-2017.
 */

public class ConnectionFragment extends android.support.v4.app.Fragment {

    BluetoothAdapter BA;
    Bundle bundle1;
    boolean pro=false;
    boolean flag=false;

    Fragment fragment=null;
    ListView listView;
    LinearLayout linearLayout;
    TextView textView;
    ProgressBar progressBar;
    IntentFilter filter;

    ArrayList<String> arrayList=new ArrayList<>(3);
    ArrayList<String> arrayList2=new ArrayList<>(3);
    CommunicationFromeConnection communication=new CommunicationFromeConnection() {
        @Override
        public void StringAddresspass(String address,String s) {

        }
    };
    BroadcastReceiver receiver=new BroadcastReceiver() {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceive(Context context, Intent intent) {
            log(intent.getAction());
            try {
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if(Objects.equals(intent.getAction(), BluetoothDevice.ACTION_FOUND)){
                        try {
                            if(!arrayList.contains(device.getName())){
                                arrayList.add(device.getName());
                                arrayList2.add(device.getAddress());
                            }
                        }catch (Exception e){
                            log("error: "+e.getMessage());
                        }
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), R.layout.listview_layout, R.id.txt, arrayList);
                    listView.setAdapter(arrayAdapter);
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if(Objects.equals(intent.getAction(), BluetoothDevice.ACTION_FOUND)){
                            try {
                                if(!arrayList.contains(device.getName())){
                                    arrayList.add(device.getName());
                                    arrayList2.add(device.getAddress());
                                }

                            }catch (Exception e){
                                log("error: "+e.getMessage());
                            }
                        }
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), R.layout.listview_layout, R.id.txt, arrayList);
                    listView.setAdapter(arrayAdapter);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (Objects.equals(intent.getAction(), BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                        linearLayout.setVisibility(View.GONE);
                    }
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if (Objects.equals(intent.getAction(), BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                            linearLayout.setVisibility(View.GONE);
                        }
                    }else{
                        if(Objects.equals(intent.getAction(), BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                }
            }catch (Exception e){
                log(e.getMessage());
            }

        }
    };

    String FromFrag=null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            communication = (CommunicationFromeConnection) getActivity();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.connection_fragment,container,false);
        try {
            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                log("1");
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        10);
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    log("2");
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    log("3");
                    // No explanation needed; request the permission


                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                log("4");

            }
        }catch (Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        try {
            listView = view.findViewById(R.id.lv);



            linearLayout=view.findViewById(R.id.linear);
            textView = view.findViewById(R.id.linearText);
            progressBar=view.findViewById(R.id.progressBar);

            linearLayout.setVisibility(View.VISIBLE);
            bundle1 = getArguments();
            try {
                assert bundle1 != null;
                pro=bundle1.getBoolean("pro");
                FromFrag = bundle1.getString("key_fragment");
           /* if(FromFrag.equals("lamp")){
                Toast.makeText(getActivity(),"from lamp",Toast.LENGTH_SHORT).show();
            }else if (FromFrag.equals("home")){
                Toast.makeText(getActivity(),"from home",Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(getActivity(),"not recieved",Toast.LENGTH_SHORT).show();

            }*/

            } catch (Exception e) {
                e.printStackTrace();
            }


            final Bundle bundle = new Bundle();


            BA = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> myDevice = BA.getBondedDevices();
            if (!myDevice.isEmpty()) {
                for (BluetoothDevice device : myDevice) {
                    arrayList.add(device.getName());
                    arrayList2.add(device.getAddress());
                    if (device.getName().equals("HC-06") && device.getName().equals("HC-05") && device.getName().equals("HC-07")&& device.getName().equals("SPP-CA")) {
                        flag = true;
                    }
                }
                if (!flag) {
                    BA.startDiscovery();
                    String s="Searching for new......";
                    textView.setText(s);


                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("HC-05, HC-06, HC-07 or SPP-CA not paired, Go to Bluetooth setting page to continue. Use 0000 as default password");
                    builder.setCancelable(true);


                   /* builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });*/
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                   /* builder.setNeutralButton("Rate App", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Toast.makeText(getApplicationContext(), "Rating", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.blogspot.ardulathtech.projectone.releaseAlpha&hl=en"));
                            startActivity(intent);
                            dialogInterface.cancel();

                        }
                    });*/
                    AlertDialog alertDialog = builder.create();
                   // alertDialog.show();

                    //setting_page.setVisibility(View.VISIBLE);
                   // Toast.makeText(getActivity(), "HC-05,HC-06,HC-07 or SPP-CA not paired, \nGo to Bluetooth " +
                           // "setting page", Toast.LENGTH_LONG).show();
                   /* setting_page.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent ISettings = new Intent();
                            ISettings.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
                            fragment = new HomeFragment();
                            Bundle bundle4=new Bundle();
                            bundle4.putBoolean("pro",pro);
                            fragment.setArguments(bundle4);
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.FrameFragments, fragment);
                            startActivity(ISettings);
                            fragmentTransaction.commit();

                        }
                    });*/

                }


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_layout, R.id.txt, arrayList);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {

                            BA.cancelDiscovery();
                            String address = arrayList2.get(i);
                            communication.StringAddresspass(address,"Connection");
                            //Toast.makeText(getActivity(),address,Toast.LENGTH_SHORT).show();
                            bundle.putBoolean("pro",pro);
                            bundle.putString("key_address", address);
                            if (FromFrag.equals("home")) {
                                fragment = new HomeFragment();
                            } else if (FromFrag.equals("lamp")) {

                                fragment = new LampControllFragment8();
                            } else if (FromFrag.equals("rgb")) {

                                fragment = new RGBfragment();
                            }else if (FromFrag.equals("Incandescent")) {

                                fragment = new IncandescentFrag();
                            }
                            else {
                                fragment = new HomeFragment();
                                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                            }
                            fragment.setArguments(bundle);
                            assert getFragmentManager() != null;
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.FrameFragments, fragment);
                            fragmentTransaction.commit();


                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage()+" sa", Toast.LENGTH_LONG).show();
                        }


                    }
                });

            }else {
                if (!flag) {
                    BA.startDiscovery();
                    String s="Searching for new......";
                    textView.setText(s);
            }/*else {
                setting_page.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "No Bluetooth   Device paired yet, Go to Bluetooth " +
                        "setting page", Toast.LENGTH_LONG).show();
                setting_page.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ISettings = new Intent();
                        ISettings.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);

                        fragment = new HomeFragment();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.FrameFragments, fragment);
                        startActivity(ISettings);
                        fragmentTransaction.commit();

                    }
                });
            }*/
            }
        }catch (Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return view;
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    private void log(String s){
        Log.i("key",s);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        Objects.requireNonNull(getActivity()).unregisterReceiver(receiver);
        super.onDestroyView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        Objects.requireNonNull(getActivity()).registerReceiver(receiver,filter);
    }

    public interface CommunicationFromeConnection{
        void StringAddresspass(String address,String s);
    }
}
