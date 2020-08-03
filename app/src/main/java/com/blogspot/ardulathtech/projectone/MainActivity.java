package com.blogspot.ardulathtech.projectone;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
//import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
//import android.location.Address;
import android.content.IntentFilter;
//import android.graphics.Color;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.os.Parcelable;
//import android.preference.DialogPreference;
//import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.Button;
import android.widget.Button;
import android.widget.Toast;
import com.blogspot.ardulathtech.projectone.util.IabBroadcastReceiver;
import com.blogspot.ardulathtech.projectone.util.IabHelper;
import com.blogspot.ardulathtech.projectone.util.IabResult;
import com.blogspot.ardulathtech.projectone.util.Inventory;
import com.blogspot.ardulathtech.projectone.util.Purchase;

import java.util.Random;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ConnectionFragment.CommunicationFromeConnection,HomeFragment.Communication,LampControllFragment8.Communication8
,RGBfragment.CommunicationRGB,SampleProgramFrag.CommunicationProgArduino,IncandescentFrag.CommunicationIn,IabBroadcastReceiver.IabBroadcastListener{

    byte count=0;

    String PREFS_NAME="MyPrefsFile";
    SharedPreferences settings,sharedPreferences;


    boolean dialogReapeater=false;//prevent dialog repeater


    BluetoothAdapter BA;
    //BluetoothSocket bluetoothSocket;
    String address;
    String fragmentIdentity="";
    NavigationView navigationView;
    Toolbar toolbar;

    FloatingActionButton fab;

    IabHelper mHelper;
    //Boolean pro=false;

    //delete it after
    Boolean pro=true;//delete it after
    //delete it after

    String key="";

    devpayload d=new devpayload();
    String sku=d.sku1;
    String Payload=d.payload+d.RandonGen(100);

    int countOfOperation=0;



   android.support.v4.app.Fragment fragment=null;


    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListner=new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase info) {
            if(result.isSuccess()&&info.getSku().equals(sku)&&info.getDeveloperPayload().equals(Payload)){
               // pro=true;
               // messageLong("purchase success");

                try {
                    mHelper.queryInventoryAsync(mQueryInventoryFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }

            }else{
                pro=true;
                messageLong("Purchase failed, Try again");
            }

        }
    };
    IabHelper.QueryInventoryFinishedListener mQueryInventoryFinishedListener=new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            try {
                if (result.isSuccess() && inv.hasPurchase(sku)) {
                    pro = true;
                    // messageLong("purchased already");
                    fab.setImageResource(R.drawable.download);
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colormy)));
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_main_drawer2);
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.main2);
                    fragment = new HomeFragment();
                    fragmentTransition(fragment);


                } else {
                    //messageLong("didn't purchase");
                    //pro=false;

                    //delete it after
                    pro = true;
                    fab.setImageResource(R.drawable.download);
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colormy)));
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_main_drawer2);
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.main2);
                    //delete it after


                    fragment = new HomeFragment();
                    fragmentTransition(fragment);
                }
            }catch (Exception e){

            }

        }
    };
    IabBroadcastReceiver mReciever=new IabBroadcastReceiver(MainActivity.this);


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);
        log("160");
        try {
            super.onCreate(savedInstanceState);
            try {
                setContentView(R.layout.activity_main);
            } catch (Exception e) {
                log("166 "+e.getMessage());

            }

            log("170");
            sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            try {
                countOfOperation = sharedPreferences.getInt("CounterValue", 0);
                countOfOperation++;
            } catch (Exception e) {
              log("177 "+e.getMessage());
            }
            editor.putInt("CounterValue", countOfOperation);
            editor.apply();
            log("181");

            // messageLong(String.valueOf(rvalue));
            //messageLong(Payload);
            //messageLong(Payload);
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            log("188");
            Payload += Build.BRAND;
            Payload += getResources().getString(R.string.payload);
            // messageLong(Payload);
            key += getResources().getString(R.string.key641);
            key += getResources().getString(R.string.key642);
            key += getResources().getString(R.string.key643);
            log("195");
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            log("197");
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            log("202");
            navigationView = findViewById(R.id.nav_view);
            log("204");
            navigationView.inflateMenu(R.menu.activity_main_drawer);
            log("206");
            navigationView.setNavigationItemSelectedListener(this);

            log("209");
            mHelper = new IabHelper(MainActivity.this, key);
            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                @Override
                public void onIabSetupFinished(IabResult result) {
                    if (result.isSuccess()) {
                        // messageLong("setup is finished");
                         log("216");
                        try {
                        IntentFilter filter = new IntentFilter(IabBroadcastReceiver.ACTION);
                        log("219");
                        registerReceiver(mReciever, filter);

                            mHelper.queryInventoryAsync(mQueryInventoryFinishedListener);
                        } catch (Exception e) {
                            log("222"+e.getMessage());
                        }
                    } else {
                        log("225");
                       // messageLong("Setup failed update Play store ");
                    }
                }
            });

            fragment = new HomeFragment();
            log("232");
            fragmentTransition(fragment);
            BA = BluetoothAdapter.getDefaultAdapter();
            if (BA == null) {

                messageShort();
                finish();
            } else {
                if (!BA.isEnabled()) {
                    Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(i, 1);
                } else {
                    settings = getSharedPreferences(PREFS_NAME, 0);
                    if (settings.getBoolean("isFirstTime", true)) {

                        FirstTimeAlert();

                        settings.edit().putBoolean("isFirstTime", false).apply();
                    } else {
                        Random random = new Random();
                        int rvalue = random.nextInt(100) + 1;
                        //rvalue=3;
                        if (rvalue == 10) {
                            callingRating(true);
                            dialogReapeater = true;//prevent dialog repeater
                        }
                    }

                }


                fab = findViewById(R.id.fab);
                fab.setVisibility(View.VISIBLE);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!pro) {
                            Snackbar.make(view, "Purchase Full Version", Snackbar.LENGTH_LONG)
                                    .setAction("Purchase", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            try {
                                                mHelper.launchPurchaseFlow(MainActivity.this, sku, 1001, mPurchaseFinishedListner, Payload);
                                            } catch (IabHelper.IabAsyncInProgressException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).setActionTextColor(Color.argb(255, 255, 255, 0)).show();
                        } else {
                            Snackbar.make(view, "Download Sample Program ", Snackbar.LENGTH_LONG)
                                    .setAction("Download", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            // messageShort(fragmentIdentity);
                                            if (fragmentIdentity.equals("HomeFragment")) {
                                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/open?id=13DB8EpJgM_LJxwgGjgqwrGGcOrTR-z_k"));
                                                startActivity(i);
                                            }
                                            if (fragmentIdentity.equals("8lamp")) {
                                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/open?id=1_B9Vulq0nWWgC-3O9_gWfmDkP4JBfnwz"));
                                                startActivity(i);
                                            }
                                            if (fragmentIdentity.equals("RGB")) {
                                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/open?id=16193XeexsvbQqfFQAzLSPozOuOBKFqtd"));
                                                startActivity(i);
                                            }
                                            if (fragmentIdentity.equals("Incandescent")) {
                                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/open?id=1wNB6qyc3sufE1KBItciaXeq-bXVGTbAr"));
                                                startActivity(i);
                                            }

                                        }
                                    }).show();
                        }
                    }
                });


            }
        }catch (Exception e){

        }
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_main_drawer2);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.main2);
    }

    private void FirstTimeAlert() {
        try {
            //messageLong("first Time running");
            AlertDialog.Builder bulilderFirstTime = new AlertDialog.Builder(MainActivity.this);

            bulilderFirstTime.setCancelable(true);


            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialogfirsttime, null);
            bulilderFirstTime.setView(view);
            Button ok = view.findViewById(R.id.ok);
            final AlertDialog dialog2 = bulilderFirstTime.create();
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog2.dismiss();
                }
            });
            dialog2.show();
        }catch (Exception e){

        }
    }

    private void callingRating(final boolean b) {
        try {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
            @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.dialog, null);
            builder1.setView(view);
            // builder1.setMessage("If you like this App, Please Rate it.");
            builder1.setCancelable(true);
            final AlertDialog dialog = builder1.create();

            Button bto = view.findViewById(R.id.button2);
            bto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (b) {
                        dialog.cancel();
                    } else {
                        finish();
                    }
                }
            });
            Button yes = view.findViewById(R.id.button3);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (b) {
                        // messageShort("rate later");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.blogspot.ardulathtech.projectone.releaseAlpha&hl=en"));
                        startActivity(intent);
                        dialog.cancel();
                    } else {
                        //messageShort("rate later");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.blogspot.ardulathtech.projectone.releaseAlpha&hl=en"));
                        startActivity(intent);
                        finish();

                    }
                }
            });

            if (countOfOperation >= 10) {
                dialog.show();
            }
        }catch (Exception e){

        }

    }

    @Override
    protected void onDestroy() {


        try {

            if (mHelper != null) {
                try {
                    mHelper.dispose();
                    mHelper = null;
                } catch (IabHelper.IabAsyncInProgressException e) {
                   // e.printStackTrace();
                }
            }
            if (BA.isEnabled()) {
                BA.disable();
            }
            unregisterReceiver(mReciever);

        }catch (IllegalArgumentException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
           // LogDebugging(e.getMessage());
           // e.printStackTrace();
        }catch (Exception e){
           // e.printStackTrace();
            //LogDebugging(e.getMessage());
            //e.printStackTrace();
        }
        super.onDestroy();
    }

    private void log(String message) {
        Log.i("key",message);
    }


    private void fragmentTransition(Fragment fragment) {
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean("pro", true);
           fragment.setArguments(bundle);
            log("439");
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameFragments, fragment).commit();
            //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //fragmentTransaction.replace(R.id.FrameFragments, fragment);
            log("442");
            //fragmentTransaction.commit();
            log("444");
        }catch (Exception e){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!mHelper.handleActivityResult(requestCode,resultCode,data)) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1) {
                if (resultCode == -1) {
                    settings=getSharedPreferences(PREFS_NAME,0);
                    if(settings.getBoolean("isFirstTime",true)){

                        FirstTimeAlert();

                        settings.edit().putBoolean("isFirstTime",false).apply();
                    }else {
                        Random random=new Random();
                        int rvalue=random.nextInt(100)+1;
                       // rvalue=11;
                        if(rvalue==11){
                            callingRating(true);
                            dialogReapeater=true;
                        }
                    }
                    //messageShort("Bluetooth is Activated");
                } else {
                    count++;
                    if(count==2) {
                        messageLong("You didn't  activate Bluetooth, So app is going to exit");
                        finish();
                    }else {
                        Intent bluetoothOpen=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(bluetoothOpen,1);
                    }
                }
            }
        }
    }

    private void messageShort() {
       // Toast.makeText(MainActivity.this, "Your Device is not supported Bluetooth", Toast.LENGTH_SHORT).show();
    }
    private void messageLong(String s) {
       // Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(!pro) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Really, Do you want to Exit ?");
                builder.setCancelable(true);


                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();

                    }
                });
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setNeutralButton("Rate App", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // Toast.makeText(getApplicationContext(), "Rating", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.blogspot.ardulathtech.projectone.releaseAlpha&hl=en"));
                        startActivity(intent);
                        dialogInterface.cancel();
                        finishAffinity();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }else {
                Random random=new Random();
                int rvalue=random.nextInt(100)+1;
                //rvalue=3;
                if(rvalue%4==0){
                    if(!dialogReapeater) {
                        callingRating(false);
                    }
                }else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Really, Do you want to Exit ?");
                    builder.setCancelable(true);


                    builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finishAffinity();

                        }
                    });
                    builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    if(countOfOperation>8) {
                        alertDialog.show();
                    }else {
                        finishAffinity();
                    }
                   // super.onBackPressed();
                    //finish();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(!pro) {
            getMenuInflater().inflate(R.menu.main, menu);
        }else {
            getMenuInflater().inflate(R.menu.main2, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent i=new Intent(MainActivity.this,AboutActivity.class);
            startActivity(i);
            return true;
        }else if(id == R.id.action_Share){
            Intent shareIntent=new Intent(Intent.ACTION_SEND);
            shareIntent.setType("Text/plain");
            String shareBody="https://play.google.com/store/apps/details?id=com.blogspot.ardulathtech.projectone.releaseAlpha&hl=en";
            String shareSub="This App will help you to control lamps with Arduino";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(shareIntent,"Share Using"));
        }else if(id == R.id.action_buy){
            if(!pro) {
                try {
                    mHelper.launchPurchaseFlow(MainActivity.this, sku, 1001, mPurchaseFinishedListner, Payload);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }else {
                messageLong("Purchased Already");
            }

        }else if (id==R.id.action_download){
            //do later
            Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("https://drive.google.com/file/d/1rToKc-Lhzf0GUDOkTj5GRueblhPXorTU/view?usp=sharing"));
            startActivity(i);
        }else{
            //do Later
           messageLong(fragmentIdentity);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {
            log("630");
            fab.setVisibility(View.VISIBLE);
            fragment=new HomeFragment();
            //fragment=new LampControllFragment8();
            Bundle bundle=new Bundle();
            bundle.putString("key_address",address);
            bundle.putBoolean("pro",true);
            fragment.setArguments(bundle);
            log("636");
            fragmentTransition(fragment);
            log("639");


        } else if (id == R.id.nav_8Button) {
            fab.setVisibility(View.VISIBLE);
            fragment=new LampControllFragment8();
            Bundle bundle=new Bundle();
            bundle.putString("key_address",address);
            bundle.putBoolean("pro",pro);
            fragment.setArguments(bundle);
            fragmentTransition(fragment);

        }else if (id == R.id.nav_RGB) {
            fab.setVisibility(View.VISIBLE);
            fragment=new RGBfragment();
            Bundle bundle=new Bundle();
            bundle.putString("key_address",address);
            bundle.putBoolean("pro",pro);
            fragment.setArguments(bundle);
            fragmentTransition(fragment);


        }else if (id == R.id.nav_incandescent) {
            fab.setVisibility(View.VISIBLE);
           // if(pro) {
                fragment = new IncandescentFrag();
                Bundle bundle = new Bundle();
                bundle.putBoolean("pro",pro);
                bundle.putString("key_address", address);
                fragment.setArguments(bundle);
                fragmentTransition(fragment);
                fragmentIdentity="incandesent";
            //}else {

                //messageLong("Purchase full version");
              /*  AlertDialog.Builder builder_requestPurchase=new AlertDialog.Builder(MainActivity.this);
                builder_requestPurchase.setCancelable(true);
                builder_requestPurchase.setMessage("You have to Purchase Full version to use this Menu.\n" +
                        "Do you want Purchase now?.");
                builder_requestPurchase.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });
                builder_requestPurchase.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            mHelper.launchPurchaseFlow(MainActivity.this, sku, 1001, mPurchaseFinishedListner, Payload);
                        } catch (IabHelper.IabAsyncInProgressException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Random random=new Random();
                int i=random.nextInt(2)+1;
                if(i==2) {
                    AlertDialog dialog_inc = builder_requestPurchase.create();
                    dialog_inc.show();
                }else {
                    messageLong("You have to Purchase Full version to use this Menu");
                }*/
           // }


        } else if (id == R.id.nav_ConnectionCircuit) {
           Intent image=new Intent(MainActivity.this,Main2Activity.class);
           startActivity(image);

        } else if (id == R.id.nav_Arduino_program) {
            fab.setVisibility(View.GONE);
            fragment=new SampleProgramFrag();

            fragmentTransition(fragment);

        } else if (id == R.id.nav_about) {
            Intent i=new Intent(MainActivity.this,AboutActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_Exit) {
            finish();

        }
        else if(id==R.id.nav_pdf){
            //do later
            Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("https://drive.google.com/file/d/1rToKc-Lhzf0GUDOkTj5GRueblhPXorTU/view?usp=sharing"));
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void StringAddresspass(String Address,String s) {
        address=Address;
        fragmentIdentity=s;
        //Toast.makeText(getApplicationContext(),fragmentIdentity,Toast.LENGTH_LONG).show();
    }

    @Override
    public void PassBluetoothSocket(String s) {

       fragmentIdentity=s;
        //Toast.makeText(getApplicationContext(),fragmentIdentity,Toast.LENGTH_LONG).show();
    }

    @Override
    public void transferString8(String s) {
        fragmentIdentity=s;
       // Toast.makeText(getApplicationContext(),fragmentIdentity,Toast.LENGTH_LONG).show();

    }

    @Override
    public void TansferRGB(String s) {
        fragmentIdentity=s;
        //Toast.makeText(getApplicationContext(),fragmentIdentity,Toast.LENGTH_LONG).show();

    }

    @Override
    public void transferString(String s) {
        fragmentIdentity=s;
       // Toast.makeText(getApplicationContext(),fragmentIdentity,Toast.LENGTH_LONG).show();
    }

    @Override
    public void transferInString(String s) {
        fragmentIdentity=s;
        //Toast.makeText(getApplicationContext(),fragmentIdentity,Toast.LENGTH_LONG).show();
    }

    @Override
    public void receivedBroadcast() {
        try {
            mHelper.queryInventoryAsync(mQueryInventoryFinishedListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }

    }
}
