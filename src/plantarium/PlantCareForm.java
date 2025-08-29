import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlantCareForm {
    private JTextField nameField;
    private JTextField waterFreqField;
    private JTextField waterQuField;
    private JTextField sunExpositionField;
    private JTextField pruningField;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton readButton;
    private JPanel plantCarePanel;
    private JTextArea outputArea;

    public PlantCareForm() {
        plantCarePanel = new JPanel();
        plantCarePanel.setLayout(new BoxLayout(plantCarePanel, BoxLayout.Y_AXIS));

        nameField = new JTextField(20);
        waterFreqField = new JTextField(20);
        waterQuField = new JTextField(20);
        sunExpositionField = new JTextField(20);
        pruningField = new JTextField(20);
        saveButton = new JButton("Save");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        readButton = new JButton("Read");
        outputArea = new JTextArea(10, 30);

        plantCarePanel.add(new JLabel("Name:"));
        plantCarePanel.add(nameField);
        plantCarePanel.add(new JLabel("Water Frequency:"));
        plantCarePanel.add(waterFreqField);
        plantCarePanel.add(new JLabel("Water Quantity:"));
        plantCarePanel.add(waterQuField);
        plantCarePanel.add(new JLabel("Sun Exposition:"));
        plantCarePanel.add(sunExpositionField);
        plantCarePanel.add(new JLabel("Pruning:"));
        plantCarePanel.add(pruningField);
        plantCarePanel.add(saveButton);
        plantCarePanel.add(updateButton);
        plantCarePanel.add(deleteButton);
        plantCarePanel.add(readButton);
        plantCarePanel.add(new JScrollPane(outputArea));

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String waterFreq = waterFreqField.getText();
                String waterQu = waterQuField.getText();
                String sunExposition = sunExpositionField.getText();
                String pruning = pruningField.getText();

                Connection connection = DatabaseConnection.getConnection();
                String sql = "INSERT INTO PlantCare (name, water_freq, water_qu, sun_exposition, pruning) VALUES (?, ?, ?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, waterFreq);
                    preparedStatement.setString(3, waterQu);
                    preparedStatement.setString(4, sunExposition);
                    preparedStatement.setString(5, pruning);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Plant care added successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String waterFreq = waterFreqField.getText();
                String waterQu = waterQuField.getText();
                String sunExposition = sunExpositionField.getText();
                String pruning = pruningField.getText();

                Connection connection = DatabaseConnection.getConnection();
                String sql = "UPDATE PlantCare SET water_freq = ?, water_qu = ?, sun_exposition = ?, pruning = ? WHERE name = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, waterFreq);
                    preparedStatement.setString(2, waterQu);
                    preparedStatement.setString(3, sunExposition);
                    preparedStatement.setString(4, pruning);
                    preparedStatement.setString(5, name);
                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, "Plant care updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Plant care not found!");
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
                String sql = "DELETE FROM PlantCare WHERE name = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, name);
                    int rowsDeleted = preparedStatement.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(null, "Plant care deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Plant care not found!");
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
                String sql = "SELECT * FROM PlantCare";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    StringBuilder result = new StringBuilder();
                    while (resultSet.next()) {
                        result.append("Name: ").append(resultSet.getString("name"))
                              .append(", Water Frequency: ").append(resultSet.getInt("water_freq"))
                              .append(", Water Quantity: ").append(resultSet.getBigDecimal("water_qu"))
                              .append(", Sun Exposition: ").append(resultSet.getString("sun_exposition"))
                              .append(", Pruning: ").append(resultSet.getString("pruning"))
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
        return plantCarePanel;
    }
}
