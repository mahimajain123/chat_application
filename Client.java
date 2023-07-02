package com.june;

import java.io.*;
import java.net.*;

public class Client{
    private Socket socket=null;
    private DataInputStream keyboard=null;
    private DataInputStream server=null;
    private DataOutputStream client=null;
    public Client(String address,int port) throws IOException {
        try {
            socket = new Socket(address, port);
            System.out.println("Client connected");
            keyboard = new DataInputStream(System.in);
            server = new DataInputStream(socket.getInputStream());
            client = new DataOutputStream(socket.getOutputStream());
            client.writeUTF("Thanks");
        }
        catch(IOException i){
            System.out.println(i);
        }
        startcon();
    }
    public void startcon() throws IOException {
        System.out.println(server.readUTF());
        Thread t1=new Thread(()->{

            try {
                String line = "";
                while (!line.contains("Over")) {
                    line = keyboard.readLine();
                    client.writeUTF(line);
                }
            }
            catch(IOException i){
                System.out.println(i);
            }
        });
        t1.start();
        Thread t2=new Thread(()->{

            try {
                String line = "";
                while (!line.contains("Over")) {
                    line = server.readUTF();
                    System.out.println(line);
                }
            }
            catch(IOException i){
                System.out.println(i);
            }
        });
        t2.start();
    }

    public static void main(String[] args) {
        try {
            Client cli=new Client("127.0.0.1",5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}