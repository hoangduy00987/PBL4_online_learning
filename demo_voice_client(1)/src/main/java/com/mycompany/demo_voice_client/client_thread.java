/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_voice_client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
public class client_thread extends Thread{
    public  TargetDataLine audio_in = null;
    
    public SourceDataLine audio_out;
    public DatagramSocket dout;
    byte buffer[] = new byte[60000];
    public InetAddress server_ip;
    public int server_port;
    @Override
    public void run(){
        System.out.print("Gui am thanh den server");
        while(Demo_voice_client.calling){
        try{
        //doc du lieu am thanh de luu vao buffer
        audio_in.read(buffer,0,buffer.length);
        // doi tuong packet chua du lieu am thanh dia chi ip va port
        DatagramPacket data = new DatagramPacket(buffer, buffer.length,server_ip,server_port);
        // gui den may chu
        dout.send(data);
        
         DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
         dout.receive(receivePacket);
       // byte[] receivedData = receivePacket.getData();
        //String receivedMessage = new String(receivedData, 0, receivePacket.getLength());
        //System.out.println("Received message: " + receivedMessage);
        buffer = receivePacket.getData();
        audio_out.write(buffer, 0, buffer.length);
        }catch(Exception ex){
           
        }
        }
        audio_in.close();
        // dam bao am thanh duoc gui di truoc khi dong
        audio_in.drain();
        System.out.print("Ket thuc");
    }
}