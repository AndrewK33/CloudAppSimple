package com.geekbrains.CloudServer.Interface;

public interface Command {

    String EOC = ">>>>>>>>>>>>EOC\n";

    String execute(String line);

}
