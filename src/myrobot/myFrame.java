package myrobot;

import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


import static myrobot.preFrame.titletest;

class myFrame extends JFrame implements ActionListener, KeyListener, MouseListener {

    private Robot robot;
    Random rnd = new Random(System.currentTimeMillis());
    Thread thread;
    int minq, maxq, min, max, mousek;
    int p = 0;
    final JLabel lblLed = new JLabel("<html><span style='font-size:20px'>" + "•" + "</span></html>");
    JFrame frame = new JFrame();
    Image img = Toolkit.getDefaultToolkit().getImage(preFrame.class.getResource("null_JAPP_icon.png"));
    final JPanel panel = new JPanel();
    final JTextField keyMin = new JTextField(20);
    final JTextField keyMax = new JTextField(20);
    final JTextField mouse = new JTextField(20);
    JLabel mintext = new JLabel("Min time (sec.)         ");
    JLabel maxtext = new JLabel("Max time (sec.)");
    JLabel proc = new JLabel("Mouse click (%)");
    static JButton settings = new JButton();
    JLabel kl = new JLabel();
    JLabel cl = new JLabel();
    JLabel rt = new JLabel();
    Image imgSet = ImageIO.read(getClass().getResource("settings-128.png"));
    Image imgon = ImageIO.read(getClass().getResource("on.png"));
    Image imgoff = ImageIO.read(getClass().getResource("off.png"));
    Image btofflin = ImageIO.read(getClass().getResource("lbtoff.png"));
    Image btonlin = ImageIO.read(getClass().getResource("lbton.png"));
    static JToggleButton toggleButton = new JToggleButton();
    String textopenitem = "Open";
    int j = 0;
    int l = 0;
    String s;
    PopupMenu trayMenu = new PopupMenu();
    boolean on = false;
    TrayIcon trayIcon;


    public void bton() {
        try {
            toggleButton.setIcon(new ImageIcon(imgon));
            toggleButton.setOpaque(false);
            toggleButton.setContentAreaFilled(false);
            toggleButton.setBorderPainted(false);
        } catch (Exception er) {
            toggleButton.setText("Start");
        }
    }

    public void btoff() {
        try {
            toggleButton.setIcon(new ImageIcon(imgoff));
            toggleButton.setOpaque(false);
            toggleButton.setContentAreaFilled(false);
            toggleButton.setBorderPainted(false);
        } catch (Exception er) {
            toggleButton.setText("\nStop");
        }
    }

    public myFrame() throws IOException, AWTException {


        frame.setLocationRelativeTo(null);
        setTitle(titletest);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        setIconImage(img);
        setPreferredSize(new Dimension(350, 220));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        MenuItem itemexit = new MenuItem("Exit");
        final MenuItem itemopen = new MenuItem(textopenitem);
        itemexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        itemopen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (myFrame.this.isVisible()) {
                    myFrame.this.setVisible(false);
                    myFrame.this.setFocusable(false);
                    itemopen.setLabel("Open");
                } else {
                    myFrame.this.setVisible(true);
                    myFrame.this.setFocusable(true);
                    itemopen.setLabel("Hiden");
                }
            }
        });


        trayMenu.add(itemopen);
        trayMenu.add(itemexit);
        if (SystemTray.isSupported()) {
            frame.setVisible(false);
            TrayIcon trayIcon = new TrayIcon(ImageIO.read(getClass().getResource("lbtoff.png")), "Click too start.", trayMenu);
            SystemTray tray = SystemTray.getSystemTray();
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);

        }
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = env.getDefaultScreenDevice();

        panel.setLayout(new MigLayout());

        try {
            robot = new Robot(screen);
        } catch (AWTException ex) {
            System.out.print("Run failed");
        }

        try {
            settings.setIcon(new ImageIcon(imgSet));
            settings.setMargin(new Insets(0, 0, 0, 0));
            settings.setOpaque(false);
            settings.setContentAreaFilled(false);
            settings.setBorderPainted(false);
        } catch (Exception e) {
            settings.setText("Settings");
        }
        panel.add(settings, "cell 0 0");
        panel.add(lblLed, "cell 0 0");
        panel.add(kl, "cell 0 0");
        panel.add(cl, "cell 0 0");
        panel.add(rt, "cell 1 0, wrap");
        panel.add(mintext);
        panel.add(keyMin, "wrap");
        keyMin.setText("10");
        panel.add(maxtext);
        panel.add(keyMax, "wrap");
        keyMax.setText("20");
        panel.add(proc);
        panel.add(mouse, "wrap");
        mouse.setText("50");
        panel.add(toggleButton, "span, center");
        toggleButton.setMargin(new Insets(0, 0, 0, 0));
        kl.setText("Pr: " + l);
        cl.setText("Cl: " + j);
        rt.setText("Run: 00.00");

        keyMin.addKeyListener(this);
        keyMax.addKeyListener(this);
        mouse.addKeyListener(this);
        toggleButton.addKeyListener(this);
        settings.addKeyListener(this);
        if (SystemTray.isSupported()) {
            trayIcon.addMouseListener(this);
        }
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                on = !on;
                repaint();
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();

        try {
            toggleButton.setIcon(new ImageIcon(imgoff));
            toggleButton.setOpaque(false);
            toggleButton.setContentAreaFilled(false);
            toggleButton.setBorderPainted(false);
        } catch (Exception e) {
            toggleButton.setText("Start");
        }
        if (SystemTray.isSupported()) {
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowIconified(WindowEvent event) {
                    myFrame.this.setState(Frame.ICONIFIED);
                    myFrame.this.setVisible(false);

                }
            });
        }
        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
