package com.example.denis.chat.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.denis.chat.CommandClient;
import com.example.denis.chat.MainActivity;
import com.example.denis.chat.R;


public class Login extends Fragment implements View.OnClickListener {

    MainActivity client = MainActivity.client;
    CommandClient command = client.command ;
    EditText edTextname;
    EditText edTextpassword;
    public static TextView textLogin;
    Button create;
    Button signIn;
    Button cancel;
    FragmentTransaction transaction;
    Fragment fragmentUser;
    public static final String TAG = "TAG";




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.login, container, false);
        fragmentUser = new UserPage();
        command = client.command ;
        create = (Button) v.findViewById(R.id.create);
        create.setOnClickListener(this);
        signIn = (Button) v.findViewById(R.id.signIn);
        signIn.setOnClickListener(this);
        cancel = (Button) v.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        edTextname = (EditText) v.findViewById(R.id.writeName);
        edTextpassword = (EditText) v.findViewById(R.id.writePassword);
        textLogin = (TextView) v.findViewById(R.id.textLogin);
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.create: {
                    client.name = edTextname.getText().toString();
                    client.password = edTextpassword.getText().toString();
                    command.setNameAndPassword();

                    break;
                }

                case R.id.cancel: {
                    command.CancelAutorize();
                    break;
                }

                case R.id.signIn: {
                    client.name = edTextname.getText().toString();
                    client.password = edTextpassword.getText().toString();
                    command.SignIn();
                    break;
                }
            }
    }
}