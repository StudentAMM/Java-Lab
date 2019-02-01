package Fabric;

import java.io.File;
import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class JSONManager extends FileManager {
    @Override
    public void saveToFile(File file, JsonArray arr) throws IOException {
        writeStringToFile(file, arr.toString());
    }

    @Override
    public JsonArray loadFromFile(File file) throws IOException {
        String str = readFileToString(file);
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(str);
        JsonArray arr = element.getAsJsonArray();
        return arr;
    }


}
