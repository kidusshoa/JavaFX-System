package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class MainMenu implements ActionListener {

    private final JPanel MainPanel;

    public JPanel getMainPanel() {
        return MainPanel;
    }

    public MainMenu() {
        MainPanel = new JPanel();

        MainPanel.setLayout(new AbsoluteLayout());
        MainPanel.setMinimumSize(new Dimension(1366, 730));

        JButton customerButton = new JButton("Customer");
        JButton carsButton = new JButton("Cars");
        JButton ownerButton = new JButton("Owner");
        JButton bookingButton = new JButton("Booking Details");
        JButton logoutButton = new JButton("Logout");

        JLabel image_Label = new JLabel();

        logoutButton.setFont(new Font("Tahoma", 1, 14));
        customerButton.setFont(new Font("Tahoma", 1, 14));
        carsButton.setFont(new Font("Tahoma", 1, 14));
        ownerButton.setFont(new Font("Tahoma", 1, 14));
        bookingButton.setFont(new Font("Tahoma", 1, 14));

        image_Label.setIcon((new ImageIcon("MainMenuImage.jpeg")));

        customerButton.setBackground(new Color(240,240,240));
        carsButton.setBackground(new Color(240,240,240));
        ownerButton.setBackground(new Color(240,240,240));
        logoutButton.setBackground(new Color(240,240,240));
        bookingButton.setBackground(new Color(240,240,240));

        MainPanel.add(logoutButton, new AbsoluteConstraints(1166, 80, 100, 25));
        MainPanel.add(customerButton, new AbsoluteConstraints(70, 220, 200, 99));
        MainPanel.add(carsButton, new AbsoluteConstraints(70, 500, 200, 99));
        MainPanel.add(ownerButton, new AbsoluteConstraints(70, 360, 200, 99));
        MainPanel.add(bookingButton, new AbsoluteConstraints(70, 80, 200, 99));
        MainPanel.add(image_Label, new AbsoluteConstraints(0, 0, 1370, 710));

        bookingButton.addActionListener(this);
        customerButton.addActionListener(this);
        ownerButton.addActionListener(this);
        logoutButton.addActionListener(this);
        carsButton.addActionListener(this);
//        Parent_JFrame.getMainFrame().add(MainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Cars": {
                Parent_JFrame.getMainFrame().getContentPane().removeAll();
                Car_Details cd = new Car_Details();
                Parent_JFrame.getMainFrame().add(cd.getMainPanel());
                Parent_JFrame.getMainFrame().getContentPane().revalidate();
            }
            break;
            case "Customer": {
                Parent_JFrame.getMainFrame().getContentPane().removeAll();
                Customer_Details cd = new Customer_Details();
                Parent_JFrame.getMainFrame().add(cd.getMainPanel());
                Parent_JFrame.getMainFrame().getContentPane().revalidate();
            }
            break;
            case "Owner": {
                Parent_JFrame.getMainFrame().getContentPane().removeAll();
                CarOwner_Details cd = new CarOwner_Details();
                Parent_JFrame.getMainFrame().add(cd.getMainPanel());
                Parent_JFrame.getMainFrame().getContentPane().revalidate();
            }
            break;
            case "Logout": {
                Parent_JFrame.getMainFrame().dispose();
                Runner r = new Runner();
                JFrame frame = r.getFrame();
                Login login = new Login();
                JPanel panel = login.getMainPanel();
                frame.add(panel);
                frame.setVisible(true);
            }
            break;
            case "Booking Details": {
            Parent_JFrame.getMainFrame().getContentPane().removeAll();
                Booking_Details cd = new Booking_Details();
                Parent_JFrame.getMainFrame().add(cd.getMainPanel());
                Parent_JFrame.getMainFrame().getContentPane().revalidate();
            }
            break;
        }
    }
}
