package com.geekbrains.CloudServer.core;

import com.geekbrains.CloudServer.Interface.Command;
import com.geekbrains.CloudServer.Interface.Impl.CatFileCommand;
import com.geekbrains.CloudServer.Interface.Impl.ListDirectoryCommand;
import com.geekbrains.CloudServer.Interface.Impl.PingFileCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {


    private Map<String, Command> commands = new HashMap<String, Command>() {
        {
            put("ls", new ListDirectoryCommand());
            put("cat", new CatFileCommand());
            put("ping", new PingFileCommand());
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
