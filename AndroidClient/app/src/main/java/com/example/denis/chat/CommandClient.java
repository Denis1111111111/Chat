package com.example.denis.chat;

import android.graphics.Color;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.denis.chat.Gson.ConverterJson;
import com.example.denis.chat.Gson.RequestContext;
import com.example.denis.chat.Fragments.UserPage;

public class CommandClient
{
    MainActivity client;
    private ConverterJson converter = new ConverterJson();
    RequestContext reqTxt;
    final String LOG_TAG = "my logs";


    public CommandClient(MainActivity client)
    {
        this.client = client;
    }


    public void sendingMessage(String message)
    {
        client.outMessage.println(message);

        new Thread(new Runnable() {
            @Override
            public void run() {

                client.outMessage.flush();
            }
        }).start();
    }


//     void showMessage(String message)
//    {
//        UserPage.txt.setText(UserPage.txt.getText().toString()+ message + "\n");
//        Log.d(LOG_TAG, "lalalalalalallalalallalala");
//        UserPage.txt.postInvalidate();
//    }



        public void getMessages(String nameDialog)
    {
        reqTxt = new RequestContext(client.name, client.password, client.room, "getMessages", nameDialog);
        String json = converter.toGsonClient(reqTxt);

        sendingMessage(json);
    }

    public void close(String autorize)
    {
        if(autorize.equals("autorize"))
        {
            RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "close", client.name + " offline");
            String json = converter.toGsonClient(reqTxt);
            sendingMessage(json);
            return;
        }
        RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "close", "Uknown person left the dialog");
        String json = converter.toGsonClient(reqTxt);

        sendingMessage(json);
    }

    public void SignIn()
    {
        RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "SignIn", "");
        String json = converter.toGsonClient(reqTxt);

        sendingMessage(json);
    }

    public void CancelAutorize()
    {
        RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "CancelAutorize", "");
        String json = converter.toGsonClient(reqTxt);

        sendingMessage(json);
    }
//
//    public void sendMessage(String message)
//    {
//        RequestContext reqTxt = new RequestContext(name, password, room, "sendMessage", name + ": " + message);
//        String json = converter.toGsonClient(reqTxt);
//
//        sendingMessage(json);
//
//        if(name.equals("Admin"))
//        {
//            frameAdmin.jtfMessage.setText("");
//        }
//
//        else
//        {
//            frameClient.jtfMessage.setText("");
//        }
//    }

    public void setNameAndPassword()
    {
        RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "setNameAndPassword", "");
        String json = converter.toGsonClient(reqTxt);

        sendingMessage(json);
    }

    public void sendMessage(String message)
    {
        RequestContext reqTxt = new RequestContext(client.name, client.password, client.room, "sendMessage", client.name + ": " + message);
        String json = converter.toGsonClient(reqTxt);

        sendingMessage(json);
    }
}
