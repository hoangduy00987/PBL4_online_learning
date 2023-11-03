package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import java.net.DatagramSocket;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
public class serverFormController {
	@FXML
		private Button start;
		public  int port = 8888;
		public SourceDataLine audio_out;
	    public static AudioFormat getaudioFrmat(){
	    float sampleRate =  44100.0F;// tan so lay mau am thanh
	    int sampleSizeInbits = 16;//so bit cho moi mau
	    int channel = 2;// Số kênh âm thanh (2 cho stereo)
	    boolean signed = true;// Dấu (true cho âm thanh có dấu)
	    boolean bigEdian = false;// Dữ liệu lưu trữ theo đúng thứ tự (false cho little-endian)

	    return new AudioFormat(sampleRate, sampleSizeInbits, channel, signed, bigEdian);
	    
	}
	    public void init_audio()  {
	    try {
	        // Khởi tạo định dạng âm thanh
	        AudioFormat format = getaudioFrmat();
	        
	        // Tạo thông tin dòng đầu ra
	        DataLine.Info info_out = new DataLine.Info(SourceDataLine.class, format);
	        
	        // Kiểm tra xem hệ thống có hỗ trợ định dạng âm thanh này không
	        if (!AudioSystem.isLineSupported(info_out)) {
	            System.out.println("Không hỗ trợ định dạng âm thanh này");
	            System.exit(0);
	        }
	        
	        // Tạo và cấu hình SourceDataLine
	        audio_out = (SourceDataLine) AudioSystem.getLine(info_out);
	        audio_out.open(format);
	        audio_out.start();
	        
	        // Khởi tạo và bắt đầu luồng server_thread
	        server_thread thr = new server_thread();
	        thr.din = new DatagramSocket(port);
	        thr.audio_out = audio_out;
	        Main.Calling = true;
	        thr.start();
	        
	        start.setVisible(false);
	    } catch (Exception e) {
	        
	        
	    }
	}
	
	@FXML
	public void btn_start(ActionEvent event) {
		init_audio();
	}
	
}
