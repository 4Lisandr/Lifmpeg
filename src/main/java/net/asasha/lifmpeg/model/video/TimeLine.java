package net.asasha.lifmpeg.model.video;


import net.asasha.lifmpeg.model.video.load.XmlLoaderOfTimeCodes;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class TimeLine {
    /**
     * @timebase - frame rate, default value is 30
     * @duration - length in number of frames
     */
    private int timebase = 30;
    private int duration;
    private ArrayList <TimeCode> timeCodes = new ArrayList<TimeCode>();
    private ArrayList <PartOfVideo> parts = PartOfVideo.getAllParts();


    public TimeLine(int duration, int timebase) {
        this.duration = duration;
        this.timebase = timebase;
    }

    public static TimeLine loadFromXml(String fileName) throws IOException, SAXException, ParserConfigurationException {
        XmlLoaderOfTimeCodes xml = new XmlLoaderOfTimeCodes(fileName);

        TimeLine timeLine = new TimeLine(xml.getDuration(), xml.getTimebase());
        timeLine.addAllTimeCodes(xml.getFrames());

        return timeLine;
    }

    public void addAllTimeCodes(ArrayList<Integer> frames) {
        for (int frame: frames) {
            addTimeCode(frame);
        }
    }

    public void addTimeCode(int frame) {
        timeCodes.add(new TimeCode(calcMSecOfFrames(frame)));
    }

    public void doTimeLineMarkup(){
        for (TimeCode t: timeCodes) {
            new PartOfVideo(t);
        }
    }

    private int calcMSecOfFrames(int frame) {
        return Math.round ((1000* frame)/ this.timebase *1f);
    }

    public ArrayList<TimeCode> getTimeCodes() {
        return timeCodes;
    }

    public void printTimeCodes(){
        System.out.println(toString());
    };

    public void  printPartsOfVideo(){
        doTimeLineMarkup();
        parts = PartOfVideo.getAllParts();
        for (PartOfVideo p:parts) {
            p.print();
        }
    };

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (TimeCode t:timeCodes) {
            sb.append(t).append("\t \t").append(t.toShortString()).append("\n");
        }

        sb.append("=== Length of video ========").append("\n");

        TimeCode length = new TimeCode(calcMSecOfFrames(duration));
        sb.append(length.toString()).append("\t \t").append(length.toShortString());

        return sb.toString();
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        loadFromXml("a:\\1_INBOX\\jgc.xml").
                printPartsOfVideo();
    }

}
