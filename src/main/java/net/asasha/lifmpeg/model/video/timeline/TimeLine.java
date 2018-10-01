package net.asasha.lifmpeg.model.video.timeline;


import net.asasha.lifmpeg.model.video.load.XmlVideoLoader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TimeLine {
    /**
     * @timebase - frame rate, default value is 30
     * @duration - length in number of frames
     */
    private int timebase = 30;
    private int duration;
    private ArrayList<TimeCode> timeCodes = new ArrayList<>();
    private List<String> descriptions = new ArrayList<>();;
    private ArrayList<PartOfVideo> parts = PartOfVideo.getAllParts();


    public TimeLine(int duration, int timebase) {
        this.duration = duration;
        this.timebase = timebase;
    }

    public static TimeLine loadFromXml(String fileName) throws IOException, SAXException, ParserConfigurationException {
        XmlVideoLoader xml = new XmlVideoLoader(fileName);

        TimeLine timeLine = new TimeLine(xml.getDuration(), xml.getTimebase());
        timeLine.addAllTimeCodes(xml.getFrames());

        return timeLine;
    }

    public void addAllTimeCodes(ArrayList<Integer> frames) {
        for (int frame : frames) {
            timeCodes.add(new Marker(calcMSecOfFrames(frame)));
        }
    }

    // фабрика частей видео
    public void doTimeLineMarkup() {
        for (int i = 0; i < timeCodes.size(); i++) {
            TimeCode t = timeCodes.get(i);
            if (descriptions.size()> i)
                t.setDescription(descriptions.get(i));
            new PartOfVideo((Marker) t);
        }
}

    private int calcMSecOfFrames(int frame) {
        return Math.round((1000 * frame) / this.timebase * 1f);
    }

    public ArrayList<TimeCode> getTimeCodes() {
        return timeCodes;
    }

    public void printTimeCodes() {
        System.out.println(toString());
    }

    public void printPartsOfVideo() {
        final String LINE = "----------------------------------------------------------------------------";
        final String TITLE = "Id\t    From    \t     To     \t Round  \t Length  \t Position\t Description";

        parts = PartOfVideo.getAllParts();
        System.out.println(LINE);
        System.out.println(TITLE);
        System.out.println(LINE);
        for (PartOfVideo part : parts) {
            System.out.println(part);
        }
        System.out.println(LINE);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (TimeCode t : timeCodes) {
            sb.append(t).append("\t \t").append(t.toShortString()).append("\n");
        }

        sb.append("=== Length of video ========").append("\n");

        TimeCode length = new Marker(calcMSecOfFrames(duration));
        sb.append(length.toString()).append("\t \t").append(length.toShortString());

        return sb.toString();
    }


    private static String askUser(String query) throws IOException {
        BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
        System.out.println(query);
        return reader.readLine();
    }

    private static List<String> takeDescriptions(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<String> lines = new ArrayList<String>();
        String line;
        while((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    private void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        TimeLine tl = loadFromXml(askUser("Input path to XML file. [C:\\directories\\...\\file.xml]"));
        tl.setDescriptions(takeDescriptions(askUser("File with descriptions for video (UTF-8):")));

        tl.doTimeLineMarkup();
        tl.printPartsOfVideo();
    }
}
