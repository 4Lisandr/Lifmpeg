package net.asasha.lifmpeg.model.video;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlExportTimeCodes {
    public static final String INBOX_XML = "a:\\1_INBOX\\recursion.xml";

    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
            .newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();


    public XmlExportTimeCodes() throws ParserConfigurationException, IOException, SAXException {
        Document document = documentBuilder.parse(new File(INBOX_XML));

        int duration = tagToInt(document, "duration", 0);
        int timebase = tagToInt(document, "timebase", 0);

        NodeList list = document.getElementsByTagName("in");
        ArrayList<Integer> frames = new ArrayList<Integer>();

        for (int i = 0; i < list.getLength() ; i++) {
            int x = tagToInt(document, "in", i);
            if (x > 0){
                frames.add(x);
                TimeCode t =  new TimeCode(calcMSecOfFrames(x, timebase));
                System.out.println(x +"\t"+ t+"\t"+t.toShortString());
            }
        }

        System.out.println(duration+"\t "+timebase);

        int length = calcMSecOfFrames(duration, timebase);
        System.out.println(new TimeCode(length).toString());
        System.out.println(new TimeCode(length).toShortString());


    }

    private int calcMSecOfFrames(int duration, int timebase) {
        return Math.round ((1000* duration)/timebase*1f);
    }

    private int tagToInt(Document document, String tagname, int index) {
        String s = document.getElementsByTagName(tagname).item(index).getTextContent();
        return Integer.parseInt(s);
    }

    public static void main(String[] args) {
        try {
            new XmlExportTimeCodes();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
