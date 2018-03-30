package net.asasha.lifmpeg.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test
 */
public class FindTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void init() throws Exception {

    }

    @Test
    public void positive() throws Exception {
        createAnImageTest();
    }

    private void createAnImageTest() {
        String cmd = "cmd /c start /min ffmpeg -i \"%s\" -ss \"%s\" -vframes 1 \"%s\"";
        String[] options = new String[]{"in", "time", "out"};
        System.out.println(String.format(cmd, "in", "time", "out"));
        System.out.println(String.format(cmd, (Object[]) options));
    }

    @Test
    public void negative() throws Exception {

    }

}