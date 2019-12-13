package com.examples.Laba;

public class Command {
    CommandType type;
    Object[] args;
    public Command(CommandType type, Object ...args){
        this.type = type;
        this.args = args;
    }

    public static Command fromStr(String cmd) {
        String[] split = cmd.trim().split(" ");
    }
}
