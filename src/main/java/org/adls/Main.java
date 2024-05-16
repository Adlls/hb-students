package org.adls;

import org.adls.menu.CommandOptions;
import org.adls.menu.Menu;
import org.adls.storage.FileReader;
import org.adls.storage.FileWriter;
import org.adls.storage.impl.FileReaderImpl;
import org.adls.storage.impl.FileWriterImpl;
import org.adls.validation.Validation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static final Menu menu = new Menu();
    public static final FileReader fileReader = new FileReaderImpl();
    public static final FileWriter fileWriter = new FileWriterImpl();

    public static final Map<String, List<Integer>> gradeListByStudent = new HashMap<>();
    public static final Validation validation = new Validation();
    public static final CommandExecuteContext commandExecuteContext = new CommandExecuteContext();


    public static void main(String[] args) throws IOException {

        final BufferedReader bufferedReaderInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("load an existing file? [Y/N] (default Y; N - clear an existing file)");

        String input = bufferedReaderInput.readLine().toLowerCase();

        if (input.equals("y") || input.isBlank()) {
            hashMapInitializeFromFile();
        }
        if (input.equals("n")) {
            fileWriter.clearFile();
        }

        System.out.println(menu.getOptionsMenu());

        while (true) {
            final String commandOptionInString = bufferedReaderInput.readLine();
            final Optional<CommandOptions> commandOption = menu.getCommandOptionByOptionInString(commandOptionInString);

            if (commandOption.isEmpty()) {
                System.out.println(commandOptionInString + " argument option is not correct. Try again");
                System.out.println(menu.getOptionsMenu());
                continue;
            }

            if (commandOption.get() == CommandOptions.EXIT) {
                fileWriter.writeFile(gradeListByStudent);
                System.out.println("file saved. bye!");
                return;
            }

            final String optionSubArgsName = menu.getOptionSubArgsName(commandOption.get());
            if (!Objects.isNull(optionSubArgsName)) {
                System.out.println(optionSubArgsName);
            }

            final String subArguments = Objects.isNull(optionSubArgsName) ? null : bufferedReaderInput.readLine();

            final List<String> validationStackTrace = validation.getValidationStackTrace(commandOption.get(), subArguments);

            if (!validationStackTrace.isEmpty()) {
                System.out.println("validation error Cause by: " + validationStackTrace.stream().collect(Collectors.joining("; ")));
                System.out.println(menu.getOptionsMenu());
                continue;
            }

            final String result = commandExecuteContext.executeCommand(commandOption.get(), subArguments, gradeListByStudent);
            System.out.println(">>>\n" + result + "\n<<<");
            System.out.println(menu.getOptionsMenu());
        }

    }

    public static void hashMapInitializeFromFile() {
        Arrays.stream(fileReader.readFile().split("\n")).forEach(line -> {
            final String[] lineSplit = line.split(" ");

            List<Integer> gradeList = Arrays.stream(lineSplit).skip(1)
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(LinkedList::new));

            gradeListByStudent.put(lineSplit[0].trim(), gradeList);

        });
        System.out.println("uploaded data: ");
        gradeListByStudent.forEach((studentName, gradeList) -> System.out.println(studentName + " " + gradeList));
        System.out.println("\n");
    }
}
