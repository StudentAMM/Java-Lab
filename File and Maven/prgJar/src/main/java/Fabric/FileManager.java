package Fabric;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonArray;

import java.io.*;

public abstract class FileManager {
    //метод сохранения
    public abstract void saveToFile(File file, JsonArray arr) throws IOException;

    //метод загрузки
    public abstract JsonArray loadFromFile(File file) throws IOException;

    protected String readFileToString(File file) throws  FileNotFoundException, IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }

    protected static void writeStringToFile(File file, String data) throws IOException {
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            throw new IOException();
        } finally {
            try {
                fr.close();
            } catch (IOException e) {
                throw new IOException();
            }
        }
    }
}
