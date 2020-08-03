package com.blogspot.ardulathtech.projectone;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {
    Button ok;
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {



            setContentView(R.layout.activity_about);
            ok = findViewById(R.id.ok);
            txt = findViewById(R.id.textView2);
            String s="Arduino Lamp Controller is an app to Control the Lamp through Bluetooth and " +
                    "Arduino.You can use Bluetooth Devices Like HC-05,HC-06,HC-07 or SPP-CA and use any Arduino Board " +
                    "which allow these Bluetooth Devices.\n" + "\n" +
                    "You Can Download Sample Program by Fab button given in bottom. Here each button " +
                    "will send Integer Value in String Format with affixing 'a' on end.";
            txt.setText(s);

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int w = dm.widthPixels;
            int h = dm.heightPixels;
            getWindow().setLayout((int) (w * .85), (int) (h * .7));
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }catch (Exception e){
           // Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
