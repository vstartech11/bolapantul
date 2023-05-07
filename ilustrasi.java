
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ilustrasi extends JPanel {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new ilustrasi());
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 453;
        int width = 80;
        int height = 80;
        g.drawOval(x, y, width, height);
        g.setColor(Color.RED);

        // Hitung titik tengah lingkaran
        int centerX = x + (width / 2);
        int centerY = y + (height / 2);

        // Hitung panjang garis silang
        double lineLength = Math.sqrt(2) * (width / 2);

        // Hitung titik-titik ujung garis silang
        int lineX1 = centerX - (int) (lineLength / 2);
        int lineY1 = centerY - (int) (lineLength / 2);
        int lineX2 = centerX + (int) (lineLength / 2);
        int lineY2 = centerY + (int) (lineLength / 2);

        // Gambar batas atas dan batas bawah
        g.setColor(Color.BLACK);
        int topY = (int) (getHeight() * 0.05);
        g.drawLine(0, topY, x * width, topY);
        int bottomY = (int) (getHeight() * 0.95);
        g.drawLine(0, bottomY, x * width, bottomY);

        // Gambar garis silang
        g.setColor(Color.BLUE);
        g.drawLine(lineX1, lineY1, lineX2, lineY2);
        g.setColor(Color.RED);
        g.drawLine(lineX2, lineY1, lineX1, lineY2);
    }
}
