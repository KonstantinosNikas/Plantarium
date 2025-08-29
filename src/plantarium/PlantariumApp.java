import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlantariumApp extends JFrame {
    private JPanel mainPanel;
    private JButton manageEmployeeButton;
    private JButton managePlantInfoButton;
    private JButton managePlantHealthButton;
    private JButton managePlantCareButton;

    public PlantariumApp() {
        initComponents();

        manageEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateAdmin();
            }
        });

        managePlantInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateEmployee(new PlantInfoForm().getPanel(), "Manage Plant Info");
            }
        });

        managePlantHealthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateEmployee(new PlantHealthForm().getPanel(), "Manage Plant Health");
            }
        });

        managePlantCareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateEmployee(new PlantCareForm().getPanel(), "Manage Plant Care");
            }
        });
    }

    private void initComponents() {
        mainPanel = new JPanel();
        manageEmployeeButton = new JButton("Manage Employees");
        managePlantInfoButton = new JButton("Manage Plant Info");
        managePlantHealthButton = new JButton("Manage Plant Health");
        managePlantCareButton = new JButton("Manage Plant Care");

        mainPanel.add(manageEmployeeButton);
        mainPanel.add(managePlantInfoButton);
        mainPanel.add(managePlantHealthButton);
        mainPanel.add(managePlantCareButton);

        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void authenticateAdmin() {
        JPanel authPanel = new JPanel();
        JTextField adminNameField = new JTextField(20);
        JPasswordField adminPasswordField = new JPasswordField(20);

        authPanel.add(new JLabel("Admin Name:"));
        authPanel.add(adminNameField);
        authPanel.add(new JLabel("Admin Password:"));
        authPanel.add(adminPasswordField);

        int option = JOptionPane.showConfirmDialog(null, authPanel, "Admin Authentication", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String adminName = adminNameField.getText();
            String adminPassword = new String(adminPasswordField.getPassword());
            if (authenticate(adminName, adminPassword, "Admin")) {
                JFrame frame = new JFrame("Manage Employees");
                frame.setContentPane(new EmployeeForm().getPanel());
                frame.pack();
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid admin credentials!");
            }
        }
    }

    private void authenticateEmployee(JPanel panel, String title) {
        JPanel authPanel = new JPanel();
        JTextField employeeNameField = new JTextField(20);
        JPasswordField employeePasswordField = new JPasswordField(20);

        authPanel.add(new JLabel("Employee Name:"));
        authPanel.add(employeeNameField);
        authPanel.add(new JLabel("Employee Password:"));
        authPanel.add(employeePasswordField);

        int option = JOptionPane.showConfirmDialog(null, authPanel, "Employee Authentication", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String employeeName = employeeNameField.getText();
            String employeePassword = new String(employeePasswordField.getPassword());
            if (authenticate(employeeName, employeePassword, "Employee")) {
                JFrame frame = new JFrame(title);
                frame.setContentPane(panel);
                frame.pack();
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid employee credentials!");
            }
        }
    }

    private boolean authenticate(String name, String password, String role) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM " + role + " WHERE name = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PlantariumApp();
            }
        });
    }
}
