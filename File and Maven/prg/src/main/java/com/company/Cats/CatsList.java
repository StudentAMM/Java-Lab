package com.company.Cats;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CatsList {
    private ArrayList<Cat> cats;

    public CatsList() {
        this.cats = new ArrayList<Cat>();
    }

    public void add(Cat cat) {
        this.cats.add(cat);
    }

    public void set(int ind, Cat cat) {
        this.cats.set(ind, cat);
    }

    public void remove(int ind) {
        this.cats.remove(ind);
    }

    public void clear(){
        this.cats.clear();
    }

    public Cat get(int ind){
        return this.cats.get(ind);
    }

    public int size(){
        return this.cats.size();
    }

    public void jsonToArray(JsonArray jsonArray){
        Type listType = new TypeToken<List<Cat>>(){}.getType();
        this.cats = new Gson().fromJson(jsonArray, listType);
    }

    public JsonArray listCatsToJson(){
        //JsonArray arr = Json.createArrayBuilder().build();
       /* JsonArray arr = Json.createArrayBuilder().add(cats.get(0).catToJson()).build();
        for (Cat cat : this.cats) {
            arr.add(cat.catToJson());
        }
        return arr;*/
        Gson gson = new Gson();

        JsonElement element =
                gson.toJsonTree(this.cats , new TypeToken<List<Cat>>() {}.getType());

        return element.getAsJsonArray();
    }


}
