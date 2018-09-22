package net.asasha.lifmpeg.model.video.timeline;

import net.asasha.lifmpeg.view.Console;

import java.util.ArrayList;
import java.util.Objects;

public class PartOfVideo {
    private static int counter = 0;
    public final int id;

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
    private boolean isTerminator = true;

    //todo - private String description;
    // прочитать из текстового файла, если строка начинается с точки
    //.то начало части видео является границей для порезки файла
    //если содержит только точку - то это пауза
    //todo - isConcatToNext

    /**
     * @param from //todo создавать только недублирующийся объект
     *             проверить TimeCode from на наличие isContents во всей цепочке начиная с head
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

    public boolean isConcatToNext() {
        return !(next == null || next.isTerminator);
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

    public void print() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        if (to == null)
            return "";

        String length = length(true);

        String brief = from.toShortString() + "\t" +
                length;
        String full = id + "\t" + from.toString() + "\t" +
                to.toString() + "\t" + brief;

        return Objects.equals("", "brief") ?
                brief : full;

    }

    private String length(boolean color) {
        TimeCode diff = TimeCode.difference(to, from);
        String result = diff.toShortString();
        if (!color)
            return result;

        if (diff.isShortLength())
            result = Console.colorizeString("red", result);
        if (diff.isLongLength())
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
