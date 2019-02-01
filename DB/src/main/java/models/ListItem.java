package models;

//public class listItem {
//}
import java.util.List;

import java.util.ArrayList;

public class ListItem {
    private ArrayList<Item> items;

    public ListItem() {
        this.items = new ArrayList<Item>();
    }

    /*public listItem(List list){
        items =new ArrayList<Item>(list);
    }*/
    public void updateList(List list){
        items.clear();
        items.addAll(list);
    }

    public void add(Item Item) {
        this.items.add(Item);
    }

    public void set(int ind, Item Item) {
        this.items.set(ind, Item);
    }

    public void remove(int ind) {
        this.items.remove(ind);
    }

    public void clear(){
        this.items.clear();
    }

    public Item get(int ind){
        return this.items.get(ind);
    }

    public int size(){
        return this.items.size();
    }



}
