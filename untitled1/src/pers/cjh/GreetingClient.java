package pers.cjh;

import java.net.*;
import java.io.*;

public class GreetingClient {

    private Socket client = null;
    private String serverName = null;
    private int port = 0;
    DataInputStream in = null;
    DataOutputStream out = null;

    public void setServer(String name, int port) {
        serverName = name;
        this.port = port;
    }

    public boolean connect(){
        if(client != null){
            return true;
        }

        try {
            System.out.println("connecting to "+serverName+", port is "+port);
            client = new Socket(serverName, port);
            System.out.println("server address is " + client.getRemoteSocketAddress());
            System.out.println("client address is " + client.getLocalSocketAddress());
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return client.isConnected();
        }
    }
    public void disconnect(){
        if(client!=null) {
            try {
                in.close();
                out.close();
                client.close();
                System.out.println("disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
            client = null;
        }
    }
    public void sendTo(String content){
        if(client!=null){
            try {
                out.write(content.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String recvFrom(){
        if(client!=null){
            try {
                while(true) {
                    byte []buff = new byte[128];
                    int len = in.read(buff);
                    return (new String(buff, 0, len));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void test(){
        try {
            Socket client = new Socket("127.0.0.1", 7000);
            //client.setSoTimeout(10000);
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            while(true) {
                out.write("Hello!\r\n".getBytes());
                byte[] data = new byte[128];
                int len = in.read(data);
                String str = new String(data,0,len);

                if((str.compareTo("Q\r\n")==0)||(str.compareTo("q\r\n")==0))break;

                for(int i=0; i<len; i++){
                    System.out.printf("%c", data[i]);
                }
            }
            in.close();
            out.close();
            client.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("quit");
    }
}
