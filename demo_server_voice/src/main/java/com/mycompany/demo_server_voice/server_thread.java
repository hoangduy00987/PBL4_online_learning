/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_server_voice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
    byte[] buffer = new byte[512];
    @Override
    public void run(){
        
        DatagramPacket data = new DatagramPacket(buffer, buffer.length);
        while(Demo_server_voice.call){
            
            try {
                din.receive(data);
                buffer = data.getData();
                audio_out.write(buffer, 0, buffer.length);
            } catch (IOException ex) {
                Logger.getLogger(server_thread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        audio_out.close();
        audio_out.drain();
        System.out.print("stop");
               
    }
}
