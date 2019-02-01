package Fabric;

import java.io.*;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;


public class CSVManager extends FileManager {
    @Override
    public void saveToFile(File file, JsonArray arr) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> helpMap = gson.fromJson(arr.get(0), type);
        List<String> listKeys = new ArrayList<String>(helpMap.keySet());

        StringBuilder sb = new StringBuilder();
        //добавление ключей
        for (int i = 0; i < listKeys.size() - 1; i++) {
            sb.append(listKeys.get(i));
            sb.append(";");
        }
        sb.append(listKeys.get(listKeys.size() - 1));
        sb.append("\n");
        // добавление элементов
        for (JsonElement je : arr) {
            Map<String, String> myMap = gson.fromJson(je, type);
            for (int i = 0; i < listKeys.size() - 1; i++) {
                sb.append(myMap.get(listKeys.get(i)));
                sb.append(";");
            }
            sb.append(myMap.get(listKeys.get(listKeys.size() - 1)));
            sb.append("\n");
        }
        // запись в файл
        writeStringToFile(file, sb.toString());
        //CSVWriter writer = new CSVWriter(new FileWriter(file, true));
        //writer.writeNext();
        //writer.close();
    }

    @Override
    public JsonArray loadFromFile(File file) throws IOException {
        // String [] record = "3,David,Feezor,USA,40".split(",");
        String str = readFileToString(file);
        // получаем строки в которых элеиенты разделены ;
        String[] record = str.split("\n");
        //
        JsonArray resArr;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (record.length > 0) {
            String[] keys = record[0].split(";");
            //
            for (int i = 1; i < record.length-1; i++) {
                String[] elements = record[i].split(";");
                Map<String, String> myMap = new HashMap<String, String>();
                // собираю мапу
                for(int j = 0; j < elements.length; j++){
                    myMap.put(keys[j],elements[j]);
                }
                String jsonStr = new Gson().toJson(myMap);
                sb.append(jsonStr);
                sb.append(",");
            }
            String[] elements = record[record.length-1].split(";");
            Map<String, String> myMap = new HashMap<String, String>();
            // собираю мапу
            for(int j = 0; j < elements.length; j++){
                myMap.put(keys[j],elements[j]);
            }
            String jsonStr = new Gson().toJson(myMap);
            sb.append(jsonStr);
        }
        sb.append("]");
        JsonParser parser = new JsonParser();
        JsonElement elements = parser.parse(sb.toString());
        JsonArray jarr = elements.getAsJsonArray();
        return jarr;
    }
}
