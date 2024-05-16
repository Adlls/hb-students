package org.adls.storage;

import org.adls.storage.FileConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileStudentReader {

    public String readFile() {
        File file = new File(FileConstants.pathFile);
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String currentLine = bufferedReader.readLine();
            final StringBuilder result = new StringBuilder();

            while (currentLine != null) {
                result.append(currentLine.trim()).append("\n");
                currentLine = bufferedReader.readLine();
            }
            return result.toString().trim();

        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }

    }

}
