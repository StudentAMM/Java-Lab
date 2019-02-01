package forms;

import connector.DBConnector;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.ERROR_MESSAGE;


public class login extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtLogin;
    private JTextField txtPassword;
    private JLabel lbLog;

    // так не красиво
    private static final String DB_PROPERTIES_FILE = "src/main/resources/db.properties";

    public login() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setBounds(200, 200, 200, 200);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        //отредактировать properties
        String connectString = "url=jdbc:postgresql://localhost:5432/javaDB\n" +
                "user=" + txtLogin.getText() + "\n" +
                "password=" + txtPassword.getText();

        try {
        //создать соединение
            writeStringToFile(DB_PROPERTIES_FILE, connectString);
            Connection connection = DBConnector.getConnection();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(login.this, "Exception has occured during creation of connection","хм", ERROR_MESSAGE);
            return;
            //System.out.println("Exception has occured during creation of conenction");
            //e.printStackTrace();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(login.this, "File: " + DB_PROPERTIES_FILE + " is missing.","хм", ERROR_MESSAGE);
            return;
            //System.out.println("File: " + DB_PROPERTIES_FILE + " is missing.");
            //e.printStackTrace();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(login.this,
                    "Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE,"хм", ERROR_MESSAGE);
            return;
            //System.out.println("Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE);
            //e.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(login.this, "Привет","хм", ERROR_MESSAGE);
            return;
        }
        JFrame myWindow = new FrmMain();
        myWindow.setVisible(true);
        dispose();
    }

    private static void writeStringToFile(String file, String data) throws IOException {
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            throw new IOException();
        } finally {
            try {
                fr.close();
            } catch (IOException e) {
                throw new IOException();
            }
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        login dialog = new login();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
