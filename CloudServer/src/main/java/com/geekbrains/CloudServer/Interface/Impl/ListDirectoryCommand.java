package com.geekbrains.CloudServer.Interface.Impl;

import com.geekbrains.CloudServer.Interface.Command;

import java.io.File;

public class ListDirectoryCommand implements Command {

    @Override
    public String execute(String line) {
        String[] args = line.split(" ");
        String path  = ".";
        if (2==args.length){
            path = args[1];
        }

        File file = new File(path);
        if (file.isDirectory()) {
            File[] values = file.listFiles();
            StringBuilder response = new StringBuilder();
            for (File subFile : values) {
                response.append("\r\n");
                response.append(subFile.getAbsolutePath());
            };
            response.append(EOC);

            return response.toString();
        } else {
            return "not is a directory";
        }
    }
}
