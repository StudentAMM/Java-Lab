/*
 * Created by JFormDesigner on Sun Oct 07 21:35:45 MSK 2018
 */

package forms;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.*;

import Repository.SomeItemRepository;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import models.Item;
import models.ListItem;
import myTable.TableItem;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 * @author Alexander
 */
public class FrmMain extends JFrame {
    private static final String DB_PROPERTIES_FILE = "src/main/resources/db.properties";

    public FrmMain() {
        initComponents();
        try {
            SomeItemRepository repository = new SomeItemRepository();
            itemList.updateList(repository.getAll(cmbTables.getSelectedItem().toString()));
            myTableItem.fireTableDataChanged();
            setBounds(200, 200, 545, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Exception has occured during creation of connection", "хм", ERROR_MESSAGE);
            //System.out.println("Exception has occured during creation of conenction");
            //e.printStackTrace();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File: " + DB_PROPERTIES_FILE + " is missing.", "хм", ERROR_MESSAGE);
            //System.out.println("File: " + DB_PROPERTIES_FILE + " is missing.");
            //e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE, "хм", ERROR_MESSAGE);
            //System.out.println("Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE);
            //e.printStackTrace();
        }
    }

    //BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu;
    private JMenuItem mnItmConnect;
    private JMenuItem mnItmCloseConnect;
    private JMenuItem mnItmExit;
    private JMenu mnEdit;
    private JMenuItem mnItmAdd;
    private JMenuItem mnItmChange;
    private JMenuItem mnItmDelete;
    private JMenuItem mnItmSinchro;
    private JMenuItem mnItmClearTable;
    private JComboBox cmbTables;
    private JComboBox cmbColumn;
    private JTextField txtParse;
    private JButton btnSelect;
    private JButton btnSelectParents;
    private JButton btnSelectChildren;
    private JButton btnSelectAll;
    private JScrollPane scrollPane1;
    private JTable mTable;

    private TableItem myTableItem;
    private ListItem itemList;
    //END:variables

    private void initComponents() {
        //BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu = new JMenu();
        mnItmConnect = new JMenuItem();
        mnItmCloseConnect = new JMenuItem();
        mnItmExit = new JMenuItem();
        mnEdit = new JMenu();
        mnItmAdd = new JMenuItem();
        mnItmChange = new JMenuItem();
        mnItmDelete = new JMenuItem();
        mnItmSinchro = new JMenuItem();
        mnItmClearTable = new JMenuItem();
        cmbTables = new JComboBox();
        cmbColumn = new JComboBox();
        txtParse = new JTextField();
        btnSelect = new JButton();
        btnSelectParents = new JButton();
        btnSelectChildren = new JButton();
        btnSelectAll = new JButton();
        scrollPane1 = new JScrollPane();
        //mTable = new JTable();

        itemList = new ListItem();
        myTableItem = new TableItem(this.itemList);
        mTable = new JTable(myTableItem);
        mTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cmbTables.addItem("pTable");
        cmbTables.addItem("cTable");
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SomeItemRepository repository = new SomeItemRepository();
                    itemList.updateList(repository.getAll(cmbTables.getSelectedItem().toString()));
                    myTableItem.fireTableDataChanged();
                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "Exception has occured during creation of connection", "хм", ERROR_MESSAGE);
                    //System.out.println("Exception has occured during creation of conenction");
                    //e.printStackTrace();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "File: " + DB_PROPERTIES_FILE + " is missing.", "хм", ERROR_MESSAGE);
                    //System.out.println("File: " + DB_PROPERTIES_FILE + " is missing.");
                    //e.printStackTrace();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE, "хм", ERROR_MESSAGE);
                    //System.out.println("Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE);
                    //e.printStackTrace();
                }
            }
        };
        cmbTables.addActionListener(actionListener);
        cmbColumn.addItem("idPerson");
        cmbColumn.addItem("someInfo");
        cmbColumn.addItem("dateBirthday");
        cmbColumn.addItem("parentId");
        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
                "default, 0dlu, $lcgap, 46dlu, $lcgap, 50dlu, 2*($lcgap), 2*(default, $lcgap), $lcgap, default, 0dlu",
                "2*(default, $lgap), default"));

        //======== menuBar1 ========
        {

            //======== menu ========
            {
                menu.setText("Menu");

                //---- mnItmConnect ----
                mnItmConnect.setText("Connect");
                menu.add(mnItmConnect);

                //---- mnItmCloseConnect ----
                mnItmCloseConnect.setText("Close Connect");
                menu.add(mnItmCloseConnect);
                menu.addSeparator();

                //---- mnItmExit ----
                mnItmExit.setText("Exit");
                menu.add(mnItmExit);
            }
            menuBar1.add(menu);

            //======== mnEdit ========
            {
                mnEdit.setText("Edit");

                //---- mnItmAdd ----
                mnItmAdd.setText("Add");
                mnEdit.add(mnItmAdd);

                mnItmAdd.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            SomeItemRepository repository = new SomeItemRepository();
                            Item itm = new Item();
                            helpShowItem(itm);
                            if (itm.getSomeInfo().equals(""))
                                return;
                            repository.insert(cmbTables.getSelectedItem().toString(), itm);
                            itemList.updateList(repository.getAll(cmbTables.getSelectedItem().toString()));
                            myTableItem.fireTableDataChanged();
                        }catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Exception has occured during creation of connection", "хм", ERROR_MESSAGE);
                            //System.out.println("Exception has occured during creation of conenction");
                            //e.printStackTrace();
                        } catch (FileNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "File: " + DB_PROPERTIES_FILE + " is missing.", "хм", ERROR_MESSAGE);
                            //System.out.println("File: " + DB_PROPERTIES_FILE + " is missing.");
                            //e.printStackTrace();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE, "хм", ERROR_MESSAGE);
                            //System.out.println("Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE);
                            //e.printStackTrace();
                        }
                    }
                });

                //---- mnItmChange ----
                mnItmChange.setText("Change");
                mnEdit.add(mnItmChange);

                mnItmChange.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        SomeItemRepository repository = new SomeItemRepository();
                        if (cmbTables.getSelectedItem() == null)
                            return;
                        int selectedRow = mTable.getSelectedRow();
                        Item itm = itemList.get(selectedRow);
                        helpShowItem(itm);
                        if (itm.getSomeInfo().equals(""))
                            return;
                        try {
                            repository.update(cmbTables.getSelectedItem().toString(), itm);
                            itemList.updateList(repository.getAll(cmbTables.getSelectedItem().toString()));
                            myTableItem.fireTableDataChanged();
                        }catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Exception has occured during creation of connection", "хм", ERROR_MESSAGE);
                            //System.out.println("Exception has occured during creation of conenction");
                            //e.printStackTrace();
                        } catch (FileNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "File: " + DB_PROPERTIES_FILE + " is missing.", "хм", ERROR_MESSAGE);
                            //System.out.println("File: " + DB_PROPERTIES_FILE + " is missing.");
                            //e.printStackTrace();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE, "хм", ERROR_MESSAGE);
                            //System.out.println("Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE);
                            //e.printStackTrace();
                        }
                    }
                });

                //---- mnItmDelete ----
                mnItmDelete.setText("Delete");
                mnEdit.add(mnItmDelete);

                mnItmDelete.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        SomeItemRepository repository = new SomeItemRepository();
                        if (cmbTables.getSelectedItem() == null)
                            return;
                        int result = JOptionPane.showConfirmDialog(
                                FrmMain.this,
                                "Элемент и все потомки будут удалены. Вы уверены?",
                                "Удалить элемент",
                                JOptionPane.YES_NO_OPTION);
                        if (result != JOptionPane.YES_OPTION)
                            return;
                        try {
                            repository.delete(cmbTables.getSelectedItem().toString(), itemList.get(mTable.getSelectedRow()).getId());
                            itemList.updateList(repository.getAll(cmbTables.getSelectedItem().toString()));
                            myTableItem.fireTableDataChanged();
                        }catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Exception has occured during creation of connection", "хм", ERROR_MESSAGE);
                            //System.out.println("Exception has occured during creation of conenction");
                            //e.printStackTrace();
                        } catch (FileNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "File: " + DB_PROPERTIES_FILE + " is missing.", "хм", ERROR_MESSAGE);
                            //System.out.println("File: " + DB_PROPERTIES_FILE + " is missing.");
                            //e.printStackTrace();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE, "хм", ERROR_MESSAGE);
                            //System.out.println("Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE);
                            //e.printStackTrace();
                        }
                    }
                });
                mnEdit.addSeparator();

                //---- mnItmSinchro ----
                mnItmSinchro.setText("Synchro");
                mnEdit.add(mnItmSinchro);

                mnItmSinchro.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            SomeItemRepository repository = new SomeItemRepository();
                            repository.sinchro("pTable", "cTable");
                            itemList.updateList(repository.getAll(cmbTables.getSelectedItem().toString()));
                            myTableItem.fireTableDataChanged();
                        }catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Exception has occured during creation of connection", "хм", ERROR_MESSAGE);
                            //System.out.println("Exception has occured during creation of conenction");
                            //e.printStackTrace();
                        } catch (FileNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "File: " + DB_PROPERTIES_FILE + " is missing.", "хм", ERROR_MESSAGE);
                            //System.out.println("File: " + DB_PROPERTIES_FILE + " is missing.");
                            //e.printStackTrace();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE, "хм", ERROR_MESSAGE);
                            //System.out.println("Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE);
                            //e.printStackTrace();
                        }
                    }
                });

                mnEdit.addSeparator();

                //---- mniClearTable ----
                mnItmClearTable.setText("Clear Table");
                mnItmClearTable.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            SomeItemRepository repository = new SomeItemRepository();
                            int result = JOptionPane.showConfirmDialog(
                                    FrmMain.this,
                                    "Clear All. Are you sure?",
                                    "Clear",
                                    JOptionPane.YES_NO_OPTION);
                            if (result != JOptionPane.YES_OPTION)
                                return;
                            repository.clear(cmbTables.getSelectedItem().toString());
                            itemList.updateList(repository.getAll(cmbTables.getSelectedItem().toString()));
                            myTableItem.fireTableDataChanged();
                        }catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Exception has occured during creation of connection", "хм", ERROR_MESSAGE);
                            //System.out.println("Exception has occured during creation of conenction");
                            //e.printStackTrace();
                        } catch (FileNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "File: " + DB_PROPERTIES_FILE + " is missing.", "хм", ERROR_MESSAGE);
                            //System.out.println("File: " + DB_PROPERTIES_FILE + " is missing.");
                            //e.printStackTrace();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE, "хм", ERROR_MESSAGE);
                            //System.out.println("Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE);
                            //e.printStackTrace();
                        }
                    }
                });
                mnEdit.add(mnItmClearTable);
            }
            menuBar1.add(mnEdit);
            menuBar1.add(cmbTables);
        }
        setJMenuBar(menuBar1);
        contentPane.add(cmbColumn, CC.xy(1, 1));
        contentPane.add(txtParse, CC.xy(4, 1));

        //---- btnSelect ----
        btnSelect.setText("Select");
        contentPane.add(btnSelect, CC.xy(6, 1));

        btnSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SomeItemRepository repository = new SomeItemRepository();
                    itemList.updateList(repository.getNeedLike(cmbTables.getSelectedItem().toString(),
                            cmbColumn.getSelectedItem().toString(),
                            txtParse.getText()
                    ));
                    // itemList.updateList(repository.getAll(cmbTables.getSelectedItem().toString()));
                    myTableItem.fireTableDataChanged();
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Exception has occured during creation of connection", "хм", ERROR_MESSAGE);
                    //System.out.println("Exception has occured during creation of conenction");
                    //e.printStackTrace();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "File: " + DB_PROPERTIES_FILE + " is missing.", "хм", ERROR_MESSAGE);
                    //System.out.println("File: " + DB_PROPERTIES_FILE + " is missing.");
                    //e.printStackTrace();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE, "хм", ERROR_MESSAGE);
                    //System.out.println("Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE);
                    //e.printStackTrace();
                }
            }
        });

        //---- btnSelectParents ----
        btnSelectParents.setText("Parent");
        contentPane.add(btnSelectParents, CC.xy(9, 1));

        btnSelectParents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SomeItemRepository repository = new SomeItemRepository();
                    itemList.updateList(repository.getAncestryOrDescendants(cmbTables.getSelectedItem().toString(),
                            itemList.get(mTable.getSelectedRow()).getId(),
                            false));
                    myTableItem.fireTableDataChanged();
                }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Exception has occured during creation of connection", "хм", ERROR_MESSAGE);
                    //System.out.println("Exception has occured during creation of conenction");
                    //e.printStackTrace();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "File: " + DB_PROPERTIES_FILE + " is missing.", "хм", ERROR_MESSAGE);
                    //System.out.println("File: " + DB_PROPERTIES_FILE + " is missing.");
                    //e.printStackTrace();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE, "хм", ERROR_MESSAGE);
                    //System.out.println("Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE);
                    //e.printStackTrace();
                }
            }
        });

        //---- btnSelectChildren ----
        btnSelectChildren.setText("Children");
        contentPane.add(btnSelectChildren, CC.xy(11, 1));

        btnSelectChildren.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SomeItemRepository repository = new SomeItemRepository();
                    itemList.updateList(repository.getAncestryOrDescendants(cmbTables.getSelectedItem().toString(),
                            itemList.get(mTable.getSelectedRow()).getId(),
                            true));
                    myTableItem.fireTableDataChanged();
                }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Exception has occured during creation of connection", "хм", ERROR_MESSAGE);
                    //System.out.println("Exception has occured during creation of conenction");
                    //e.printStackTrace();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "File: " + DB_PROPERTIES_FILE + " is missing.", "хм", ERROR_MESSAGE);
                    //System.out.println("File: " + DB_PROPERTIES_FILE + " is missing.");
                    //e.printStackTrace();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE, "хм", ERROR_MESSAGE);
                    //System.out.println("Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE);
                    //e.printStackTrace();
                }
            }
        });

        //---- btnSelectAll ----
        btnSelectAll.setText("Reset");
        contentPane.add(btnSelectAll, CC.xy(14, 1));

        btnSelectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SomeItemRepository repository = new SomeItemRepository();
                    itemList.updateList(repository.getAll(cmbTables.getSelectedItem().toString()));
                    myTableItem.fireTableDataChanged();
                    txtParse.setText("");
                }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Exception has occured during creation of connection", "хм", ERROR_MESSAGE);
                    //System.out.println("Exception has occured during creation of conenction");
                    //e.printStackTrace();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "File: " + DB_PROPERTIES_FILE + " is missing.", "хм", ERROR_MESSAGE);
                    //System.out.println("File: " + DB_PROPERTIES_FILE + " is missing.");
                    //e.printStackTrace();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE, "хм", ERROR_MESSAGE);
                    //System.out.println("Exception has occured during loading connection properties from file: " + DB_PROPERTIES_FILE);
                    //e.printStackTrace();
                }
            }
        });

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(mTable);
        }
        contentPane.add(scrollPane1, CC.xywh(1, 3, 14, 1));
        pack();
        setLocationRelativeTo(getOwner());
        //END:initComponents
    }

    private void helpShowItem(Item itm) {
        chgItem dialog = new chgItem(itm);
        dialog.pack();
        dialog.setVisible(true);
    }
}
