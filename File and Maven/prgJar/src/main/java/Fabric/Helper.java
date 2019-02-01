package Fabric;

import java.io.File;

import com.google.gson.JsonArray;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

public class Helper {

    public FileManager getManager(File file) {
        String h = file.toString();
        String ext = h.substring(h.indexOf('.') + 1);
        if (ext.equals("json")) {
            return new JSONManager();
        }
        if (ext.equals("csv")) {
            return new CSVManager();
        }
        if (ext.equals("xml")) {
            return new XMLManager();
        }
        return null;
    }

    public void strategy(File file, JsonArray arr) throws IOException {
        getManager(file).saveToFile(file, arr);
    }

    public JsonArray strategy(File file) throws IOException {
        Object help = getManager(file);
        if(help != null){
            FileManager fl = (FileManager)help;
            return fl.loadFromFile(file);
        }
        else
        {
            throw new NoSuchElementException();
        }
    }

}
