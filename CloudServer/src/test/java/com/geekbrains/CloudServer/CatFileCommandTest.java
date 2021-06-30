package com.geekbrains.CloudServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.geekbrains.CloudServer.Command.EOC;
import static org.junit.jupiter.api.Assertions.*;

class CatFileCommandTest {

    CatFileCommand underTest;


    @BeforeEach
    void setUp() {
        underTest = new CatFileCommand();
    }

    @Test
    void execute() {
        String executeResult = underTest.execute("cat C:\\1.txt 0 1024");
        System.out.println(executeResult);

        assertTrue(executeResult.contains(EOC));

    }


    @Test
    void execute2() {
        String executeResult = underTest.execute("cat C:\\1.txt 0 5");
        System.out.println(executeResult);

        executeResult = underTest.execute("cat C:\\1.txt 5 5");
        System.out.println(executeResult);


        executeResult = underTest.execute("cat C:\\1.txt 10 5");
        System.out.println(executeResult);


    }
}