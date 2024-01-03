package Controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MouseDrawingThread extends Thread {

    private Canvas canvas;
    private GraphicsContext gc;

    public MouseDrawingThread(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    @Override
    public void run() {
        // Logic vẽ lại vị trí của chuột
        // ...
    }

    public void drawMousePointer(MouseEvent event) {
        // Xóa trạng thái trước đó
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Vẽ mũi tên chuột tại vị trí mới
        double x = event.getX();
        double y = event.getY();
        double size = 10;

        gc.setStroke(Color.RED); // Màu đỏ cho mũi tên chuột
        gc.setLineWidth(2);
        gc.strokeLine(x - size, y, x + size, y);
        gc.strokeLine(x, y - size, x, y + size);
    }
}
