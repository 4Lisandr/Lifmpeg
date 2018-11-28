package net.asasha.lifmpeg.model.video.XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLReaderDOM {

    public static final String PATHNAME = "a:\\1_INBOX\\JavaNIO.xml";

    public static void main(String[] args) throws ParserConfigurationException {

        File xmlFile = new File(PATHNAME);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("sequence");
            //now XML is loaded as Document in memory, lets convert it to Object List
            List<String> frames = new ArrayList<String>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                frames.add(getFrame(nodeList.item(i)));
                System.out.println(i);
            }
            for (String frame : frames) {
                System.out.println(frame);
//                System.out.println(Integer.parseInt(frame));
            }
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }

    }


    private static String getFrame(Node node) {
        //XMLReaderDOM domReader = new XMLReaderDOM();
        String frame = "";
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            frame = getTagValue("in", element);
        }
        return frame;
    }


    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

}