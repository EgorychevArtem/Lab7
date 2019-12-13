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
        if(split[0] == "GET"){
            return new Command(CommandType.GET, Integer.parseInt(split[1]));
        }
        if(split[0] == "PUT"){
            return new Command(CommandType.PUT, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }
        if(split[0] == "NOTIFY"){
            return new Command(CommandType.NOTIFY, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }
    }
}
