package com.pitroq.kevinmobile;

import android.content.Context;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.*;

public class JsonFileManager {
    protected String dirPath;
    protected String fileName;
    protected String filePath;
    protected File file;
    protected Context context;

    public JsonFileManager(String fileName, Context context) {
        this.context = context;
        this.fileName = fileName;
        dirPath = context.getFilesDir().toString();
        filePath = dirPath + File.separatorChar + fileName;
        file = new File(filePath);
    }

    public static boolean isJsonValid(String json) {
        if (json.isEmpty()) return false;

        try {
            JsonParser.parseString(json);
        }
        catch (JsonSyntaxException e) {
            return false;
        }
        return true;
    }

    public void saveFileContent(String content) {
        try (FileOutputStream stream = new FileOutputStream(file)) {
            stream.write(content.getBytes());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFileContent() {
        int length = (int) file.length();

        byte[] bytes = new byte[length];


        try (FileInputStream in = new FileInputStream(file)) {
            in.read(bytes);
        }
        catch (IOException ignored) {}

        return new String(bytes);
    }
}

