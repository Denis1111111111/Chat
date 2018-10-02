package com.example.denis.chat;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.denis.chat.Fragments.Login;
import com.example.denis.chat.Fragments.UserPage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity   {
    Button create;
    Button signIn;
    Button cancel;
    EditText edTextname;
    EditText edTextpassword;
    TextView textLogin;
    public static String name = "unknown";
    public static String password = null;
    public static String room = "Global";
    public ArrayList<String> rooms = new ArrayList<String>();
    HashMap<Integer, Color> colorMap = new HashMap<Integer, Color>();
    Socket clientSocket;
    Scanner inMessage;
    PrintWriter outMessage;
    public static CommandClient command;
    static ClientController controller;
    public static MainActivity client;
    String messages;


    FragmentTransaction transaction;
    Fragment fragmentUser;
    Fragment fragmentLogin;

    final String LOG_TAG = "my logs";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        client = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputFragment();

//        create = (Button) findViewById(R.id.create);
//        create.setOnClickListener(onClickListener);
//        signIn = (Button) findViewById(R.id.signIn);
//        signIn.setOnClickListener(onClickListener);
//        cancel = (Button) findViewById(R.id.cancel);
//        cancel.setOnClickListener(onClickListener);
//        edTextname = (EditText) findViewById(R.id.writeName);
//        edTextpassword = (EditText) findViewById(R.id.writePassword);
//        textLogin = (TextView) findViewById(R.id.textLogin);
        command = new CommandClient(this);
        controller = new ClientController(this);
        openConnect();
    }

    private void openConnect()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket = new Socket("192.168.0.110", 8080);
                    inMessage = new Scanner(clientSocket.getInputStream());
                    outMessage = new PrintWriter(clientSocket.getOutputStream());
                    handleServer();

                } catch (IOException e)
                {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void handleServer()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    while (true)
                    {
                        if (inMessage.hasNext())
                        {
                            String inMes = inMessage.nextLine();
                            Log.d(LOG_TAG, inMes + "   input");
                            controller.identificateMessage(inMes);
                            Log.d(LOG_TAG, inMes + "   input2222");
                        }
                    }
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.create: {
                    name = edTextname.getText().toString();
                    password = edTextpassword.getText().toString();
                    command.setNameAndPassword();

                    break;
                }

                case R.id.cancel: {
                    command.CancelAutorize();
                    break;
                }

                case R.id.signIn: {
                    name = edTextname.getText().toString();
                    password = edTextpassword.getText().toString();
                    command.SignIn();
                    break;
                }
            }
        }
    };

    public void inputFragment()
    {
        fragmentLogin = new Login();
        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout,fragmentLogin );
        transaction.replace(R.id.frameLayout, fragmentLogin);
        transaction.commit();
    }

    public void changeFragment()
    {
        fragmentUser = new UserPage();
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout,fragmentUser );
        transaction.commit();
    }

    void showMessage(String messager) {
        messages = messager;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                UserPage.txt.setText(UserPage.txt.getText().toString()+ messages + "\n");
                UserPage.txt.postInvalidate();
            }
        });
    }
}
