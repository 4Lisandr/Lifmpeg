package net.asasha.lifmpeg.model;

/**
 *
 */
public class TimeCodeTest {

    public static void main(String[] args) {

        TimeCode[] timeCodes = {
            new TimeCode(""),
            new TimeCode(" 00:58:45.367 "),
            new TimeCode(" 02 58 45.667 "),
            new TimeCode(" 58 45.367 "),
            new TimeCode(" 58 -45.367 "),
            new TimeCode(" -45.367 "),
            new TimeCode(" 0.567 "),
            new TimeCode(" .567 ")
        };

        for (TimeCode t: timeCodes) {
            System.out.println(t.calcExactCode());
            System.out.println(t.calcUserCode());
        }
    }
}
