package org.adls;


import org.adls.menu.CommandOptions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.adls.menu.CommandOptions.ADD_STUDENT;
import static org.adls.menu.CommandOptions.DELETE_STUDENT;
import static org.adls.menu.CommandOptions.UPDATE_GRADE;
import static org.adls.menu.CommandOptions.VIEW_ALL_GRADES;
import static org.adls.menu.CommandOptions.VIEW_SPECIFIC_GRADES;

public class CommandExecuteContext {

    private final Map<CommandOptions, BiFunction<Map<String, List<Integer>>, String, String>> EXECUTE_BY_COMMAND = Map.of(
            ADD_STUDENT, addStudent(),
            DELETE_STUDENT, deleteStudent(),
            UPDATE_GRADE, updateGrade(),
            VIEW_ALL_GRADES, viewAllGrade(),
            VIEW_SPECIFIC_GRADES, viewSpecificGrade()
    );


    public String executeCommand(CommandOptions commandOptions, String args, Map<String, List<Integer>> hashMap) {
        return EXECUTE_BY_COMMAND.get(commandOptions).apply(hashMap, args);
    }


    private BiFunction<Map<String, List<Integer>>, String, String> addStudent() {
        return (hashMap, args) -> {
            final var argsSplit = args.split(" ");

            final var studentName = argsSplit[0];
            final var grades = Arrays.stream(argsSplit).skip(1)
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(LinkedList::new));

            hashMap.put(studentName, grades);

            return "student with name " + studentName + " added successfully";
        };
    }

    private BiFunction<Map<String, List<Integer>>, String, String> deleteStudent() {
        return (hashMap, args) -> {
            final var gradeList = hashMap.get(args);
            if (gradeList == null) {
                return "Student named " + args + " was not found";
            }

            hashMap.remove(args);
            return "student with name " + args + " removed successfully";
        };
    }

    private BiFunction<Map<String, List<Integer>>, String, String> updateGrade() {
        return (hashMap, args) -> {
            // вводить имя студента, номер оценки и новое значение (student_name grade_number new_value)
            // если не существует такой оценки - действий не будет

            final var argsSplit = args.split(" ");

            final var studentName = argsSplit[0];
            final var gradeNumber = Integer.parseInt(argsSplit[1]);
            final var newGradeValue = Integer.parseInt(argsSplit[2]);

            final var gradeList = hashMap.get(studentName);
            if (gradeList == null) {
                return "student named " + studentName + " was not found";
            }
            final var indexGrade = gradeNumber - 1;
            if (0 > indexGrade || indexGrade > gradeList.size()) {
                return "grade with number " + gradeNumber + " was not found";
            }
            final var oldGrade = gradeList.set(indexGrade, newGradeValue);

            return "a grade of " + oldGrade + " was updated to a grade of " + newGradeValue + " for a student with name " + studentName;
        };
    }

    private BiFunction<Map<String, List<Integer>>, String, String> viewAllGrade() {
        return (hashMap, args) -> {
            final var result = new StringBuilder();

            hashMap.forEach((studentName, gradeList) -> result.append(studentName).append(" ").append(gradeList).append("\n"));

            if (result.toString().isBlank()) {
                return "student list empty";
            }

            return result.toString().trim();
        };
    }

    private BiFunction<Map<String, List<Integer>>, String, String> viewSpecificGrade() {
        return (hashMap, args) -> {
            final var gradeList = hashMap.get(args);

            if (gradeList == null) {
                return "Student named " + args + " was not found";
            }

            if (gradeList.isEmpty()) {
                return "grade list empty for student " + args;
            }

            return args + " " + gradeList.stream().map(String::valueOf).collect(Collectors.joining(", ")).trim();
        };
    }

}
