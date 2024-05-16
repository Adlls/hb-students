package org.adls.validation;

import org.adls.menu.CommandOptions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class Validation {

    private final Map<CommandOptions, List<Function<String, String>>> errorHandlerByCommandOptions = Map.of(
            CommandOptions.ADD_STUDENT, List.of(validateStudentName(), validateGrade(), validateArgumentCount(2)),
            CommandOptions.UPDATE_GRADE, List.of(validateStudentName(), validateGrade(), validateArgumentCount(2)),
            CommandOptions.DELETE_STUDENT, List.of(validateStudentName(), validateArgumentCount(1, 1)),
            CommandOptions.VIEW_SPECIFIC_GRADES, List.of(validateStudentName(), validateArgumentCount(1, 1))
    );

    public List<String> getValidationStackTrace(CommandOptions commandOptions, String subArgs) {
        if (!errorHandlerByCommandOptions.containsKey(commandOptions)) {
            return List.of();
        }
        return errorHandlerByCommandOptions.get(commandOptions).stream()
                .map(function -> function.apply(subArgs.trim()))
                .filter(Objects::nonNull)
                .toList();
    }

    private Function<String, String> validateStudentName() {
        return (args) -> {
            if (args.isBlank()) {
                return "name must not be empty";
            }
            final String studentName = args.split(" ")[0];
            for (char ch : studentName.toCharArray()) {
                if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))) {
                    return "name must be Latin";
                }
            }
            return null;
        };
    }

    private Function<String, String> validateGrade() {
        return (args) -> {
            final List<String> grades = Arrays.stream(args.split(" ")).skip(1).toList();

            if (grades.isEmpty()) {
                return "grades must not be empty";
            }

            final var gradeOneIsEmpty = grades.stream().filter(Predicate.not(grade -> !grade.isBlank())).toList();
            if (!gradeOneIsEmpty.isEmpty()) {
                return "grade must not be empty";
            }

            final var gradesWithIncorrectLength = grades.stream().filter(Predicate.not(grade -> grade.length() == 1)).toList();
            if (!gradesWithIncorrectLength.isEmpty()) {
                return "grade must be from 1 to 5";
            }

            final var gradesWithIncorrectPeriod = grades.stream()
                    .map(grade -> grade.toCharArray()[0])
                    .filter(Predicate.not(grade -> grade >= '1' && grade <= '5'))
                    .toList();
            if (!gradesWithIncorrectPeriod.isEmpty()) {
                return "grade must be from 1 to 5";
            }

            return null;
        };
    }


    private Function<String, String> validateArgumentCount(int argsMinCountShouldBe) {
        return (args) -> {
            final int argsCount = args.split(" ").length;
            if (!(argsCount >= argsMinCountShouldBe)) {
                return "number of arguments must be minimum " + argsMinCountShouldBe;
            }
            return null;
        };
    }

    private Function<String, String> validateArgumentCount(int argsMinCountShouldBe, int argsMaxCountShouldBe) {
        return (args) -> {
            final int argsCount = args.split(" ").length;
            if (!(argsCount >= argsMinCountShouldBe && argsMaxCountShouldBe >= argsCount)) {
                return argsMinCountShouldBe == argsMaxCountShouldBe ? "number of arguments must be " + argsMinCountShouldBe
                        : "number of arguments must be from " + argsMinCountShouldBe + " to " + argsMaxCountShouldBe;
            }
            return null;
        };
    }
}

