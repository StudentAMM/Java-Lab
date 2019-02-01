package com.company.myForm;

import com.company.Cats.Cat;

import javax.swing.*;
import java.awt.event.*;

public class chet extends JDialog {
    private JPanel contentPane;
    private JButton btnOK;
    private JButton btnCancel;
    private JTextField txtFldName;
    private JTextField txtFldBreed;

    private Cat cat;

    public chet( Cat cat1) {

        //сделать подсветку невведенных значений
        setBounds(330, 220, 470, 320);
        this.cat = cat1;
        this.txtFldName.setText(this.cat.getName());
        this.txtFldBreed.setText(this.cat.getBreed());
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnOK);

        btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

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
        this.cat.setName(this.txtFldName.getText());
        this.cat.setBreed(this.txtFldBreed.getText());
        dispose();
    }

    private void onCancel() {
        this.cat.setName("");
        this.cat.setBreed("");
        dispose();
    }

}
