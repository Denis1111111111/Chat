package com.example.denis.chat;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.denis.chat.Gson.AnswerContext;
import com.example.denis.chat.Gson.ConverterJson;
import com.example.denis.chat.Gson.RequestContext;
import com.example.denis.chat.Fragments.Login;

public class ClientController
{
    MainActivity client;
    RequestContext reqTxt;
    final String LOG_TAG = "my logs";



    private ConverterJson converter = new ConverterJson();


    public ClientController(MainActivity client)
    {
        this.client = client;
    }


     void identificateMessage(String message)
    {
        AnswerContext context = converter.fromJsonClient(message);


        switch(context.commandName)
        {

            case "SendMessage":
            {
                switch(context.commandContext)
                {
                    case "Wrong name or password":
                    {
                        Login.textLogin.setText("Wrong name or password");
                        break;
                    }

                    case "Account not authorized":
                    {
                        client.changeFragment();
                        client.command.getMessages(client.room);
                        break;
                    }

                    case "Use a different name":
                    {
                        Login.textLogin.setText("Use a different name");
                        break;
                    }

                    case "Account created":
                    {
                        client.changeFragment();
                        client.showMessage("Account created");
                        client.command.getMessages(client.room);

                        reqTxt = new RequestContext(client.name, client.password, client.room, "AllDialog", "");
                        String json = converter.toGsonClient(reqTxt);
                        client.command.sendingMessage(json);

                        break;
                    }

                    case "Account authorized":
                    {
                        client.changeFragment();
                        client.showMessage("Account authorized");
                        client.command.getMessages(client.room);

                        reqTxt = new RequestContext(client.name, client.password, client.room, "AllDialog", "");
                        String json = converter.toGsonClient(reqTxt);
                        client.command.sendingMessage(json);
                        break;
                    }

//                    case "Room Created":
//                    {
//                        room = reqTxt.commandContext;
//
//                        if(name.equals("Admin"))
//                        {
//                            frameAdmin.room.setText("Room: " + reqTxt.commandContext);
//                            frameAdmin.jtaTextAreaMessage.setText("");
//                        }
//
//                        else
//                        {
//                            frameClient.room.setText("Room: " + reqTxt.commandContext);
//                            frameClient.jtaTextAreaMessage.setText("");
//                        }
//
//                        showMessage("Room Created");
//                        sendMessage("online");
//
//                        break;
//                    }
//
//                    case "Find room":
//                    {
//                        room = reqTxt.commandContext;
//
//                        if(name.equals("Admin"))
//                        {
//                            frameAdmin.room.setText("Room: " + reqTxt.commandContext);
//                            frameAdmin.jtaTextAreaMessage.setText("");
//                        }
//
//                        else
//                        {
//                            frameClient.room.setText("Room: " + reqTxt.commandContext);
//                            frameClient.jtaTextAreaMessage.setText("");
//                        }
//
//                        getMessages(reqTxt.commandContext);
//                        sendMessage("online");
//
//
//                        break;
//                    }
//
//                    case "Access open":
//                    {
//                        showMessage("Access open for " + reqTxt.commandContext);
//                        break;
//                    }

                    default:
                    {
                        client.showMessage(context.commandContext);
                    }
                }
                break;
            }

//            case "AllDialog":
//            {
//                rooms = context.rooms;
//
//                for(int i = 0; i < rooms.size(); i++)
//                {
//                    colorMap.put(i, Color.WHITE);
//                }
//
//                if(name.equals("Admin"))
//                {
//                    frameAdmin.table.listen.fireTableDataChanged();
//                    break;
//                }
//
//                frameClient.table.listen.fireTableDataChanged();
//
//                break;
//            }

//            case "AddNewDialog":
//            {
//                addRoom(context.commandContext);
//
//                if(name.equals("Admin"))
//                {
//                    frameAdmin.table.listen.fireTableDataChanged();
//                    break;
//                }
//
//                frameClient.table.listen.fireTableDataChanged();
//
//                break;
//            }
//
//            case "SendMessageInCloseRoom":
//            {
//                colorMap.put(((Integer)rooms.indexOf(context.room)), Color.RED);
//
//                if(name.equals("Admin"))
//                {
//                    frameAdmin.table.listen.fireTableDataChanged();
//                    break;
//                }
//
//                frameClient.table.listen.fireTableDataChanged();
//                break;
//            }
        }
    }
}
