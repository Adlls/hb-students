package org.adls.storage;

import org.adls.storage.FileConstants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileStudentWriter {

    public void writeFile(Map<String, List<Integer>> map) {
        File file = new File(FileConstants.pathFile);

        try (FileWriter fileWriter = new FileWriter(file);
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

    public void clearFile() {
        try (java.io.FileWriter writer = new java.io.FileWriter(FileConstants.pathFile)) {
            writer.write("");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
