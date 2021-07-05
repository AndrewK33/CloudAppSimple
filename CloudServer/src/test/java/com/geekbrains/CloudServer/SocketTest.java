package com.geekbrains.CloudServer;

import com.geekbrains.CloudServer.core.ServerApp;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SocketTest {

    private FileServerClient underTest;


    @BeforeAll
    static void beforeAll() {
        new Thread(() -> ServerApp.main(new String[]{})).start();
    }

    @BeforeEach
    void setUp() throws IOException {
        underTest = new FileServerClient();
        underTest.startConnection("localhost", 8189);
    }

    @Test
    void name() throws IOException {
        assertTrue(underTest.ping());

        List<String> ls = underTest.ls();
        assertFalse(ls.isEmpty());
    }

    @Test
    void name2() throws IOException {
        File tmpFile = underTest.cat("pom.xml");

        String pomXml  = FileUtils.readFileToString(tmpFile, StandardCharsets.UTF_8);
        System.out.println(pomXml);

    }
}
