package com.zy.jena;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class ReadOwlTest {

    @Test
    public void readOwl() throws FileNotFoundException {
        ReadOwl readOwl = new ReadOwl();
        readOwl.readOwl();
    }
}