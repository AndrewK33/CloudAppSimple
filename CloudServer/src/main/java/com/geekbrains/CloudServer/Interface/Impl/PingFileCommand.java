package com.geekbrains.CloudServer.Interface.Impl;
import com.geekbrains.CloudServer.Interface.Command;



public class PingFileCommand implements Command {

    @Override
    public String execute(String line) {
        return "pong\n";
    }
}
