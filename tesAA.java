import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;

public class tesAA extends JPanel implements ChangeListener, ActionListener {
    private BufferedImage buffer;
    private int scaleFactor = 2; // faktor skala supersampling
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

        // Buat buffer gambar
        int bufferWidth = getWidth() * scaleFactor;
        int bufferHeight = getHeight() * scaleFactor;
        buffer = new BufferedImage(bufferWidth, bufferHeight, BufferedImage.TYPE_INT_RGB);

        // Render dengan metode supersampling
        render();

        // Gambar buffer gambar ke komponen
        g.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);

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
        g.setColor(new Color(82, 82, 216));
        gambarBola(g, x, y, scaledWidth, scaledHeight);

        // Titik tengah
        int centerX = (x) + (scaledWidth / 2);
        int centerY = y + (scaledHeight / 2);

        // Hitung radius
        double radius = scaledWidth / 2.0;

        // Hitung angle 
        double angle = (x % 360) * 3.141592653589793 / 180.0;

        // Garis dalam bola
        garis_dalam_bola(g, centerX, centerY, radius, angle, Color.BLUE);
        garis_dalam_bola(g, centerX, centerY, radius, angle + 3.141592653589793 / 2, Color.RED);
    }

    private void render() {
        int bufferWidth = buffer.getWidth();
        int bufferHeight = buffer.getHeight();

        for (int y = 0; y < bufferHeight; y++) {
            for (int x = 0; x < bufferWidth; x++) {
                float red = 0, green = 0, blue = 0;

                // Supersampling
                for (int i = 0; i < scaleFactor; i++) {
                    for (int j = 0; j < scaleFactor; j++) {
                        float subpixelX = x + (i + 0.5f) / scaleFactor;
                        float subpixelY = y + (j + 0.5f) / scaleFactor;

                        // Hitung warna subpixel
                        float[] color = calculatePixelColor(subpixelX, subpixelY);
                        red += color[0];
                        green += color[1];
                        blue += color[2];
                    }
                }

                // Rata-ratakan warna dari subpixel
                red /= (scaleFactor * scaleFactor);
                green /= (scaleFactor * scaleFactor);
                blue /= (scaleFactor * scaleFactor);

                // Set warna piksel pada buffer gambar
                int rgb = new Color(red, green, blue).getRGB();
                buffer.setRGB(x, y, rgb);
            }
        }
    }

    private float[] calculatePixelColor(float x, float y) {
        // Size bola
        double scale = (double) y / heightCanvas;
        int scaledWidth = (int) (width * scale);
        int scaledHeight = (int) (height * scale);

        // Periksa apakah titik berada di dalam bola
        int centerX = this.x + (scaledWidth / 2);
        int centerY = this.y + (scaledHeight / 2);
        double distance = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
        if (distance <= scaledWidth / 2) {
            return new float[]{0, 0, 1}; // Warna biru untuk titik di dalam bola
        } else {
            return new float[]{1, 1, 1}; // Warna putih untuk titik di luar bola
        }
    }

   private void gambarBola(Graphics g,int x, int y,int width,int height){
        int centerX = x + (width/2);
        int centerY = y + (height/2);
        int radius = width/2;

        for (int i = 0; i < 360; i++) {
        double angle = i * 3.141592653589793 / 180.0;
        int x1 = (int) (centerX + radius * cosine(angle));
        int y1 = (int) (centerY + radius * sine(angle));
        int x2 = (int) (centerX + (radius - 1) * cosine(angle));
        int y2 = (int) (centerY + (radius - 1) * sine(angle));
        g.drawLine(x1, y1, x2, y2);
    }
    }

    // Rotasi Bola
    private void garis_dalam_bola(Graphics g, int centerX, int centerY, double radius, double angle, Color color) {
        int x1 = centerX + (int) (radius * cosine(angle));
        int y1 = centerY + (int) (radius * sine(angle));
        int x2 = centerX - (int) (radius * cosine(angle));
        int y2 = centerY - (int) (radius * sine(angle));
        
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }


    public tesAA(){
        gravitasi();
        tombolKiri();
        tombolInput();
        tombolKanan();
        tombolSlider();
        tombolScrollBar();
        tombolGravitasi();
        
    }
    // Menghitung cos menggunakan deret Taylor
    private static double cosine(double angle) {
        double cos = 1.0;
        double term = 1.0;
        double num = 1.0;
        double den = 1.0;
        int sign = -1;

    for (int i = 2; term > 1e-10; i += 2) {
        num *= angle * angle;
        den *= (i - 1) * i;
        term = num / den;
        cos += sign * term;
        sign *= -1;
    }

    return cos;
}

// Menghitung sin menggunakan deret Taylor
    private static double sine(double angle) {
        double sin = angle;
        double term = angle;
        double num = angle;
        double den = 1.0;
        int sign = -1;

    for (int i = 3; term > 1e-10; i += 2) {
        num *= angle * angle;
        den *= (i - 1) * i;
        term = num / den;
        sin += sign * term;
        sign *= -1;
    }

    return sin;
}
    private void tombolGravitasi(){
        
        setLayout(null);
        gravityToggleButton = new JToggleButton("Gravity: OFF");
        gravityToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGravityEnabled = gravityToggleButton.isSelected();
                gravityToggleButton.setText(isGravityEnabled ? "Gravity: ON" : "Gravity: OFF");
            }
        });
        gravityToggleButton.setBounds(800, 5, 150, 25);
        add(gravityToggleButton);
    }
    private void tombolKanan() {
        JButton tombolKanan = new JButton(">>");
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
    }
    private void tombolInput(){
        textField = new JTextField();
        textField.setBounds(470, 5, 80, 26);
        add(textField);
    }
    private void tombolKiri() {
        JButton tombolKiri = new JButton("<<");
        tombolKiri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    velocityX = -Integer.parseInt(textField.getText());
                } catch(NumberFormatException ex) {
                    velocityX = 0;
                }
                textField.setText("0");
            }
        });
        tombolKiri.setBounds(415, 5, 50, 25);
        add(tombolKiri);
    }
    private void tombolSlider() {
        sliderX = new JSlider(JSlider.HORIZONTAL,0,896,x);
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
                if(gravityToggleButton.isSelected()) {
                    isGravityEnabled = true;
                }
            }
        });
        add(sliderX);
    }
    private void tombolScrollBar() {
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
                if(gravityToggleButton.isSelected()) {
                    isGravityEnabled = true;
                }
            }
        });
        add(scrollY);
    }
    private void gravitasi(){
        setOpaque(false);
        new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                velocityX *= gesekan;
                if(velocityX < 0.1 && velocityX > -0.1) {
                    velocityX = 0;
                }

                x += velocityX;
                if(x < 0.1) {
                    velocityX = -elasticity * velocityX;
                    x = 0;
                }
                if(x + width > widthCanvas+10) {
                    velocityX = -elasticity * velocityX;
                    x = (widthCanvas+10) - width;
                }
                sliderX.setValue(x);
                scrollY.setValue(y);
                
                if(isGravityEnabled) {
                    velocityY += acceleration;
                    y += velocityY;
                    if(y + height > heightCanvas-32) {
                        velocityY = -elasticity * velocityY;
                        y = (heightCanvas-32) - height;
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
    public static  void main(String[] args) {
        JFrame frame = new JFrame("Bola GLBB");
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().add(new tesAA());
        frame.setSize(widthCanvas, heightCanvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
    }
}
