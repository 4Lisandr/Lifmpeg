package net.asasha.lifmpeg.model.video.timeline;

import net.asasha.lifmpeg.view.Console;

import java.util.ArrayList;
import java.util.Objects;

public class PartOfVideo {
    private static int counter = 0;
    private final int id;

    private final static ArrayList<PartOfVideo> allParts = new ArrayList<PartOfVideo>();

    private final TimeCode from;
    private TimeCode to;
    /*Link to next part for extract parameter @to*/
    private int number; //number in the list
    private PartOfVideo next;

    private static int size;
    private static PartOfVideo head = null;
    private static PartOfVideo tail = null;

    private String name = "";
    private boolean isRemain = true;

    //todo - private ArrayList <String> descriptionLines;

    /**
     * @param from
     * //todo создавать только недублирующийся объект
     * проверить TimeCode from на наличие isContents во всей цепочке начиная с head
     */
    public PartOfVideo(TimeCode from) {
        id = counter++;
        setDefaultName();
        this.from = from;
        allParts.add(this);
//        add(this);
    }

    private void add(PartOfVideo partOfVideo) {
        if (tail != null) {
            tail.next = this;
            tail.setTo(from);
        }

        tail = partOfVideo;
    }

    // not all parts of videos remains
    public PartOfVideo(TimeCode from, boolean isRemain) {
        this(from);
        this.isRemain = false;
    }

    public PartOfVideo(TimeCode from, TimeCode to, boolean isRemain) {
        this(from, isRemain);
        this.to = to;
    }

    public PartOfVideo(String name, TimeCode from) {
        this(from);
        this.name = name;
    }

    public PartOfVideo(String name, TimeCode from, TimeCode to) {
        this(name, from);
        this.to = to;
    }

    public static boolean deletePart(int number) {
        if (number >= allParts.size())
            return false;
        allParts.remove(number);
        buildChain();
        return true;
    }

    /*
    * get all parts and set borders for render
    * */
    public static ArrayList<PartOfVideo> getAllParts() {
        if (allParts.size() == 0)
            return allParts;

        buildChain();

        return allParts;
    }

    private static void buildChain() {
        PartOfVideo previousPart = allParts.get(0);
        for (int i = 1; i < allParts.size(); i++) {
            PartOfVideo current = allParts.get(i);
            previousPart.setTo(current.getFrom());
            previousPart = current;
        }
    }

    public void print(String format) {
        System.out.println(toString(format));
    }

    @Override
    public String toString() {
        return toString("default");
    }

    public String toString(String option) {
        if (to == null)
            return "";

        boolean isColor = !Objects.equals(option, "default");
        String length = length(isColor);

        String brief = from.toShortString() + "\t" +
                length;
        String full = id + "\t" + from.toString() + "\t" +
                to.toString() + "\t" + brief;

        return Objects.equals(option, "brief") ? brief
                : full;

    }

    private String length(boolean color) {
        TimeCode t = TimeCode.difference(to, from);
        String result = t.toShortString();
        if (!color)
            return result;

        if (t.isShortLength())
            result = Console.colorizeString("red", result);
        if (t.isLongLength())
            result = Console.colorizeString("purple", result);
        return result;
    }

    private void setDefaultName() {
        name = "Part_" + id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTo(TimeCode to) {
        this.to = to;
    }

    public TimeCode getFrom() {
        return from;
    }

    public TimeCode getTo() {
        return to;
    }

}
