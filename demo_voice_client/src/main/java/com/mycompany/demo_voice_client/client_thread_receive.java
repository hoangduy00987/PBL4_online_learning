/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo_voice_client;

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
public class client_thread_receive extends Thread{
    public DatagramSocket dout;
    public SourceDataLine audio_out;
    byte buffer_receive[] = new byte[44100];
    public InetAddress server_ip;
    public int server_port;
   @Override
public void run() {
    System.out.println("Nhan am thanh tu server\n");
    while (Demo_voice_client.calling) {
        try {
            // Nhận dữ liệu từ server
            DatagramPacket receivePacket = new DatagramPacket(buffer_receive, buffer_receive.length);
            dout.receive(receivePacket);

            buffer_receive = receivePacket.getData();
            // Ghi dữ liệu âm thanh nhận được vào audio_out để phát ra loa
            audio_out.write(buffer_receive, 0, buffer_receive.length);
        } catch (IOException ex) {
            Logger.getLogger(client_thread_send.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    audio_out.close();
    // Đảm bảo âm thanh đã được gửi trước khi đóng
    audio_out.drain();
    System.out.println("Kết thúc");
}
}
