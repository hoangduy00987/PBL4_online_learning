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
    byte buffer_receive[] = new byte[44100];
    byte buffer_send[] = new byte[44100];
   // byte buffer_send[] = new byte[44100];
    @Override
    public void run(){
        while(Demo_server_voice.call){
            try {
                DatagramPacket data = new DatagramPacket(buffer_receive, buffer_receive.length);
                din.receive(data);
                buffer_receive = data.getData();
                //audio_out.write(buffer_receive, 0, buffer_receive.length);
                InetAddress clientAddress = data.getAddress();
                String clientIP = clientAddress.getHostAddress();
                int clientPort = data.getPort();
               // if (!printedClientIP) {
                System.out.println("From  client: " + clientIP);
                //printedClientIP = true;
                //}
                System.arraycopy(buffer_receive, 0, buffer_send, 0, buffer_receive.length);
                DatagramPacket sendPacket = new DatagramPacket(buffer_send, buffer_send.length, clientAddress, clientPort);
                din.send(sendPacket);
                } catch (IOException ex) {
                Logger.getLogger(server_thread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
        audio_out.close();
        audio_out.drain();
        System.out.print("stop");
               
    }
}