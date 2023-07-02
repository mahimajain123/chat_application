package com.june;


import com.june.client_handler;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server{
    Socket socket=null;
    DataInputStream in=null;
    DataOutputStream out=null;
    ServerSocket server=null;
    static Vector<client_handler> clients;
    int i=1;
    public Server(int port){
        clients = new Vector<>();
        try {
            server = new ServerSocket(port);
            System.out.println("Server Connected");
            System.out.println("Waiting for a client....");
            while (true) {
                socket = server.accept();
                System.out.println("Client " + i + " request is accepted.");
                out=new DataOutputStream(socket.getOutputStream());
                in=new DataInputStream(socket.getInputStream());
                out.writeUTF("Welcome");

                System.out.println(in.readUTF());
                System.out.println("Creating a new handler for this client...");
                socket.setKeepAlive(true);
                client_handler ch=new client_handler(socket,"Client"+i,in,out);
                Thread t=new Thread(ch);
                System.out.println("Adding client to active client list");
                clients.add(ch);
                t.start();
                i++;


            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Server serve=new Server(5000);
    }
}