//                int state = itemEvent.getStateChange();
                if (/*state == ItemEvent.SELECTED &&*/p == 1) {
                    try {
                        p = 0;
                        System.out.print("\nStop");
                        btoff();
//                        trayIcon.setImage(btofflin);
                        l = 0;
                        j = 0;
                    } catch (NumberFormatException e) {
                        confirmMs();
                    }
                } else if (/*state == ItemEvent.DESELECTED &&*/p == 0) {
                    try {
                        thread.run();
                        time();
                        System.out.print("\nStart");
                        bton();
                        if ((SystemTray.isSupported())) {
                            trayIcon.setImage(btonlin);
                        }
                    } catch (NumberFormatException e) {
                        p = 1;
                        confirmMs();
                    }
                }
            }
        };
        toggleButton.addItemListener(itemListener);


        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    settings.setEnabled(false);
                    toggleButton.setEnabled(false);
                    new preFrame().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR");
                }
            }
        });
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                minq = (int) Double.parseDouble(keyMin.getText());
                maxq = (int) Double.parseDouble(keyMax.getText());
                mousek = (int) Double.parseDouble(mouse.getText());
                p = 1;
                min = minq * 1000;
                max = maxq * 1000;
                final javax.swing.Timer t = new javax.swing.Timer(1000, taskPerformer);
                t.setRepeats(false);
                t.start();

            }
        });

        Container pane = getContentPane();
        pane.add(panel);
        pack();
    }


    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (p == 1) {
                System.out.print("1");
                int a = min + rnd.nextInt(max - min + 1);
                javax.swing.Timer t = new javax.swing.Timer(a, taskPerformer);
                t.setRepeats(false);
                t.start();
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                int b = 1 + rnd.nextInt(100 - 1 + 1);
                l++;
                kl.setText("Pr: " + l);
                rt.setText("Run: " + s);
                if (l >= preFrame.plimit && preFrame.plimit != 0) {
                    if (p == 1) {
                        p = 0;
                        System.out.print("\nStop");
                        btoff();
                        l = 0;
                        trayIcon.setImage(btofflin);
                    }
                } else if (j >= preFrame.climit && preFrame.climit != 0) {
                    if (p == 1) {
                        p = 0;
                        System.out.print("\nStop");
                        btoff();
                        l = 0;
                        j = 0;
                        trayIcon.setImage(btofflin);
                    }
                } else {
                    System.out.print("\n" + preFrame.plimit + " / " + l);
                }
                if (SystemTray.isSupported()) {
                    trayIcon.setToolTip("Uptime: " + s + "; Key pressed: " + l + " and " + "Click: " + j);
                }
                System.out.print("\nctrl: " + a + " click: " + b);
                if (b <= mousek) {
                    robot.mousePress(KeyEvent.BUTTON1_MASK);
                    robot.mouseRelease(KeyEvent.BUTTON1_MASK);
                    j++;
                    cl.setText("Cl: " + j);
                    System.out.print("\n+ ");

                }
            }
        }
    };

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            try {
                if (p == 1) {
                    p = 0;
                    System.out.print("\nStop");
                    btoff();
                    l = 0;
                    j = 0;
                    trayIcon.setImage(btofflin);
                }
            } catch (NumberFormatException e1) {
                confirmMs();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (p == 0) {
                try {
                    thread.run();
                    time();
                    System.out.print("\nStart");
                    bton();
                    trayIcon.setImage(btonlin);
                } catch (NumberFormatException e1) {
                    confirmMs();
                }
            }
        }

    }

    public String time() {
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss / dd.MM.yy");
        s = formatter.format(now);
        return s;

    }

    public void confirmMs() {
        JOptionPane.showMessageDialog(null, "Please fill in all fields.\nFields can contain only digits.", "Input ERROR", -1);

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
            if (mouseEvent.getClickCount() == 1 && !mouseEvent.isConsumed()) {
                mouseEvent.consume();
                if (p == 0) {
                    try {
                        thread.run();
                        System.out.print("2");
                        time();
                        trayIcon.setImage(btonlin);
                        System.out.print("\nStart");
                        bton();


                    } catch (NumberFormatException e1) {
                        confirmMs();
                    }
                } else if (p == 1) {
                    p = 0;
                    System.out.print("\nStop");
                    btoff();
                    trayIcon.setImage(btofflin);
                    l = 0;
                    j = 0;
                }
            }
        } else if (SwingUtilities.isMiddleMouseButton(mouseEvent)) {
            if (myFrame.this.isVisible()) {
                myFrame.this.setVisible(false);
                myFrame.this.setFocusable(false);
            } else {
                myFrame.this.setVisible(true);
                myFrame.this.setFocusable(true);
            }
        } else if (SwingUtilities.isRightMouseButton(mouseEvent)) {
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        if (!on && p == 1) {
            lblLed.setForeground(Color.RED);
        } else {
            lblLed.setForeground(Color.BLACK);
        }
        super.paint(g2d);
        g2d.dispose();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

}



