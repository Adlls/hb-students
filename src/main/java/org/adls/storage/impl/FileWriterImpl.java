package org.adls.storage.impl;

import org.adls.storage.FileWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileWriterImpl implements FileWriter {

    public void writeFile(Map<String, List<Integer>> map) {
        File file = new File(pathFile);

        try (java.io.FileWriter fileWriter = new java.io.FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            final var result = new StringBuilder();

            map.forEach((studentName, gradeList) ->
                    result
                            .append(studentName).append(" ")
                            .append(gradeList.stream().map(String::valueOf).collect(Collectors.joining(" ")))
                            .append("\n"));

            bufferedWriter.write(result.toString().trim());

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void clearFile() {
        try (java.io.FileWriter writer = new java.io.FileWriter(pathFile)) {
            writer.write("");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
