package net.asasha.lifmpeg.model;

import net.asasha.lifmpeg.model.video.timeline.Marker;
import net.asasha.lifmpeg.model.video.timeline.TimeCode;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TimeCodeTest {

    public static void main(String[] args) {

        Path path = Paths.get("..");

        TimeCode[] timeCodes = {
                new Marker((int) (1000*6649/30)),
                new Marker(" 00:58:00.489 "),
                new Marker(92345555),
                new Marker(Integer.MAX_VALUE),
                new Marker(" 58 45.4779 "),
                new Marker(" 58- 45.367 "),
                new Marker(" 45.967 "),
                new Marker(" 10.567 "),
                new Marker(" .567 ")
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
