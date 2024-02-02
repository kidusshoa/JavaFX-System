package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {

    private final JPanel MiniPanel, MainPanel, LeftPanel, RightPanel;
    private final JButton Close_Button, Login_Button;
    private final JLabel PW_Label, UN_Label, Image_jLabel, info_Label;
    private final JTextField UN_TextField;
    private final JPasswordField Password_Field;

    public Login() {
        MiniPanel = new JPanel();
        MainPanel = new JPanel(new BorderLayout());
        LeftPanel = new JPanel(new GridBagLayout());
        RightPanel = new JPanel();

        Close_Button = new JButton("Close");
        Login_Button = new JButton("Login");

        PW_Label = new JLabel("Password");
        UN_Label = new JLabel("Username");
        info_Label = new JLabel("Please Enter Your Login Details");
        Image_jLabel = new JLabel();

        UN_TextField = new JTextField();
        Password_Field = new JPasswordField();

        LeftPanel.setBackground(Color.BLACK);
        LeftPanel.setPreferredSize(new Dimension(400, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        info_Label.setFont(new Font("Consolas", Font.BOLD, 18));
        info_Label.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        LeftPanel.add(info_Label, gbc);

        UN_Label.setFont(new Font("Consolas", Font.PLAIN, 18));
        UN_Label.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        LeftPanel.add(UN_Label, gbc);

        UN_TextField.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        LeftPanel.add(UN_TextField, gbc);

        PW_Label.setFont(new Font("Consolas", Font.PLAIN, 18));
        PW_Label.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        LeftPanel.add(PW_Label, gbc);

        Password_Field.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        LeftPanel.add(Password_Field, gbc);

        // Buttons side by side
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        LeftPanel.add(Login_Button, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        LeftPanel.add(Close_Button, gbc);

        MainPanel.add(LeftPanel, BorderLayout.WEST);

        RightPanel.setLayout(new BorderLayout());
        Image_jLabel.setIcon(new ImageIcon("img2.jpg"));
        
        RightPanel.add(Image_jLabel, BorderLayout.CENTER);
        MainPanel.add(RightPanel, BorderLayout.CENTER);

        Login_Button.addActionListener(new LoginActionListener());
        Close_Button.addActionListener(new LoginActionListener());
    }

    /**
     * @return the MainPanel
     */
    public JPanel getMainPanel() {
        return MainPanel;
    }

    private class LoginActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Close": {
                    int showConfirmDialog = JOptionPane.showConfirmDialog(null, "You are about to terminate the program.\n"
                            + " Are you sure you want to continue ?", "Close Confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null);
                    if (showConfirmDialog == 0) {
                        System.exit(0);
                    }
                    break;
                }
                case "Login": {
                    if (UN_TextField.getText().trim().equalsIgnoreCase("admin")
                            && String.valueOf(Password_Field.getPassword()).equals("123")) {
                        UN_TextField.setText("");
                        Password_Field.setText("");
                        Runner.getFrame().dispose();
                        Parent_JFrame frame = new Parent_JFrame();
                        MainMenu menu = new MainMenu();
                        JFrame mainFrame = Parent_JFrame.getMainFrame();
                        mainFrame.add(menu.getMainPanel());
                        mainFrame.setVisible(true);
                    } else if (UN_TextField.getText().trim().equalsIgnoreCase("kidus")
                            && String.valueOf(Password_Field.getPassword()).equals("0000")) {
                        UN_TextField.setText("");
                        Password_Field.setText("");
                        Runner.getFrame().dispose();
                        Parent_JFrame frame = new Parent_JFrame();
                        MainMenu menu = new MainMenu();
                        JFrame mainFrame = Parent_JFrame.getMainFrame();
                        mainFrame.add(menu.getMainPanel());
                        mainFrame.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid UserName/Password", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
            }
        }
    }
}
