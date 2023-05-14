import java.awt.Color;
import java.awt.Graphics;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JScrollBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Progres3 extends JPanel implements ChangeListener {

    private int x = 50;
    private int y = 250;
    private int diameter = 80;
    private static int widthCanvas = 1024;
    private static int heightCanvas = 768;

    private JSlider sliderX;
    private JScrollBar scrollY;

    public Progres3() {
        setLayout(null);

        // Inisialisasi slider
        sliderX = new JSlider(JSlider.HORIZONTAL, 0, 900, x);
        sliderX.addChangeListener(this);
        sliderX.setBounds(10, 670, 920, 50);
        add(sliderX);

        // Inisialisasi scrollbar
        scrollY = new JScrollBar(JScrollBar.VERTICAL, y, 0, 0, 600);
        scrollY.addAdjustmentListener(e -> {
            y = scrollY.getValue();
            repaint();
        });
        scrollY.setBounds(990, 0, 20, 700);
        add(scrollY);

        setOpaque(false);
    }

    
    public static  void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().add(new Progres3());
        frame.setSize(widthCanvas, heightCanvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(x, y, diameter, diameter);

        g.setColor(Color.BLACK);
        int topY = (int) (getHeight() * 0.05);
        g.drawLine(0, topY, getWidth(), topY);
        int bottomY = (int) (getHeight() * 0.95);
        g.drawLine(0, bottomY, getWidth(), bottomY);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        x = sliderX.getValue();
        repaint();
    }
}
