package net.asasha.lifmpeg.model.video.timeline;


import net.asasha.lifmpeg.model.video.XML.XmlVideoLoader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//части видео лежат на монтажной линии редактора
//нам понадобится id генератор для частей видео
// нужно выделить фрагменты ренлеринга, содержащие части видео
// пусть это будет Sequence - класс, содержащий непрерывную последовательность частей видео
public class TimeLine {
    /**
     * @timebase - frame rate, default value is 30
     * @duration - length in number of frames
     */
    private int timebase = 30;
    private int duration;
    private ArrayList<TimeCode> timeCodes = new ArrayList<>();


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
        frames.forEach(f -> timeCodes.add(new Marker(calcMSecOfFrames(f))));
    }

    // фабрика частей видео
    public void doTimeLineMarkup(List<String> descriptions) {
        for (int i = 0; i < timeCodes.size(); i++) {
            TimeCode t = timeCodes.get(i);
            if (descriptions.size() > i)
                t.setDescription(descriptions.get(i));
            new PartOfVideo((Marker) t);
        }
    }

    private int calcMSecOfFrames(int frame) {
        return Math.round((1000 * frame) / this.timebase * 1f);
    }

    public static int calcFrameOfString(String timeCode){
        int base = 30;
        return Math.round(TimeCode.parseCode(timeCode)* base /1000f);
    }

    public int calcFrameOfMSec(int mSec){
        return Math.round(mSec*this.timebase/1000f);
    }

    public int calcFrameOfCode(String timeCode){
        return calcFrameOfMSec(new Marker(timeCode).milliSeconds);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        timeCodes.forEach(t ->
                sb.append(t).append("\t \t").append(t.toShortString()).append("\n"));

        sb.append("=== Length of video ========").append("\n");

        TimeCode length = new Marker(calcMSecOfFrames(duration));
        sb.append(length.toString()).append("\t \t").append(length.toShortString());

        return sb.toString();
    }


    private static String askUser(String query) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(query);
        return reader.readLine();
    }

    private static List<String> takeDescriptions(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<String> lines = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            line = trimDescription(line);
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    private static String trimDescription(String line) {
        if(line.startsWith("."))
            return line.trim();

        Pattern p = Pattern.compile("\\p{L}");
        Matcher m = p.matcher(line);
        if (m.find()) {
            line = line.substring(m.start());
        }
        return line.trim();
    }


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        //todo - спросить у юзера нужен ли автодетект в дефолтной папке или задать файл вручную
        TimeLine tl = loadFromXml(askUser("Input path to XML file. [a:\\3_WORK\\VIDEO\\Juja\\Todo\\tmp\\file.xml]"));
        tl.doTimeLineMarkup(takeDescriptions(askUser("File with descriptions for video (UTF-8):")));

        PartOfVideo.printAllParts();

        Integer[] terminators = Pattern.compile(",")
                .splitAsStream(askUser("What parts of video are Terminators [0, 7, 13, 20 ...]\",\" coma separator"))
                .map(String::trim)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        PartOfVideo.setTerminators((terminators));
        PartOfVideo.printTerminators();
        System.out.println("----------");
        PartOfVideo.printFfmpegCommands();
        if (askUser("Render video?").equalsIgnoreCase("Y")) {
            String path = askUser("Input path to video [a:\\3_WORK\\VIDEO\\Juja\\Todo\\OOPJava\\OOPJava.mp4]");
            String pathOut = askUser("Input output folder [a:\\3_WORK\\VIDEO\\Juja\\Todo\\OOPJava\\out\\]");
            PartOfVideo.renderFFmpegCommands(path, pathOut, ".mp4");
        }
    }

}
