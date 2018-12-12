package net.asasha.lifmpeg.model;

import net.asasha.lifmpeg.model.video.timeline.TimeLine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TimeLineTest {


    // 2:02:20 6. 6. 3. 2. Что дают дейли-репорты. --->
    /*				<marker>
                    <name>
					</name>
					<in>19845</in>
					<out>-1</out>
					<color>
						<alpha>0</alpha>
						<red>255</red>
						<green>127</green>
						<blue>0</blue>
					</color>
				</marker>
				*/
    public String toXml(String input) {
        return "XML";
    }

    public static void main(String[] args) throws IOException {

        Files.lines(Paths.get("a:\\1_INBOX\\data.txt"), StandardCharsets.UTF_8)
                .forEach(s -> System.out.println(s + " " + parseTimeCode(s)));

    }

    private static int parseTimeCode(String s) {
        if (s.isEmpty())
            return -1;

        TimeLine timeLine = new TimeLine(0, 30);
        String[] content = s.split(" ");
        return timeLine.calcFrameOfCode(content[0]);
    }
}
