package com.company.myForm.MyTable;
import com.company.Cats.CatsList;

import javax.swing.table.AbstractTableModel;

public class MyTableCat extends AbstractTableModel {
    private CatsList catsList;

    public MyTableCat(CatsList cats){
        this.catsList = cats;
    }

    public int getRowCount() {
        return this.catsList.size();
    }

    public int getColumnCount() {
        return 2;
    }

    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return this.catsList.get(r).getName();
            case 1:
                return this.catsList.get(r).getBreed();
            default:
                return "";
        }
    }

    public String getColumnName(int c) {
        switch (c) {
            case 0:
                return "Имя кошки";
            case 1:
                return "Порода";
            default:
                return "Другие";
        }
    }
}
