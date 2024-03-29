package com.examples.Laba;

public class Command {
    private static final String delimiter = " ";
    CommandType type;
    Object[] args;
    public Command(CommandType type, Object ...args){
        this.type = type;
        this.args = args;
    }

    public static Command fromStr(String cmd) {
        String[] split = cmd.trim().split(delimiter);
        if(split[0] == "GET"){
            return new Command(CommandType.GET, Integer.parseInt(split[1]));
        }
        if(split[0] == "PUT"){
            return new Command(CommandType.PUT, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }
        if(split[0] == "NOTIFY"){
            return new Command(CommandType.NOTIFY, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }
        if(split[0] == "RESULT"){
            return new Command(CommandType.RESULT, Integer.parseInt(split[1]));
        }
        if(split[0] == "OK"){
            return new Command(CommandType.OK, Integer.parseInt(split[1]));
        }
        if(split[0] == "ERROR"){
            return new Command(CommandType.ERROR);
        }
        return null;
    }

    public boolean matchType(CommandType type){
        return this.type == type;
    }

    public int getIndex(){
        return (int) args[0];
    }
    public int getValue(){
        return (int) args[1];
    }
    public int getStart(){
        return (int) args[0];
    }
    public int getEnd(){
        return (int) args[1];
    }
    public int getResult(){
        return (int) args[0];
    }

    public static Command OK(int res) {
        return new Command(CommandType.OK, res);
    }

    public static Command RESULT(int res) {
        return new Command(CommandType.RESULT, res);
    }

    public static Command NOTIFY(int start, int end) {
        return new Command(CommandType.NOTIFY, start, end);
    }

    public static Command ERROR() {
        return new Command(CommandType.ERROR);
    }
}
