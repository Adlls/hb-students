package org.adls.menu;

public enum CommandOptions {
    ADD_STUDENT("a"),
    DELETE_STUDENT("b"),
    UPDATE_GRADE("c"),
    VIEW_ALL_GRADES("d"),
    VIEW_SPECIFIC_GRADES("e"),
    EXIT("exit");

    private final String command;

    CommandOptions(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

}
