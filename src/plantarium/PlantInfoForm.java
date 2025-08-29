import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlantInfoForm {
    private JTextField nameField;
    private JTextField specialityField;
    private JTextField categoryField;
    private JTextField costField;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton readButton;
    private JPanel plantInfoPanel;
    private JTextArea outputArea;

    public PlantInfoForm() {
        plantInfoPanel = new JPanel();
        plantInfoPanel.setLayout(new BoxLayout(plantInfoPanel, BoxLayout.Y_AXIS));

        nameField = new JTextField(20);
        specialityField = new JTextField(20);
        categoryField = new JTextField(20);
        costField = new JTextField(20);
        saveButton = new JButton("Save");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        readButton = new JButton("Read");
        outputArea = new JTextArea(10, 30);

        plantInfoPanel.add(new JLabel("Name:"));
        plantInfoPanel.add(nameField);
        plantInfoPanel.add(new JLabel("Speciality:"));
        plantInfoPanel.add(specialityField);
        plantInfoPanel.add(new JLabel("Category:"));
        plantInfoPanel.add(categoryField);
        plantInfoPanel.add(new JLabel("Cost:"));
        plantInfoPanel.add(costField);
        plantInfoPanel.add(saveButton);
        plantInfoPanel.add(updateButton);
        plantInfoPanel.add(deleteButton);
        plantInfoPanel.add(readButton);
        plantInfoPanel.add(new JScrollPane(outputArea));

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String speciality = specialityField.getText();
                String category = categoryField.getText();
                String cost = costField.getText();

                Connection connection = DatabaseConnection.getConnection();
                String sql = "INSERT INTO PlantInfo (name, speciality, category, cost) VALUES (?, ?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, speciality);
                    preparedStatement.setString(3, category);
                    preparedStatement.setString(4, cost);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Plant info added successfully!");
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
                String category = categoryField.getText();
                String cost = costField.getText();

                Connection connection = DatabaseConnection.getConnection();
                String sql = "UPDATE PlantInfo SET speciality = ?, category = ?, cost = ? WHERE name = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, speciality);
                    preparedStatement.setString(2, category);
                    preparedStatement.setString(3, cost);
                    preparedStatement.setString(4, name);
                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, "Plant info updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Plant info not found!");
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
                String sql = "DELETE FROM PlantInfo WHERE name = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, name);
                    int rowsDeleted = preparedStatement.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(null, "Plant info deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Plant info not found!");
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
                String sql = "SELECT * FROM PlantInfo";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    StringBuilder result = new StringBuilder();
                    while (resultSet.next()) {
                        result.append("Name: ").append(resultSet.getString("name"))
                              .append(", Speciality: ").append(resultSet.getString("speciality"))
                              .append(", Category: ").append(resultSet.getString("category"))
                              .append(", Cost: ").append(resultSet.getString("cost"))
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
        return plantInfoPanel;
    }
}
