package net.asasha.lifmpeg.model.video.load;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlVideoLoader {

    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();

    private final int duration;
    private final int timebase;
    private final ArrayList<Integer> frames;

    public XmlVideoLoader(String xmlFile) throws ParserConfigurationException, IOException, SAXException {
        Document document = documentBuilder.parse(new File(xmlFile));

        duration = tagToInt(document, "duration", 0);
        timebase = tagToInt(document, "timebase", 0);
        frames = parseFrames(document);
    }

    public int getDuration() {
        return duration;
    }

    public int getTimebase() {
        return timebase;
    }

    public ArrayList<Integer> getFrames() {
        return frames;
    }

    private ArrayList<Integer> parseFrames(Document document) {
        NodeList list = document.getElementsByTagName("in");
        ArrayList<Integer> frames = new ArrayList<>();

        for (int i = 0; i < list.getLength() ; i++) {
            int frame = tagToInt(document, "in", i);
            if (frame > 0){
                frames.add(frame);
            }
        }
        return frames;
    }

    private int tagToInt(Document document, String tagName, int index) {
        String s = document.getElementsByTagName(tagName).item(index).getTextContent();
        return Integer.parseInt(s);
    }

}
