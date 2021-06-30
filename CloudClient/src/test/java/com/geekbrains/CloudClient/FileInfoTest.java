package com.geekbrains.CloudClient;


import com.github.robtimus.filesystems.ftp.FTPEnvironment;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

class FileInfoTest {

    @Test
    void getFilename() throws  Exception{
        FTPEnvironment env = new FTPEnvironment()
                .withCredentials("4637_ftptest", "x!4SH7-m#v".toCharArray());
        FileSystem fs = FileSystems.newFileSystem(URI.create("ftp://ftp.selcdn.ru"), env);
        if (fs.isOpen()){
            Path path = fs.getPath("ftptest");
            List<Path> collect = Files.walk(path).collect(Collectors.toList());
            System.out.println(collect);
        }

    }
}