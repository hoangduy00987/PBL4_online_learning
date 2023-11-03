package application;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.UIManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class clientFormController {
    public int port_sv = 8888;
    public String address_sv = "192.168.1.178";
    TargetDataLine audio_in;
    SourceDataLine audio_out;

    public static AudioFormat getaudioFrmat() {
    	List<client_thread_receive> receiveThreads = new ArrayList<>();
        float sampleRate = 44100.0F; // Tần số lấy mẫu âm thanh
        int sampleSizeInbits = 16; // Số bit cho mỗi mẫu
        int channel = 2; // Số kênh âm thanh (2 cho stereo)
        boolean signed = true; // Dấu (true cho âm thanh có dấu)
        boolean bigEdian = false; // Dữ liệu lưu trữ theo đúng thứ tự 

        return new AudioFormat(sampleRate, sampleSizeInbits, channel, signed, bigEdian);
    }

    public void init_audio() {
        try {
            // Khởi tạo định dạng âm thanh
            AudioFormat format = getaudioFrmat();

            // Tạo thông tin cho dòng đầu vào âm thanh (TargetDataLine)
            DataLine.Info infor = new DataLine.Info(TargetDataLine.class, format);

            // Kiểm tra xem hệ thống có hỗ trợ định dạng âm thanh được chỉ định hay không
            if (!AudioSystem.isLineSupported(infor)) {
                System.out.println("Không hỗ trợ định dạng âm thanh này");
                return;
            }

            // Tạo và cấu hình dòng đầu vào âm thanh (TargetDataLine)
            audio_in = (TargetDataLine) AudioSystem.getLine(infor);
            audio_in.open(format);
            audio_in.start();

            // Tạo và cấu hình dòng đầu ra âm thanh (SourceDataLine)
            DataLine.Info outInfo = new DataLine.Info(SourceDataLine.class, format);
            if (!AudioSystem.isLineSupported(outInfo)) {
                System.out.println("Không hỗ trợ định dạng âm thanh đầu ra này");
                return;
            }
            audio_out = (SourceDataLine) AudioSystem.getLine(outInfo);
            audio_out.open(format);
            audio_out.start();

            // Cấu hình thông tin cho client_thread_send (Thread gửi dữ liệu âm thanh)
            client_thread_send thr_s = new client_thread_send();
            InetAddress ip = InetAddress.getByName(address_sv);
            thr_s.audio_in = audio_in;
            thr_s.din = new DatagramSocket();
            thr_s.server_ip = ip;
            thr_s.server_port = port_sv;

            // Cấu hình thông tin cho client__thread_receive (Thread nhận dữ liệu âm thanh)
            client_thread_receive thr_rc = new client_thread_receive();
            thr_rc.audio_out = audio_out;
            thr_rc.dout = thr_s.din; // Sử dụng cùng một DatagramSocket
            thr_rc.server_ip = ip;
            thr_rc.server_port = port_sv;
            Main.Calling = true;
            thr_s.start();
            thr_rc.start();
            btn_start.setVisible(false);
            
        } catch (LineUnavailableException | SocketException | UnknownHostException ex) {

        }
    }

    @FXML
    private Button btn_start;

    @FXML
    public void btn_Clickedstart(ActionEvent event) {
        init_audio();
    }
   
}