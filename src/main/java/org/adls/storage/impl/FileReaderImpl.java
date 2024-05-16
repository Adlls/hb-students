package org.adls.storage.impl;

import org.adls.storage.FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileReaderImpl implements FileReader {

    @Override
    public String readFile() {
        File file = new File(pathFile);
        try (java.io.FileReader fileReader = new java.io.FileReader(file);
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
