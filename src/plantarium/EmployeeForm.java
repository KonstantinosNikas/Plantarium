import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeForm {
    private JTextField nameField;
    private JTextField specialityField;
    private JTextField jobField;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton readButton;
    private JPanel employeePanel;
    private JTextArea outputArea;

    public EmployeeForm() {
        employeePanel = new JPanel();
        employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));

        nameField = new JTextField(20);
        specialityField = new JTextField(20);
        jobField = new JTextField(20);
        saveButton = new JButton("Save");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        readButton = new JButton("Read");
        outputArea = new JTextArea(10, 30);

        employeePanel.add(new JLabel("Name:"));
        employeePanel.add(nameField);
        employeePanel.add(new JLabel("Speciality:"));
        employeePanel.add(specialityField);
        employeePanel.add(new JLabel("Job:"));
        employeePanel.add(jobField);
        employeePanel.add(saveButton);
        employeePanel.add(updateButton);
        employeePanel.add(deleteButton);
        employeePanel.add(readButton);
        employeePanel.add(new JScrollPane(outputArea));

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String speciality = specialityField.getText();
                String job = jobField.getText();

                Connection connection = DatabaseConnection.getConnection();
                String sql = "INSERT INTO Employee (name, speciality, job) VALUES (?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, speciality);
                    preparedStatement.setString(3, job);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Employee added successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String speciality = specialityField.getText();
                String job = jobField.getText();

                Connection connection = DatabaseConnection.getConnection();
                String sql = "UPDATE Employee SET speciality = ?, job = ? WHERE name = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, speciality);
                    preparedStatement.setString(2, job);
                    preparedStatement.setString(3, name);
                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, "Employee updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Employee not found!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();

                Connection connection = DatabaseConnection.getConnection();
                String sql = "DELETE FROM Employee WHERE name = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, name);
                    int rowsDeleted = preparedStatement.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(null, "Employee deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Employee not found!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection = DatabaseConnection.getConnection();
                String sql = "SELECT * FROM Employee";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    StringBuilder result = new StringBuilder();
                    while (resultSet.next()) {
                        result.append("Name: ").append(resultSet.getString("name"))
                              .append(", Speciality: ").append(resultSet.getString("speciality"))
                              .append(", Job: ").append(resultSet.getString("job"))
                              .append("\n");
                    }
                    outputArea.setText(result.toString());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public JPanel getPanel() {
        return employeePanel;
    }
}
