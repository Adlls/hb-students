package org.adls.menu;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public final class Menu {

    final Map<CommandOptions, String> optionMenuNameByCommandOptions = Map.of(
            CommandOptions.ADD_STUDENT, "Добавьте нового ученика",
            CommandOptions.DELETE_STUDENT, "Удалите ученика",
            CommandOptions.UPDATE_GRADE, "Обновите оценку ученика",
            CommandOptions.VIEW_ALL_GRADES, "Просмотр оценок всех учащихся",
            CommandOptions.VIEW_SPECIFIC_GRADES, "Просмотр оценок конкретного учащегося",
            CommandOptions.EXIT, "Выйти и сохранить");

    final Map<CommandOptions, String> subArgsMenuNameByCommandOptions = Map.of(
            CommandOptions.ADD_STUDENT, "enter student name: ",
            CommandOptions.DELETE_STUDENT, "enter student name: ",
            CommandOptions.VIEW_SPECIFIC_GRADES, " enter student name: ",
            CommandOptions.UPDATE_GRADE, " enter student name, grade number, new value separated by space (student_name grade_number new_value): "
    );

    public String getOptionsMenu() {
        final StringBuilder result = new StringBuilder();

        Arrays.stream(CommandOptions.values())
                .forEach(option -> result.append(option.getCommand())
                        .append(".")
                        .append(" ")
                        .append(optionMenuNameByCommandOptions.get(option))
                        .append("\n"));

        return result.toString().trim();
    }

    public Optional<CommandOptions> getCommandOptionByOptionInString(String option) {
        return Arrays.stream(CommandOptions.values())
                .filter(value -> value.getCommand().equals(option))
                .findFirst();
    }

    public String getOptionSubArgsName(CommandOptions commandOptions) {
        if (!subArgsMenuNameByCommandOptions.containsKey(commandOptions)) {
            return null;
        }
        return subArgsMenuNameByCommandOptions.get(commandOptions);
    }

}
