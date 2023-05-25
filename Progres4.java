import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Progres4 extends JPanel implements ChangeListener, ActionListener {
    private int x = 0;
    private int y = 450;
    private static int widthCanvas = 1050;
    private static int heightCanvas = 750;
    private int width = 160;
    private int height = 160;
    private JSlider sliderX;
    private JScrollBar scrollY;
    private boolean isGravityEnabled;
    private JToggleButton gravityToggleButton;

    public Progres4() {
        setLayout(null);
        gravityToggleButton = new JToggleButton("Gravity: ON");
        gravityToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGravityEnabled = gravityToggleButton.isSelected();
                gravityToggleButton.setText(isGravityEnabled ? "Gravity: ON" : "Gravity: OFF");
            }
        });
        gravityToggleButton.setBounds(5, 5, 150, 25);
        add(gravityToggleButton);

        // Slider
        sliderX = new JSlider(JSlider.HORIZONTAL,0,896,x);
        sliderX.addChangeListener(this);
        sliderX.setBounds(52, 678, 912, 20);
        add(sliderX);

        // Scrollbar
        scrollY = new JScrollBar(JScrollBar.VERTICAL, y, 50, 36, 607);
        scrollY.addAdjustmentListener(e -> {
            y = scrollY.getValue();
            repaint();
        });
        scrollY.setBounds(1017, 54, 20, 602);
        add(scrollY);

        setOpaque(false);
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

        // Size
        double scale = (double)y / heightCanvas;
        int scaledWidth = (int)(width * scale);
        int scaledHeight = (int)(height * scale);
        
        //Draw Circle
        g.drawOval(x, y, scaledWidth, scaledHeight);
        g.setColor(Color.BLACK);

        // Hitung titik tengah lingkaran
        int centerX = x + (scaledWidth / 2);
        int centerY = y + (scaledHeight / 2);

        // Hitung panjang garis silang
        double lineLength = Math.sqrt(2) * (scaledWidth / 2);

        // Hitung titik-titik ujung garis silang
        int lineX1 = centerX - (int) (lineLength / 2);
        int lineY1 = centerY - (int) (lineLength / 2);
        int lineX2 = centerX + (int) (lineLength / 2);
        int lineY2 = centerY + (int) (lineLength / 2);

        // Gambar garis silang
        g.setColor(Color.BLUE);
        g.drawLine(lineX1, lineY1, lineX2, lineY2);
        g.setColor(Color.RED);
        g.drawLine(lineX2, lineY1, lineX1, lineY2);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        x = sliderX.getValue();
        repaint();
    }

    public static  void main(String[] args) {
        JFrame frame = new JFrame("Bola GLBB");
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().add(new Progres4());
        frame.setSize(widthCanvas, heightCanvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}