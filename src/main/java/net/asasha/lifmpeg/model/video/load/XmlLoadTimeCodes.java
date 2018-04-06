package net.asasha.lifmpeg.model.video.load;

import net.asasha.lifmpeg.model.video.TimeLine;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlLoadTimeCodes {

    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();

    public XmlLoadTimeCodes(String xmlFile) throws ParserConfigurationException, IOException, SAXException {
        Document document = documentBuilder.parse(new File(xmlFile));

        int duration = tagToInt(document, "duration", 0);
        int timebase = tagToInt(document, "timebase", 0);

        TimeLine timeLine = new TimeLine(duration, timebase);

        NodeList list = document.getElementsByTagName("in");

        for (int i = 0; i < list.getLength() ; i++) {
            int frame = tagToInt(document, "in", i);
            if (frame > 0){
                timeLine.addTimeCode(frame);
            }
        }
        timeLine.printTimeCodes();
        System.out.println("================");
        timeLine.printPartsOfVideo();
    }

    private int tagToInt(Document document, String tagname, int index) {
        String s = document.getElementsByTagName(tagname).item(index).getTextContent();
        return Integer.parseInt(s);
    }

    public static void main(String[] args) {
        try {
            new XmlLoadTimeCodes("a:\\1_INBOX\\jgc.xml");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
