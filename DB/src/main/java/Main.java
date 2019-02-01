import forms.login;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // форма логина
        JDialog myDialog = new login();
        myDialog.setVisible(true);

        //если что-то случилось то дальше
        /*JFrame myWindow = new frmMain();
        myWindow.setVisible(true);*/
    }
}