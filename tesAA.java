import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class tesAA extends JPanel {

    private BufferedImage image;

    public tesAA() {
        int width = 400;
        int height = 400;

        // Membuat BufferedImage dengan tipe BufferedImage.TYPE_INT_ARGB
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Mengaktifkan anti-aliasing
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Menggambar garis dengan warna hitam
        g2d.setColor(Color.BLACK);
        int topY = (int) (getHeight() * 0.05);
        g2d.drawLine(0, topY, getWidth(), topY);
        g2d.drawLine(50, 200, 350, 200);

        // Menggambar BufferedImage pada panel
        g2d.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Menggambar BufferedImage pada panel
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Anti-Aliasing Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new tesAA());
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
