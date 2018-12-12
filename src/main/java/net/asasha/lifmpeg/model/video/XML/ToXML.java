package net.asasha.lifmpeg.model.video.XML;

import net.asasha.lifmpeg.model.video.timeline.TimeLine;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.FileReader;

public class ToXML {
    BufferedReader in;
    StreamResult out;
    TransformerHandler th;

    public static void main(String args[]) {
        new ToXML().begin();
    }

    public void begin() {
        try {
            in = new BufferedReader(new FileReader("a:\\1_INBOX\\data.txt"));
            out = new StreamResult("a:\\1_INBOX\\data.xml");
            openXml();
            String line;
            while ((line = in.readLine()) != null) {
                int spaceIndex = line.indexOf(" ");
                int frames = TimeLine.calcFrameOfString(line.substring(0, spaceIndex));
                String name = line.substring(spaceIndex + 1).trim();
                wrapMarker(name, String.valueOf(frames));
            }
            in.close();
            closeXml();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openXml() throws ParserConfigurationException, TransformerConfigurationException, SAXException {

        SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        th = tf.newTransformerHandler();

        // pretty XML output
        Transformer serializer = th.getTransformer();
        serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");

        th.setResult(out);
        th.startDocument();
        startWith("sequence");
    }

    public void wrapMarker(String name, String content) throws SAXException {
        startWith("marker");
            wrapTag("name", name);
            wrapTag("in", content);
            wrapTag("out", "-1");
            startWith("color");
                wrapTag("alpha", "0");
                wrapTag("red", "255");
                wrapTag("green", "127");
                wrapTag("blue", "0");
            endWith("color");
        endWith("marker");
    }

    public void emptyTag(String tag) throws SAXException {
        wrapTag(tag, " ");
    }

    public void wrapTag(String tag, String content) throws SAXException {
        startWith(tag);
        th.characters(content.toCharArray(), 0, content.length());
        endWith(tag);
    }

    private void endWith(String tag) throws SAXException {
        th.endElement(null, null, tag);
    }

    private void startWith(String tag) throws SAXException {
        th.startElement(null, null, tag, null);
    }

    public void closeXml() throws SAXException {
        endWith("sequence");
        th.endDocument();
    }
}
