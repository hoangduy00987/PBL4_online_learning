package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.sound.sampled.SourceDataLine;

public class client_thread_receive extends Thread{
    public DatagramSocket dout;
    public SourceDataLine audio_out;
    byte buffer_receive[] = new byte[44100];
    public InetAddress server_ip;
    public int server_port;
   @Override
public void run() {
    System.out.println("Nhan am thanh tu server\n");
    while (Main.Calling) {
        try {
            // Nhận dữ liệu từ server
            DatagramPacket receivePacket = new DatagramPacket(buffer_receive, buffer_receive.length);
            dout.receive(receivePacket);
            InetAddress clientAddress = receivePacket.getAddress();
            InetAddress localhost = InetAddress.getLocalHost();
            int clientPort = receivePacket.getPort();
            buffer_receive = receivePacket.getData();
            // Ghi dữ liệu âm thanh nhận được vào audio_out để phát ra loa
            if (!clientAddress.getHostAddress().equals(localhost.getHostAddress())) {
                audio_out.write(buffer_receive, 0, buffer_receive.length);
            }
        } catch (IOException ex) {
        }
    }
    audio_out.close();
    // Đảm bảo âm thanh đã được gửi trước khi đóng
    audio_out.drain();
    System.out.println("Kết thúc");
}
}
