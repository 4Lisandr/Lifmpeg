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
    Document document = documentBuilder.parse(new File(INBOX_XML));


    public XmlExportTimeCodes() throws ParserConfigurationException, IOException, SAXException {
        String duration = document.getElementsByTagName("duration").item(0).getTextContent();
        String timebase = document.getElementsByTagName("timebase").item(0).getTextContent();
//        String in = document.getElementsByTagName("in").item(0).getTextContent();
        NodeList list = document.getElementsByTagName("in");
        ArrayList<Integer> frames = new ArrayList<Integer>();

        for (int i = 0; i < list.getLength() ; i++) {
            String s = document.getElementsByTagName("in").item(i).getTextContent();
            int x = Integer.parseInt(s);
            if (x > 0){
                frames.add(x);
                TimeCode t =  new TimeCode((1000*x)/30);
                System.out.println(x +"\t"+ t+"\t"+t.toShortString());
            }
        }

        System.out.println(duration);
        System.out.println(timebase);

        int length = (1000*Integer.parseInt(duration))/
                Integer.parseInt(timebase);
        System.out.println(new TimeCode(length).toString());
        System.out.println(new TimeCode(length).toShortString());


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
