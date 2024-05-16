package org.adls.storage;

import java.util.List;
import java.util.Map;

public interface FileWriter extends File {
    void writeFile(Map<String, List<Integer>> map);
    void clearFile();
}
