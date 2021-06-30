package com.geekbrains.CloudServer;

import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {


    private Map<String, Command> commands = new HashMap<String, Command>() {
        {
            put("ls", new ListDirectoryCommand());
            put("cat", new CatFileCommand());
        }
    };


    public String process(String line) {
        String commandName = line.substring(0,line.indexOf(" "));
        Command command = commands.get(commandName);
        if (null != command) {
            return command.execute(line);
        }else{
            return "command is not exist \r\n";
        }
    }
}
