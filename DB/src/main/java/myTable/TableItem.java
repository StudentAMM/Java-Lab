package myTable;

import models.ListItem;

import javax.swing.table.AbstractTableModel;

public class TableItem extends AbstractTableModel {
    private ListItem itemList;

    public TableItem(ListItem items) {
        this.itemList = items;
    }

    public int getRowCount() {
        return this.itemList.size();
    }

    public int getColumnCount() {
        return 4;
    }

    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return this.itemList.get(r).getId();
            case 1:
                return this.itemList.get(r).getSomeInfo();
            case 2:
                return this.itemList.get(r).getDate();
            case 3:
                return this.itemList.get(r).getParentId();
            default:
                return "";
        }
    }

    public String getColumnName(int c) {
        switch (c) {
            case 0:
                return "id";
            case 1:
                return "Info";
            case 2:
                return "Date";
            case 3:
                return "ParentId";
            default:
                return "Другие";
        }
    }
}
