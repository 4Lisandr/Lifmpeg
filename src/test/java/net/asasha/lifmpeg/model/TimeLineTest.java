package net.asasha.lifmpeg.model;

import net.asasha.lifmpeg.model.video.timeline.TimeLine;

public class TimeLineTest {
    public static TimeLine timeLine = new TimeLine(0, 30);

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
    public String toXml(String input){
        return "XML";
    }

    public static void main(String[] args) {
        System.out.println(timeLine.calcFrameOfCode("0:10:00"));
        System.out.println(timeLine.calcFrameOfMSec(1000));
    }
}
