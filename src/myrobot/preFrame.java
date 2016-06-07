package myrobot;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

class preFrame extends JFrame {

    static String titletest = "Opera";
    static String icontest = null;
    static int plimit = 0;
    static int climit = 0;


    public preFrame() throws IOException {

        setTitle(titletest);
        Image img = Toolkit.getDefaultToolkit().getImage(preFrame.class.getResource("null_JAPP_icon.png"));
        setIconImage(img);
        setPreferredSize(new Dimension(350, 200));
        setResizable(false);
        setLocationRelativeTo(null);
        final JPanel panelpre = new JPanel(); // создаем контейнер
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = env.getDefaultScreenDevice(); // GraphicsDevice класс описывает графические устройства, которые могут быть доступны в определенной среде графики.
        panelpre.setLayout(new MigLayout());
        try {
            Robot robot = new Robot(screen);
        } catch (AWTException ex) {
            System.out.print("Run failed");
        }

        final JTextField title1 = new JTextField("Opera", 25);
        final JLabel title = new JLabel("Title program:");
        final JButton bt3 = new JButton("     GO     ");
        final JLabel icontext = new JLabel("Icon program:");
        final JTextField icon = new JTextField("http://findicons.com/files/icons/1008/quiet/128/opera.png", 25);
        JRadioButton win,lin;
        ButtonGroup buttonGroup = new ButtonGroup();
        win = new JRadioButton("Windows icon");
        buttonGroup.add(win);
        lin = new JRadioButton("Linux icon");
        buttonGroup.add(lin);
        final JLabel prlim = new JLabel("CTRL limit:");
        final JTextField prlimtext = new JTextField(""+plimit, 25);
        final JLabel cllim = new JLabel("Click limit:");
        final JTextField cllimtext = new JTextField(""+climit, 25);



        panelpre.add(title);
        panelpre.add(title1, "wrap");
        panelpre.add(icontext);
        panelpre.add(icon, "wrap");
        panelpre.add(prlim);
        panelpre.add(prlimtext, "wrap");
        panelpre.add(cllim);
        panelpre.add(cllimtext, "wrap");
        panelpre.add(win);
        panelpre.add(lin, "wrap");
        panelpre.add(bt3, "wrap 15");
        lin.setSelected(true);


        bt3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                titletest = title1.getText();
                icontest = icon.getText();


                String appdata = System.getenv("APPDATA");
                String iconPath = "/home/user/IdeaProjects/robot/src/myrobot/" + appdata + "_JAPP_icon.png";
//                System.out.print(iconPath);
                File icon;
                icon = new File(iconPath);
                plimit = (int) Double.parseDouble(prlimtext.getText());
                climit = (int) Double.parseDouble(cllimtext.getText());

                if (titletest.isEmpty() && icontest.isEmpty() || titletest.isEmpty() || icontest.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.\nOr use Opera settings.");
                } else {
//
                    if (!icon.exists()) {
                        FileDownloaderNEW fd = new FileDownloaderNEW();

                        try {
                            fd.download(icontest, iconPath, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {

                            setVisible(false);
                            myFrame.settings.setEnabled(true);
                            myFrame.toggleButton.setEnabled(true);
                            Image img = Toolkit.getDefaultToolkit().getImage(preFrame.class.getResource("null_JAPP_icon.png"));
                            setIconImage(img);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "ERROR");
                        }
                    } else {
                        JDialog.setDefaultLookAndFeelDecorated(true);
                        int response = JOptionPane.showConfirmDialog(null, "Use title " + titletest + "?", "Use this title?",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.NO_OPTION) {

                            try {
                                Image img = Toolkit.getDefaultToolkit().getImage(preFrame.class.getResource("null_JAPP_icon.png"));
                                setIconImage(img);
                                setVisible(false);
                                myFrame.settings.setEnabled(true);
                                myFrame.toggleButton.setEnabled(true);
                                System.out.println("Massage closed");
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "ERROR");
                            }
                        } else if (response == JOptionPane.YES_OPTION) {
                            FileDownloaderNEW fd = new FileDownloaderNEW();

                            try {
                                fd.download(icontest, iconPath, false);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                Image img = Toolkit.getDefaultToolkit().getImage(preFrame.class.getResource("null_JAPP_icon.png"));
                                setIconImage(img);
                                myFrame.settings.setEnabled(true);
                                myFrame.toggleButton.setEnabled(true);
                                setVisible(false);
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "ERROR");
                            }

                        } else if (response == JOptionPane.CLOSED_OPTION) {
                            System.out.println("Massage closed");
                        }

                    }
                }

            }

        });
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                plimit = (int) Double.parseDouble(prlimtext.getText());
                climit = (int) Double.parseDouble(cllimtext.getText());

                System.out.println("Closed");
                try {
                    setVisible(false);
                    myFrame.settings.setEnabled(true);
                    myFrame.toggleButton.setEnabled(true);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });


        Container pane = getContentPane();

        pane.add(panelpre);

        pack();
    }


}


