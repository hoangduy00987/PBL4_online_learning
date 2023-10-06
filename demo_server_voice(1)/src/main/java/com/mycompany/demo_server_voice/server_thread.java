/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_server_voice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author ASUS-PRO
 */
public class server_thread extends Thread{
    public DatagramSocket din;
    public SourceDataLine audio_out;
    byte[] buffer = new byte[60000];
    @Override
    public void run(){
        String message = "ALO NHAN DUOC TIN NHAN CHUA";
        byte[] messageBytes = message.getBytes();
        DatagramPacket data = new DatagramPacket(buffer, buffer.length);
        while(Demo_server_voice.call){
            
            try {
                din.receive(data);
                buffer = data.getData();
                //audio_out.write(buffer, 0, buffer.length);
                InetAddress clientAddress = data.getAddress();
                String clientIP = clientAddress.getHostAddress();
                int clientPort = data.getPort();
                System.out.println("Địa chỉ IP của client: " + clientIP);
                 DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
                din.send(sendPacket);
               DatagramPacket messagePacket = new DatagramPacket(messageBytes, messageBytes.length, clientAddress, clientPort);
                din.send(messagePacket);
                } catch (IOException ex) {
                Logger.getLogger(server_thread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
        audio_out.close();
        audio_out.drain();
        System.out.print("stop");
               
    }
}