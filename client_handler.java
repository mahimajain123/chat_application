package com.june;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class client_handler implements Runnable{
    DataOutputStream out;
    DataInputStream in;
    Socket socket;
    boolean isloggedin;
    String name;
    public client_handler(Socket socket,String name, DataInputStream in , DataOutputStream out) throws IOException {

        this.in = in;
        this.out = out;
        this.socket = socket;
        this.name=name;
        this.isloggedin = true;
       sqlclass sq=new sqlclass();
        String vals="'"+this.name+"'";
        sq.insertt("clients","client_name",vals);


    }
    public void printSocketInformation(Socket socket){
        try
        {
            System.out.format("Port:                 %s\n",   socket.getPort());
            System.out.format("Canonical Host Name:  %s\n",   socket.getInetAddress().getCanonicalHostName());
            System.out.format("Host Address:         %s\n\n", socket.getInetAddress().getHostAddress());
            System.out.format("Local Address:        %s\n",   socket.getLocalAddress());
            System.out.format("Local Port:           %s\n",   socket.getLocalPort());
            System.out.format("Local Socket Address: %s\n\n", socket.getLocalSocketAddress());
            System.out.format("Receive Buffer Size:  %s\n",   socket.getReceiveBufferSize());
            System.out.format("Send Buffer Size:     %s\n\n", socket.getSendBufferSize());
            System.out.format("Keep-Alive:           %s\n",   socket.getKeepAlive());
            System.out.format("SO Timeout:           %s\n",   socket.getSoTimeout());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try {
            String line = "";
            System.out.println(this.name+" starting.");
            while(true){
                line=in.readUTF();
//                printSocketInformation(this.socket);
         //       System.out.println(line);
                if(line.contains("Over")){
                    this.isloggedin=false;
                    this.socket.close();
                    break;
                }

                StringTokenizer st=new StringTokenizer(line,"#");
                String msgToSend=st.nextToken();
                String recipient=st.nextToken();
//                this.out.writeUTF(msgToSend);
                for(client_handler search: Server.clients){
//                    System.out.println(search.name);
//                    search.out.writeUTF("Welcome");
                    if(search.name.equals(recipient)&& search.isloggedin){
                       System.out.println("Found "+search.name);
                        search.out.writeUTF(this.name+" : "+msgToSend);

                        String vals=search.name+","+this.name+","+msgToSend;
                        sqlclass sq2=new sqlclass();
                        sq2.insertt("chattable","chat_to,chat_from,chat",vals);
                        break;
                    }
                }
            }

        }
        catch(IOException e){
            System.out.println(e);
        }
        try{
            this.in.close();
            this.out.close();

        }
        catch(IOException e){
            System.out.println(e);
        }

    }

}
