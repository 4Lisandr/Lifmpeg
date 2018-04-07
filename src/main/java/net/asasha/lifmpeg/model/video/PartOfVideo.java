package net.asasha.lifmpeg.model.video;

import java.util.ArrayList;

public class PartOfVideo {
    private static int counter = 0;
    private final int id;

    private static ArrayList<PartOfVideo> allParts = new ArrayList<>();

    private final TimeCode from;
    private TimeCode to;

    private String name = "";
    private boolean isRemain = true;

    //todo - private ArrayList <String> descriptionLines;

    /**
     * @param from
     */
    public PartOfVideo(TimeCode from) {
        id = counter++;
        setDefaultName();
        this.from = from;
        allParts.add(this);
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

    /*
    * get all parts and set borders for render
    * */
    public static ArrayList<PartOfVideo> getAllParts() {
        if (allParts.size() == 0)
            return allParts;

        PartOfVideo previousPart = allParts.get(0);

        for (int i = 1; i < allParts.size(); i++) {
            PartOfVideo current = allParts.get(i);
            previousPart.setTo(current.getFrom());
            previousPart = current;
        }

        return allParts;
    }

    public void print() {
        System.out.println(toString(true));
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean color) {
        if (to == null)
            return "";

        return from.toString() + "\t" +
                to.toString() + "\t" +
                from.toShortString() + "\t" +
                length(color);
    }

    private String length(boolean color) {
        TimeCode t = TimeCode.difference(to, from);
        String result = t.toShortString();
        if (!color)
            return result;

//        System.out.println((char) 27 + "[31mWarning! " + (char)27 + "[0m");
//        System.out.println((char) 27 + "[32mWarning! " + (char)27 + "[0m");
        if (t.isSmall())
            result = (char) 27 + "[31m"+ result + (char)27 + "[0m";
        if (t.isBig())
            result = (char) 27 + "[32m"+ result + (char)27 + "[0m";
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
