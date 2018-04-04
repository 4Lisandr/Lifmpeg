package net.asasha.lifmpeg.model;

import net.asasha.lifmpeg.model.video.TimeCode;

/**
 *
 */
public class TimeCodeTest {

    public static void main(String[] args) {

        TimeCode[] timeCodes = {
                new TimeCode((int) (1000*6649/30)),
                new TimeCode(" 00:58:00.489 "),
                new TimeCode(92345555),
                new TimeCode(Integer.MAX_VALUE),
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

        System.out.println(timeCodes[1].compareTo(timeCodes[2]));
        System.out.println(timeCodes[3].compareTo(timeCodes[4]));
        System.out.println(timeCodes[5].compareTo(timeCodes[6]));
        System.out.println(timeCodes[7].compareTo(timeCodes[8]));
        System.out.println(timeCodes[4].compareTo(timeCodes[5]));
        System.out.println(timeCodes[5].compareTo(timeCodes[4]));

    }
}
