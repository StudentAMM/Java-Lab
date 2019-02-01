package Fabric;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import java.io.File;
import java.io.IOException;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


public class XMLManager extends FileManager{
    @Override
    public void saveToFile(File file, JsonArray arr) throws IOException {
        //!!!!!!!!
        JSONObject json = new JSONObject("{" +"\""+"a"+"\""+":"+arr.toString()+"}");
        String xml = XML.toString(json);
        writeStringToFile(file, xml);
    }

    @Override
    public JsonArray loadFromFile(File file) throws IOException {
        JSONObject xmlJSONObj = XML.toJSONObject(readFileToString(file));
        String jPPS = xmlJSONObj.toString(4);
        JsonParser parser = new JsonParser();
        //!!!!!!!!!
        JsonElement element = parser.parse(jPPS.substring(jPPS.indexOf("[")-1,jPPS.indexOf("]")+1));
        JsonArray arr = element.getAsJsonArray();
        return arr;
    }
}
