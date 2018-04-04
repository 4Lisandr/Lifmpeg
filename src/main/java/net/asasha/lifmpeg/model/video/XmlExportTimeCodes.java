package net.asasha.lifmpeg.model.video;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlExportTimeCodes {
    public static final String INBOX_XML = "a:\\1_INBOX\\recursion.xml";

    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
            .newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document document = documentBuilder.parse(new File(INBOX_XML));


    public XmlExportTimeCodes() throws ParserConfigurationException, IOException, SAXException {
        String duration = document.getElementsByTagName("duration").item(0).getTextContent();
        String timebase = document.getElementsByTagName("timebase").item(0).getTextContent();

        System.out.println(duration);
        System.out.println(timebase);
        int length = (1000*Integer.parseInt(duration))/
                Integer.parseInt(timebase);
        System.out.println(new TimeCode(length).toString());
        System.out.println(new TimeCode(length).toShortString());

        //        ArrayList all  = (ArrayList) document.getElementsByTagName("in");
//        for (Object o:all) {
//            System.out.println(o.toString());
//        }

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
