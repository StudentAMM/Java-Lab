/*
 * Created by JFormDesigner on Sun Oct 28 20:51:28 MSK 2018
 */

package com.company.myForm;

import com.company.Cats.CatsList;
import com.company.Cats.Cat;
import com.company.myForm.MyTable.MyTableCat;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import javax.swing.*;

import Fabric.Helper;
import com.google.gson.JsonArray;


public class MainForm extends JFrame {

    public MainForm() {
        initComponents();
        setBounds(200, 200, 470, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    //variables
    private JMenuBar menuBar;
    private JMenu mFile;
    private JMenuItem miOpen;
    private JMenuItem miNew;
    private JMenuItem miSave;
    private JMenuItem miSaveAs;
    private JMenuItem miClose;
    private JMenuItem miExit;
    private JMenu mEdit;
    private JMenuItem miAdd;
    private JMenuItem miChange;
    private JMenuItem miRemove;
    private JScrollPane scrollPane;
    private JTable tableRes;

    private MyTableCat myTable;
    private CatsList catsList;
    private Boolean listModify;  //check change list
    private String fullFileName;

    private void helpShowDialog(Cat cat) {
        chet dialog = new chet(cat);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void changeData() {
        this.listModify = true;
        this.myTable.fireTableDataChanged();
    }

    private void closeList() {
        this.catsList.clear();
        this.listModify = false;
        this.fullFileName = "";
        this.myTable.fireTableDataChanged();
    }

    //variables

    private void initComponents() {
        // initComponents
        menuBar = new JMenuBar();
        mFile = new JMenu();
        miOpen = new JMenuItem();
        miNew = new JMenuItem();
        miSave = new JMenuItem();
        miSaveAs = new JMenuItem();
        miClose = new JMenuItem();
        miExit = new JMenuItem();
        mEdit = new JMenu();
        miAdd = new JMenuItem();
        miChange = new JMenuItem();
        miRemove = new JMenuItem();
        scrollPane = new JScrollPane();

        catsList = new CatsList();
        myTable = new MyTableCat(this.catsList);
        tableRes = new JTable(myTable);
        listModify = false;
        fullFileName = "";
        //======== this ========

        setLayout(new BorderLayout());

        //======== action ========
        miOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    if (listModify) {
                        int result = JOptionPane.showConfirmDialog(
                                MainForm.this,
                                "Данные не сохранены. Вы уверены?",
                                "Закрыть файл",
                                JOptionPane.YES_NO_OPTION);
                        if (result != JOptionPane.YES_OPTION)
                            return;
                    }
                    File file = fileopen.getSelectedFile();
                    fullFileName = file.getPath();
                    //обработка файла
                    Helper help = new Helper();
                    try {
                        catsList.jsonToArray(help.strategy(file));
                        changeData();
                    } catch (NoSuchElementException ex) {
                        JOptionPane.showMessageDialog(null, "Формат файла не поддерживается");
                    }catch (FileNotFoundException fe) {
                        JOptionPane.showMessageDialog(null, "Файл не найден");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Ошибка доступа к файлу");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Ошибка при обработке файла");
                    }
                }
            }
        });

        miNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (listModify) {
                    int result = JOptionPane.showConfirmDialog(
                            MainForm.this,
                            "Данные не сохранены. Вы уверены?",
                            "Закрыть файл",
                            JOptionPane.YES_NO_OPTION);
                    if (result != JOptionPane.YES_OPTION)
                        return;
                }
                closeList();
            }
        });

        miSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!fullFileName.equals("")) {
                    //работа с файлом
                    File file = new File(fullFileName);
                    Helper help = new Helper();
                    JsonArray arr = catsList.listCatsToJson();
                    try {
                        help.strategy(file, arr);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Ошибка доступа к файлу");
                    }
                } else {
                    JFileChooser fc = new JFileChooser();
                    saveAsCommon(fc);
                }
            }
        });

        miSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                saveAsCommon(fc);
            }
        });

        miClose.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (listModify) {
                    int result = JOptionPane.showConfirmDialog(
                            MainForm.this,
                            "Данные не сохранены. Вы уверены?",
                            "Закрыть файл",
                            JOptionPane.YES_NO_OPTION);
                    if (result != JOptionPane.YES_OPTION)
                        return;
                    closeList();
                }
            }
        });

        miExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (listModify) {
                    int result = JOptionPane.showConfirmDialog(
                            MainForm.this,
                            "Данные не сохранены. Вы уверены?",
                            "Закрыть файл",
                            JOptionPane.YES_NO_OPTION);
                    if (result != JOptionPane.YES_OPTION)
                        return;
                }
                System.exit(1);
            }
        });


        miAdd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Cat cat = new Cat();
                helpShowDialog(cat);
                if (cat.getName().equals("") || cat.getBreed().equals(""))
                    return;
                catsList.add(cat);
                changeData();
            }
        });

        miChange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = tableRes.getSelectedRows();
                for (int i : selectedRows) {
                    Cat cat = catsList.get(i);
                    helpShowDialog(cat);
                    if (cat.getName().equals("") || cat.getBreed().equals(""))
                        return;
                    catsList.set(i, cat);
                    changeData();
                }
            }
        });

        miRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = tableRes.getSelectedRows();
                for (int i : selectedRows) {
                    catsList.remove(selectedRows[0]);
                }
                changeData();
            }
        });


        //обработать закрытие формы (красный крестик)

        //======== menuBar ========
        {

            //======== mFile ========
            {
                mFile.setText("File");

                //---- miOpen ----
                miOpen.setText("Open");
                mFile.add(miOpen);

                //---- miNew ----
                miNew.setText("New");
                mFile.add(miNew);

                //---- miSave ----
                miSave.setText("Save");
                mFile.add(miSave);

                //---- miSaveAs ----
                miSaveAs.setText("SaveAs");
                mFile.add(miSaveAs);

                //---- miClose ----
                miClose.setText("Close");
                mFile.add(miClose);
                mFile.addSeparator();

                //---- miExit ----
                miExit.setText("Exit");
                mFile.add(miExit);
            }
            menuBar.add(mFile);

            //======== mEdit ========
            {
                mEdit.setText("Edit");

                //---- miAdd ----
                miAdd.setText("Add");
                mEdit.add(miAdd);

                //---- miChange ----
                miChange.setText("Change");
                mEdit.add(miChange);

                //---- miDelete ----
                miRemove.setText("Delete");
                mEdit.add(miRemove);
            }
            menuBar.add(mEdit);
        }
        add(menuBar, BorderLayout.NORTH);

        //======== scrollPane ========
        {
            scrollPane.setViewportView(tableRes);
        }
        add(scrollPane, BorderLayout.CENTER);
        //initComponents

    }

    private void saveAsCommon(JFileChooser fc) {
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            Helper help = new Helper();
            JsonArray arr = catsList.listCatsToJson();
            try {
                help.strategy(file, arr);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ошибка доступа к файлу");
            }
        }
    }


}
