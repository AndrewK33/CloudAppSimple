package com.geekbrains.CloudServer;

import com.geekbrains.CloudServer.Interface.Impl.ListDirectoryCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListDirectoryCommandTest {

    ListDirectoryCommand underTest;

    @BeforeEach
    void setUp() {
        underTest = new ListDirectoryCommand();
    }

    @Test
    void execute() {
        String ls = underTest.execute("ls");
        assertFalse(ls.isEmpty());
    }

    @Test
    void test2() {
        String ls = underTest.execute("ls C:\\");
        assertFalse(ls.isEmpty());
    }
}