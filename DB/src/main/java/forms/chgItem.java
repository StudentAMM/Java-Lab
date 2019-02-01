package forms;

import models.Item;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;


public class chgItem extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtSomeInfo;
    private JTextField txtDay;
    private JTextField txtMounth;
    private JTextField txtYear;
    private JTextField txtParenId;

    private Item itm;

    public chgItem(Item item) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.itm = item;
        txtSomeInfo.setText(item.getSomeInfo());
        txtParenId.setText(Integer.toString(item.getParentId()));
        String s = String.format("%1$te %1$tm %1$tY", item.getDate());
        String ss[] = s.split(" ");
        txtDay.setText(ss[0]);
        txtMounth.setText(ss[1]);
        txtYear.setText(ss[2]);
        //txtDay.setText(Integer.toString(item.getDate().getDay()));
        //txtMounth.setText(Integer.toString(item.getDate().getMonth()));
        //txtYear.setText(Integer.toString(item.getDate().getYear()));
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
        try {
            this.itm.setSomeInfo(this.txtSomeInfo.getText());
            this.itm.setDate(new Date(Integer.parseInt(this.txtYear.getText()) - 1900,
                    Integer.parseInt(this.txtMounth.getText()) - 1,
                    Integer.parseInt(this.txtDay.getText())));
            this.itm.setParentId(Integer.parseInt(this.txtParenId.getText()));
        } catch (Exception ex) {

        } finally {
            dispose();
        }
    }

    private void onCancel() {
        //this.itm.setDate(new Date(0,0,0));
        this.itm.setSomeInfo("");
        //this.itm.setParentId(0);
        dispose();
    }

    /*public static void main(String[] args) {
        chgItem dialog = new chgItem();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
