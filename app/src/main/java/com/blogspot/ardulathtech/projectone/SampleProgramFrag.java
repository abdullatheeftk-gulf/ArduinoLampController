package com.blogspot.ardulathtech.projectone;

import android.annotation.SuppressLint;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



/**
 * Created by Abdul latheef on 24-11-2017.
 */

public class SampleProgramFrag extends Fragment {
    TextView program;
    CommunicationProgArduino communicationProgArduino=new CommunicationProgArduino() {
        @Override
        public void transferString(String s) {

        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
          communicationProgArduino=(CommunicationProgArduino)getActivity();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sample_program_layout,container,false);
        program=view.findViewById(R.id.arduino);
        communicationProgArduino.transferString("program");
        program.setText("/*    This program is only for Home Screen\n" +
                " *    I am using Arduino Uno \n" +
                " *    created by Abdul Latheef \n" +
                " * \n" +
                " */\n" +
                "\n" +
                "\n" +
                "#include <SoftwareSerial.h>\n" +
                "SoftwareSerial bt(10,11);//pin 10 will got Tx of HC-06 &\n" +
                "//pin 11 will go to Rx of HC-06\n" +
                "\n" +
                "String recieve_String=\"\";//it will recieve String from Android\n" +
                "char c;\n" +
                "int i=0;\n" +
                "\n" +
                "#define L1 2//pin 2 for digital control L1\n" +
                "#define L2 3//pin 3 for digital control L2\n" +
                "#define L3 4//pin 4 for digital control L3\n" +
                "#define L4 5//pin 5 for analog control L4 (PWM control)\n" +
                "\n" +
                "void setup() {\n" +
                "  bt.begin(9600);\n" +
                "  Serial.begin(9600);//for test purpose\n" +
                "  \n" +
                "  pinMode(L1,OUTPUT);\n" +
                "  pinMode(L2,OUTPUT);\n" +
                "  pinMode(L3,OUTPUT);\n" +
                "  pinMode(L4,OUTPUT);\n" +
                "}\n" +
                "\n" +
                "void loop() {\n" +
                "  while(bt.available()>0)\n" +
                "  {\n" +
                "     c=bt.read();\n" +
                "     if(c=='a')\n" +
                "      {\n" +
                "        break;\n" +
                "      }else\n" +
                "      {\n" +
                "        recieve_String+=c;\n" +
                "     }\n" +
                "     delay(1);\n" +
                "  }\n" +
                "\n" +
                "  \n" +
                "  if(recieve_String!=\"\")//Checking anything recieved\n" +
                "  {\n" +
                "      i=recieve_String.toInt();//if recieved string it will convert to int\n" +
                "  }\n" +
                "\n" +
                "  \n" +
                "  switch(i){\n" +
                "    case 401:\n" +
                "    {\n" +
                "      digitalWrite(L1,1);//switch on Lamp 1\n" +
                "      break;\n" +
                "    }\n" +
                "    case 402:\n" +
                "    {\n" +
                "      digitalWrite(L1,0);//switch off Lamp 1\n" +
                "      break;\n" +
                "    }\n" +
                "    case 403:\n" +
                "    {\n" +
                "      digitalWrite(L2,1);//switch on Lamp 2\n" +
                "      break; \n" +
                "    }\n" +
                "    case 404:\n" +
                "    {\n" +
                "      digitalWrite(L2,0);//switch off Lamp 2\n" +
                "      break; \n" +
                "    }\n" +
                "     case 405:\n" +
                "    {\n" +
                "      digitalWrite(L3,1);//switch on Lamp 3\n" +
                "      break; \n" +
                "    }\n" +
                "    case 406:\n" +
                "    {\n" +
                "      digitalWrite(L3,0);//switch off Lamp 3\n" +
                "      break; \n" +
                "    } \n" +
                "  }\n" +
                "case 407:\n" +
                "    {\n" +
                "       digitalWrite(L1,1); //switch on Lamp 1\n" +
                "       digitalWrite(L2,1); //switch on Lamp 2\n" +
                "       digitalWrite(L3,1); //switch on Lamp 3\n" +
                "       break;\n" +
                "       \n" +
                "    }\n" +
                "    case 407:\n" +
                "    {\n" +
                "       digitalWrite(L1,0); //switch off Lamp 1\n" +
                "       digitalWrite(L2,0); //switch off Lamp 2\n" +
                "       digitalWrite(L3,0); //switch off Lamp 3\n" +
                "       break;\n" +
                "    }" +

                "  if(i>=0&&i<=255)\n" +
                "  {\n" +
                "    analogWrite(L4,i);//Brightness Controll\n" +
                "  }\n" +
                "  Serial.println(i);\n" +
                "  delay(100);\n" +
                "  recieve_String=\"\";\n" +
                "}");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public interface CommunicationProgArduino{
        void transferString(String s);
    }
}
