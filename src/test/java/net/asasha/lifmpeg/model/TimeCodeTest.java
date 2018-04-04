package net.asasha.lifmpeg.model;

/**
 *
 */
public class TimeCodeTest {

    public static void main(String[] args) {

        TimeCode[] timeCodes = {
            new TimeCode(""),
            new TimeCode(" 00:58:00.489 "),
            new TimeCode(" 02 58 45.667 "),
            new TimeCode(" 58 45.4779 "),
            new TimeCode(" 58- 45.367 "),
            new TimeCode(" 45.967 "),
            new TimeCode(" 10.567 "),
            new TimeCode(" .567 ")
        };

        for (TimeCode t: timeCodes) {
            System.out.println(t.toString());
            System.out.println(t.toShortString());
        }

    }
}
