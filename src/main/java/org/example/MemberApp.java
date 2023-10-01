package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MemberApp {

    private JFrame frame;
    private JTextField firstNameField, lastNameField, ageField;
    private JButton saveButton;

    // Database credentials
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "ebijon";
    private static final String PASS = "ebijon";

    public MemberApp() {
        frame = new JFrame("Member Information");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        // Labels
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel ageLabel = new JLabel("Age:");

        // Text Fields
        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        ageField = new JTextField(5);

        // Save Button
        saveButton = new JButton("Save");

        // Button actions
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToDatabase();
            }
        });

        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(saveButton);

        frame.add(panel);

        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Function to save data to the Oracle database
    private void saveToDatabase() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            String query = "INSERT INTO members (id, firstname, lastname, age) VALUES (members_seq.nextval, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, firstNameField.getText());
            pstmt.setString(2, lastNameField.getText());
            pstmt.setInt(3, Integer.parseInt(ageField.getText()));

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Member saved successfully!");

            // Clearing the fields
            firstNameField.setText("");
            lastNameField.setText("");
            ageField.setText("");

            pstmt.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error saving member info.");
        }
    }

    public static void main(String[] args) {
        new MemberApp();
    }
}
