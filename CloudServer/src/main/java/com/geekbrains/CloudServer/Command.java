package com.geekbrains.CloudServer;

public interface Command {

    String EOC = ">>>>>>>>>>>>EOC";

    String execute(String line);

}
