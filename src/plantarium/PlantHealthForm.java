import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlantHealthForm {
    private JTextField nameField;
    private JTextField issueField;
    private JTextField drugsField;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton readButton;
    private JPanel plantHealthPanel;
    private JTextArea outputArea;

    public PlantHealthForm() {
        plantHealthPanel = new JPanel();
        plantHealthPanel.setLayout(new BoxLayout(plantHealthPanel, BoxLayout.Y_AXIS));

        nameField = new JTextField(20);
        issueField = new JTextField(20);
        drugsField = new JTextField(20);
        saveButton = new JButton("Save");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        readButton = new JButton("Read");
        outputArea = new JTextArea(10, 30);

        plantHealthPanel.add(new JLabel("Name:"));
        plantHealthPanel.add(nameField);
        plantHealthPanel.add(new JLabel("Issue:"));
        plantHealthPanel.add(issueField);
        plantHealthPanel.add(new JLabel("Drugs:"));
        plantHealthPanel.add(drugsField);
        plantHealthPanel.add(saveButton);
        plantHealthPanel.add(updateButton);
        plantHealthPanel.add(deleteButton);
        plantHealthPanel.add(readButton);
        plantHealthPanel.add(new JScrollPane(outputArea));

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String issue = issueField.getText();
                String drugs = drugsField.getText();

                Connection connection = DatabaseConnection.getConnection();
                String sql = "INSERT INTO PlantHealth (name, issue, drugs) VALUES (?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, issue);
                    preparedStatement.setString(3, drugs);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Plant health added successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String issue = issueField.getText();
                String drugs = drugsField.getText();

                Connection connection = DatabaseConnection.getConnection();
                String sql = "UPDATE PlantHealth SET issue = ?, drugs = ? WHERE name = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, issue);
                    preparedStatement.setString(2, drugs);
                    preparedStatement.setString(3, name);
                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, "Plant health updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Plant health not found!");
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
                String sql = "DELETE FROM PlantHealth WHERE name = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, name);
                    int rowsDeleted = preparedStatement.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(null, "Plant health deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Plant health not found!");
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
                String sql = "SELECT * FROM PlantHealth";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    StringBuilder result = new StringBuilder();
                    while (resultSet.next()) {
                        result.append("Name: ").append(resultSet.getString("name"))
                              .append(", Issue: ").append(resultSet.getString("issue"))
                              .append(", Drugs: ").append(resultSet.getString("drugs"))
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
        return plantHealthPanel;
    }
}
