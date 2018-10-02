package com.example.denis.chat.Fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.denis.chat.CommandClient;
import com.example.denis.chat.MainActivity;
import com.example.denis.chat.R;

public class UserPage extends Fragment implements View.OnClickListener {

    MainActivity client = MainActivity.client;
    CommandClient command = client.command ;
   // public static ListView listViev;
    EditText EdTextMessage;
    EditText EdTextNameRoom;
    EditText EdTextNamePrivateRoom;
    EditText EdTextNamePersonAddToPrivateRoom;
    Button send;
    Button btnCreateRoom;
    Button btnCreatePrivateRoom;
    Button btnAddPersonToPrivateRoom;

    public static   TextView txt;




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.userpage, container, false);
        command = client.command ;

         txt = (TextView) v.findViewById(R.id.txtMessage);
         txt.setMovementMethod(new ScrollingMovementMethod());
         txt.setTextColor(Color.BLACK);
         txt.setTextSize(12);



        send = (Button) v.findViewById(R.id.btnSend);
        send.setOnClickListener(this);
        btnCreateRoom = (Button) v.findViewById(R.id.btnCreateRoom);
        btnCreateRoom.setOnClickListener(this);
        btnCreatePrivateRoom = (Button) v.findViewById(R.id.btnCreatePrivateRoom);
        btnCreatePrivateRoom.setOnClickListener(this);
        btnAddPersonToPrivateRoom = (Button) v.findViewById(R.id.btnAddPersonToPrivateRoom);
        btnAddPersonToPrivateRoom.setOnClickListener(this);

        EdTextMessage = (EditText) v.findViewById(R.id.EdTextMessage);
        EdTextNameRoom = (EditText) v.findViewById(R.id.EdTextNameRoom);
        EdTextNamePrivateRoom = (EditText) v.findViewById(R.id.EdTextNamePrivateRoom);
        EdTextNamePersonAddToPrivateRoom = (EditText) v.findViewById(R.id.EdTextNamePersonAddToPrivateRoom);

        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onClick(View v) {

         switch (v.getId()) {
         case R.id.btnCreateRoom: {


         break;
         }

         case R.id.btnCreatePrivateRoom: {

         break;
         }

         case R.id.btnAddPersonToPrivateRoom: {

         break;
         }

         case R.id.btnSend: {
             client.command.sendMessage(EdTextMessage.getText().toString());
             EdTextMessage.setText("");
             break;
         }
         }
    }
}



