import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;

public class BolaPantul extends JPanel implements ChangeListener, ActionListener{
    private BufferedImage image;
    private int x = 0;
    private int y = 450;
    private static int widthCanvas = 1050;
    private static int heightCanvas = 750;
    private int width = 160;
    private int height = 160;
    private JSlider sliderX;
    private JScrollBar scrollY;
    private boolean isGravityEnabled;
    private JTextField textField;
    private JButton tombolKiri, tombolKanan;
    private JToggleButton gravityToggleButton;
    private double velocityY = 0;
    private double velocityX = 0;
    private double acceleration = 10;
    private double elasticity = 0.8;
    private double gesekan = 0.98;
    private boolean systemChangingSlider;

    @Override
    public void stateChanged(ChangeEvent e) {
        x = sliderX.getValue();
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int topY = (int) (getHeight() * 0.05);
        g.drawLine(0, topY, getWidth(), topY);
        int bottomY = (int) (getHeight() * 0.95);
        g.drawLine(0, bottomY, getWidth(), bottomY);
        g.drawLine(1016,677,1016,35);

        // Size bola
        double scale = (double)y / heightCanvas;
        int scaledWidth = (int)(width * scale);
        int scaledHeight = (int)(height * scale);
        
        // Draw bola
        g.setColor(new Color(120, 255, 255));
        g.fillOval(x, y, scaledWidth, scaledHeight);

        // Titik tengah
        int centerX = (x) + (scaledWidth / 2);
        int centerY = y + (scaledHeight / 2);

        // Hitung radius
        double radius = scaledWidth / 2.0;

        // Hitung angle 
        double angle = (x % 360) * Math.PI / 180.0;

        // Garis dalam bola
        garis_dalam_bola(g, centerX, centerY, radius, angle, Color.BLUE);
        garis_dalam_bola(g, centerX, centerY, radius, angle + Math.PI / 2, Color.RED);
    }

    // Rotasi Bola
    private void garis_dalam_bola(Graphics g, int centerX, int centerY, double radius, double angle, Color color) {
        int x1 = centerX + (int) (radius * Math.cos(angle));
        int y1 = centerY + (int) (radius * Math.sin(angle));
        int x2 = centerX - (int) (radius * Math.cos(angle));
        int y2 = centerY - (int) (radius * Math.sin(angle));
        
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }
    
    public static  void main(String[] args) {
        JFrame frame = new JFrame("Bola GLBB");
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().add(new BolaPantul());
        frame.setSize(widthCanvas, heightCanvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
    }
}
