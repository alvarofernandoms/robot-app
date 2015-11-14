package com.br.robot_app.connect;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 */
public class Connector {

    private final int SERVERPORT = 7394;
    private final String SERVER_IP = "10.0.0.1";
    private final int RETRY_IN = 1000;

    public static Connector objConnector = null;

    private Socket mainSocket;
    private ClientThread client;

    private Connector(){}

    public static Connector getConnector(){
        if (objConnector == null) {
            objConnector = new Connector();
        } else {
            // Nothing
        }
        return objConnector;
    }

    public void connectToAlfa(){
        client = new ClientThread();
        new Thread(client).start(); // Thread to connect to the server
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

        /**
         * TODO: isConnected is aways tru if the first connection succeed
         * @return true if the connection if is alive
         */
        private int isAlfaAwake() throws IOException {
            int result = 0;
            result = mainSocket.getInputStream().read();
            Log.d("Is Alfa Awake: ", String.valueOf(result));
            return result;
        }

        @Override
        public void run() {
            while(true){
                try{
                    InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                    mainSocket = new Socket(serverAddr, SERVERPORT);
                    isAlfaAwake();
                    Thread.sleep(RETRY_IN);
                } catch (UnknownHostException e) {
                    Log.d("Unknown Host: ", e.getMessage());
                } catch (IOException e) {
                    Log.d("IOException: ", e.getMessage());
                } catch (InterruptedException e) {
                    Log.d("InterruptedException: ", e.getMessage());
                }
            }
        }
    }
}
