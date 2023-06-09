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
    private JTextField textField;
    private JButton tombolKiri, tombolKanan;
    private JToggleButton gravityToggleButton;
    private double velocityY = 0;
    private double velocityX = 0;
    private double acceleration = 10;
    private double elasticity = 0.8;
    private double gesekan = 0.98;
    private boolean systemChangingSlider;

    public Progres4() {
        setLayout(null);
        gravityToggleButton = new JToggleButton("Gravity: OFF");
        gravityToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGravityEnabled = gravityToggleButton.isSelected();
                gravityToggleButton.setText(isGravityEnabled ? "Gravity: ON" : "Gravity: OFF");
            }
        });
        gravityToggleButton.setBounds(5, 5, 150, 25);
        add(gravityToggleButton);

        // Tombol kanan
        tombolKanan = new JButton(">>");
        tombolKanan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    velocityX = Integer.parseInt(textField.getText());
                } catch(NumberFormatException ex) {
                    velocityX = 0;
                }
                textField.setText("");
            }
        });
        tombolKanan.setBounds(554, 5, 50, 25);
        add(tombolKanan);

        // Tombol kiri
        tombolKiri = new JButton("<<");
        tombolKiri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    velocityX = -Integer.parseInt(textField.getText());
                } catch(NumberFormatException ex) {
                    velocityX = 0;
                }
                textField.setText("");
            }
        });
        tombolKiri.setBounds(415, 5, 50, 25);
        add(tombolKiri);

        // Input box
        textField = new JTextField();
        textField.setBounds(470, 5, 80, 26);
        add(textField);

        // Slider
        sliderX = new JSlider(JSlider.HORIZONTAL, 0, 896, x);
        sliderX.addChangeListener(e -> {
            if (!systemChangingSlider) {
                x = sliderX.getValue();
                repaint();
            }
        });
        sliderX.setBounds(52, 678, 912, 20);
        sliderX.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isGravityEnabled = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (gravityToggleButton.isSelected()) {
                    isGravityEnabled = true;
                }
            }
        });
        add(sliderX);

        // Scrollbar
        scrollY = new JScrollBar(JScrollBar.VERTICAL, y, 50, 36, 607);
        scrollY.addAdjustmentListener(e -> {
            y = scrollY.getValue();
            repaint();
        });
        scrollY.addAdjustmentListener(e -> {
            if (!systemChangingSlider) {
                y = scrollY.getValue();
                repaint();
            }
        });
        scrollY.setBounds(1017, 54, 20, 602);
        scrollY.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isGravityEnabled = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (gravityToggleButton.isSelected()) {
                    isGravityEnabled = true;
                }
            }
        });
        add(scrollY);

        setOpaque(false);

        new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                velocityX *= gesekan;
                if (Math.abs(velocityX) < 0.1) {
                    velocityX = 0;
                }

                x += velocityX;
                if (x < 0.1) {
                    velocityX = -elasticity * velocityX;
                    x = 0;
                }
                if (x + width > widthCanvas + 10) {
                    velocityX = -elasticity * velocityX;
                    x = (widthCanvas + 10) - width;
                }
                sliderX.setValue(x);
                scrollY.setValue(y);

                if (isGravityEnabled) {
                    velocityY += acceleration;
                    y += velocityY;
                    if (y + height > heightCanvas - 32) {
                        velocityY = -elasticity * velocityY;
                        y = (heightCanvas - 32) - height;
                    }
                    // Sistem yang merubah slider agar tidak bertabrakan sama kondisi jika slider di tekan)
                    systemChangingSlider = true;
                    sliderX.setValue(x);
                    scrollY.setValue(y);
                    systemChangingSlider = false;
                }
                repaint();
            }
        }).start();
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int topY = (int) (getHeight() * 0.05);
        g.drawLine(0, topY, getWidth(), topY);
        int bottomY = (int) (getHeight() * 0.95);
        g.drawLine(0, bottomY, getWidth(), bottomY);
        g.drawLine(1016, 677, 1016, 35);

        // Size bola
        double scale = (double) y / heightCanvas;
        int scaledWidth = (int) (width * scale);
        int scaledHeight = (int) (height * scale);

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

    @Override
    public void stateChanged(ChangeEvent e) {
        x = sliderX.getValue();
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bola GLBB");
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().add(new Progres4());
        frame.setSize(widthCanvas, heightCanvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
