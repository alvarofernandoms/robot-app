package com.br.robot_app.connect;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by paulo on 16/10/15.
 */
public class Connector {

    public static Connector objConnector = null;

    private Socket mainSocket = new Socket();
    private final int SERVERPORT = 7394;
    private final String SERVER_IP = "10.5.5.1";

    private ClientThread client;

    private Connector(){
        Log.d("Connector: ", " connecting...");
        client = new ClientThread();
        new Thread(client).start(); // Thread to connect to the server
    }

    public static Connector getConnector(){
        if (objConnector == null) {
            objConnector = new Connector();
        } else {
            // Nothing
        }
        return objConnector;
    }

    public void sender(File program){
        try{
            // Preparing to send
            byte[] byteArray  = new byte [(int)program.length()];
            FileInputStream fileInput = new FileInputStream(program);
            BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
            bufferedInput.read(byteArray,0,byteArray.length);
            OutputStream output = mainSocket.getOutputStream();

            // Sending the file
            output.write(byteArray,0,byteArray.length);
            output.flush();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private class ClientThread implements Runnable{
        @Override
        public void run() {
            Log.d("Running: ","running...");
            try{
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                mainSocket = new Socket(serverAddr, SERVERPORT);
            } catch (UnknownHostException e) {
                Log.d("Unknown Host: ", e.getMessage());
            } catch (IOException e) {
                Log.d("IOException: ", e.getMessage());
            }
        }
    }
}
