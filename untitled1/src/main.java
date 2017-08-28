import pers.cjh.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import java.sql.*;


public class main {
    public static void rc5Test(){
        short []src = {(short)0xB1D2, (short)0x52B2, (short)0x1A02, (short)0xA232};

        short []enc = new short[8];
        short []dec = new short[8];
        short []keys = new short[26];

        for(short i=0; i<keys.length; i++){
            keys[i] = 0;
        }

        System.out.println("legths: "+src.length+" "+enc.length+" "+dec.length+" "+keys.length);
        rc5.encipher(src,4, enc, keys);
        rc5.decipher(src,4, dec, keys);
        System.out.printf("src: %04x,%04x,%04x,%04x\n",src[0],src[1],src[2],src[3]);
        System.out.printf("enc: %04x,%04x,%04x,%04x\n",enc[0],enc[1],enc[2],enc[3]);
        System.out.printf("dec: %04x,%04x,%04x,%04x\n",dec[0],dec[1],dec[2],dec[3]);
    }

    public static void forFor(){
        int []number = {1,2,3,4,5,6,7,78,8,9,90,0,};
        System.out.println(number.length);
        for(int x:number){
            System.out.println(x);
        }
    }

    public static void forScanner(){
        Scanner s = new Scanner(System.in);
        System.out.println("input an integer: ");
        try {
            int x = s.nextInt();
            System.out.println("it is :" + x);
        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("input string: ");
        try {
            String str = s.next();
            System.out.println("it is :" + str);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void forNumber(){
        Integer i = 360;
        System.out.println(i.floatValue());
        System.out.println(i.toString());
        System.out.println(i.byteValue());
        System.out.println(i.compareTo(360));
        System.out.println(i.equals(360));
        System.out.println(Integer.valueOf(100));
        System.out.println(Math.random());
    }

    public static void forBleProt(){
        Scanner s = new Scanner(System.in);
        System.out.println("prefix + length + control(sn|dir) + objId(sn|len) + objDat + suffix");
        System.out.println("ctrl->dir:");
        System.out.println("0: SET");
        System.out.println("1: GET");
        System.out.println("2: RPT");
        System.out.println("3: ACK");
        byte dir = s.nextByte();
        System.out.println("obj->id:");
        byte objId = s.nextByte();
        System.out.println("obj->len idx:");
        System.out.println("0: 1");
        System.out.println("1: 2");
        System.out.println("2: 4");
        System.out.println("3: 8");
        System.out.println("4: 16");
        System.out.println("5: 32");
        System.out.println("6: 64");
        System.out.println("7: 1");
        byte lenIdx = s.nextByte();
        System.out.println("obj->dats:");
        byte len = (lenIdx==7)?1:(byte)Math.pow(2.0, (float)lenIdx);
        byte []datas = new byte[len];
        for(byte i=0; i<len; i++){
            datas[i] = s.nextByte();
        }
        byte []pktBuff = new byte[len+16];

        int index = 0;
        pktBuff[index++] = 0x58;
        pktBuff[index++] = (byte)(len+3);
        pktBuff[index++] = dir;
        pktBuff[index++] = (byte)(((objId<<3) | (lenIdx&0x7))&0xff);
        for(byte i=0; i<len; i++){
            pktBuff[index++] = datas[i];
        }
        pktBuff[index++] = 0;//chksum
        pktBuff[index++] = 0x4d;
        byte chksum = 0;
        for(byte i=1; i<index-1; i++) {
            chksum += pktBuff[i];
        }
        pktBuff[index-2] = chksum;

        for(byte i=0; i<index; i++) {
            System.out.printf("%02X ",pktBuff[i]);
        }
        System.out.println("");
    }

    private static int byte2Hex(byte c){
        if((c>=(byte)'0')&&(c<=(byte)'9')){
            return (c -'0');
        }
        if((c>=(byte)'a')&&(c<=(byte)'f')){
            return (c -'a');
        }
        if((c>=(byte)'A')&&(c<=(byte)'F')){
            return (c -'A');
        }
        return 0;
    }
    public static void forBleProtObjId(){
        Scanner s = new Scanner(System.in);
        System.out.println("obj(HEX):");
        String obj_str = s.next();
        byte []obj_bytes = obj_str.getBytes();
        try {
            int obj_id = (byte2Hex(obj_bytes[0]) << 4) | byte2Hex(obj_bytes[1]);
            System.out.println("obj(DEC):" + obj_id + " id:" + (obj_id >> 3) + " len(idx:" + (obj_id & 0x7) +"):"+(short)Math.pow(2,(obj_id & 0x7)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void forMap(){
        Map<String, String> map = new HashMap<String, String>();
        map.clear();
        map.put("k1", "value1");
        map.put("k2", "value2");
        map.put("k3", "value3");
        map.put("k4", "value4");
        map.put("k5", "value5");
        for(String str:map.keySet()){
            System.out.println(str);
        }
        Iterator<Map.Entry<String,String>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, String> entry = it.next();
            System.out.println("[key:val] = "+entry.getKey() +" : "+entry.getValue());
        }
    }

    public static void forSocectClient(){
        GreetingClient client = new GreetingClient();
        client.setServer("127.0.0.1", 7000);
        client.connect();
        client.sendTo("Hello, this is client!\r\n");
        String recv = client.recvFrom();
        System.out.println(recv);
        client.disconnect();
    }
    public static void forRunnableTest(){
        RunnableTest rt = new RunnableTest();
        Thread t = new Thread(rt);
        t.start();
    }
    public static void forThreadTest(){
        /*RunnableTest rt = new RunnableTest();
        ThreadTest tt = new ThreadTest(rt);
        tt.start();*/
        ThreadTest tt = new ThreadTest();
        tt.start();
    }

    public static void forMySql(){
        MySqlTest sql = new MySqlTest();
        sql.connect("test1", "root", "1234");
        System.out.println("ori: ");
        sql.select("mytable1", "*");

        sql.insert("mytable1", "name,date", "\"heshen2\",\"2017-08-29\"");
        sql.update("mytable1", "name", "\"jianhao\"", "id=2");

        System.out.println("after insert & update: ");
        sql.select("mytable1", "*");

        sql.delete("mytable1", "name=\"heshen2\"");
        System.out.println("after delete: ");
        sql.select("mytable1", "*");
        sql.disconnect();
    }

    public static void main(String args[]){
        //rc5Test();
        //forFor();
        //forScanner();
        //forNumber();
        //forBleProt();
        //while(true)forBleProtObjId();
        //forMap();
/*        byte []b = {1,2,3,4};
        short []s = {0,0};
        rc5.expand_keys(b,s);
        System.out.printf("{%02x,%02x,%02x,%02x}->{%04x,%04x}\r\n", b[0], b[1], b[2], b[3] , s[0],s[1]);*/
        //forSocectClient();
        //GreetingClient client = new GreetingClient();
        //client.test();
        //forRunnableTest();
        //forThreadTest();
        forMySql();
    }
}
