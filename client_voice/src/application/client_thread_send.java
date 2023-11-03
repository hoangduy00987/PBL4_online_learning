package application;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.TargetDataLine;
public class client_thread_send extends Thread{
    public  TargetDataLine audio_in = null;
    public DatagramSocket din;
    byte buffer_send[] = new byte[44100];
    public InetAddress server_ip;
    public int server_port;
    @Override
    public void run(){
        System.out.print("Gui am thanh den server");
        while(Main.Calling){
        try{
        //doc du lieu am thanh de luu vao buffer
        audio_in.read(buffer_send ,0,buffer_send.length);
        // doi tuong packet chua du lieu am thanh dia chi ip va port
        DatagramPacket data = new DatagramPacket(buffer_send, buffer_send.length,server_ip,server_port);
        // gui den may chu
        din.send(data);
        }catch(IOException ex){
            Logger.getLogger(client_thread_send.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        audio_in.close();
        // dam bao am thanh duoc gui di truoc khi dong
        audio_in.drain();
        System.out.print("Ket thuc");
    }
}  