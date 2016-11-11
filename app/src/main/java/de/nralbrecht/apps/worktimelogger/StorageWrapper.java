package de.nralbrecht.apps.worktimelogger;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

class StorageWrapper {
    private final String fileName = "date_log";
    private Context context;
    private ArrayList<String> data;

    StorageWrapper(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
        Load();
    }

    void Save() {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);

            for (int i = 0; i < data.size(); i++) {
                fos.write((data.get(i) + "\n").getBytes(Charset.defaultCharset()));
            }

            fos.close();
        } catch (Exception e) {}
    }

    void Load() {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            Scanner scanner = new Scanner(fis);

            ArrayList<String> tempResult = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String temp = scanner.nextLine().trim();

                if (!temp.equals("")) {
                    tempResult.add(temp);
                }
            }

            data.clear();
            data.addAll(tempResult);
        } catch (Exception e) {}
    }

    void addLine(String input) {
        data.add(input);
    }

    void clear() {
        data.clear();
    }

    void updateLine(int index, String newLine) {
        data.set(index, newLine);
        Save();
    }

    ArrayList<String> getAllLines() {
        return data;
    }
}
