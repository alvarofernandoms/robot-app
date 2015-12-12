package com.br.robot_app.connect;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 */
public class Connector {

    private final int SERVERPORT = 7394;
    private final String SERVER_IP = "10.0.0.1";
    private final int RETRY_IN = 5000;

    public static Connector objConnector = null;

    private Socket mainSocket;
    private ClientThread client;
    private boolean connectionStatus;

    private Connector(){}

    /**
     * Singleton to make only one instance of Connector
     * @return the only instance of Connector
     */
    public static Connector getConnector(){
        if (objConnector == null) {
            objConnector = new Connector();
        } else {
            // Nothing
        }
        return objConnector;
    }

    /**
     * Start a connection with Alfa
     */
    public void connectToAlfa(){
        client = new ClientThread();
        new Thread(client).start(); // Thread to connect to the server
    }

    /**
     * This send the program file to the Alfa thought socket
     * @param program The json program to send to Alfa
     */
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
            output.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Verify the connection to Alfa thought the wifi
     * This is used to connection retry
     * @return the status of the connection based on socket connection
     */
    public boolean getConnectionStatus(){
        if (mainSocket != null) {
            connectionStatus = mainSocket.isConnected() && ! mainSocket.isClosed();
        }
        return this.connectionStatus;
    }

    /**
     * Iner class to create a Thread to create a connection with the socket
     *      defined on Alfa server
     */
    private class ClientThread implements Runnable{
        @Override
        public void run() {
            while(true){
                try{
                    InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                    mainSocket = new Socket(serverAddr, SERVERPORT);
                } catch (UnknownHostException e) {
                    Log.d("Unknown Host: ", e.getMessage());
                    retryIn();
                } catch (ConnectException e) {
                    Log.d("ConnectException: ", e.getMessage());
                    closeSocket();
                    retryIn();
                } catch (IOException e) {
                    Log.d("IOException: ", e.getMessage());
                    retryIn();
                }
            }
        }

        /**
         * Close socket connection
         */
        private void closeSocket() {
            if(mainSocket != null){
                try {
                    mainSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Retry connection to the server
         */
        private void retryIn(){
            try {
                Thread.sleep(RETRY_IN);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
