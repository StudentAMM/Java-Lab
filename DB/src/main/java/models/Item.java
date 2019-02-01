package models;

import java.sql.Date;

public class Item {
    private int id;
    private String someInfo;
    private Date date;
    private int parentId;

    public Item() {
    }

    public Item(String inf, Date dat) {
        this.someInfo = inf;
        this.date = dat;
    }

    public Item(int id, String info, Date date, int parentId) {
        this.id = id;
        this.someInfo = info;
        this.date = date;
        this.parentId = parentId;
    }

    public int getId() {
        return this.id;
    }

    public String getSomeInfo() {
        return this.someInfo;
    }

    public Date getDate() {
        return this.date;
    }

    public int getParentId() {
        return this.parentId;
    }

    public void setSomeInfo(String info) {
        this.someInfo = info;
    }

    public void setDate(Date dat){
        this.date = dat;
    }

    public void setParentId(int id){
        this.parentId = id;
    }

}
