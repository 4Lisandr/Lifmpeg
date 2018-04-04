package net.asasha.lifmpeg.model.video;

import java.util.ArrayList;

public class PartOfVideo {
    private static int counter = 0;

    private static ArrayList<PartOfVideo> allParts = new ArrayList<>();

    private final int id;

    private final TimeCode start;

    private TimeCode end;
    private String name = "";
    private boolean isRemain = true;

    //todo - private ArrayList <String> descriptionLines;

    /**
     * 
     * @param start
     */
    public PartOfVideo(TimeCode start) {
        id = counter++;
        setDefaultName();
        this.start = start;
        allParts.add(this);
    }

    // not all parts of videos remains
    public PartOfVideo(TimeCode start, boolean isRemain) {
        this(start);
        this.isRemain = false;
    }

    public PartOfVideo(TimeCode start, TimeCode end, boolean isRemain) {
        this(start, isRemain);
        this.end = end;
    }

    public PartOfVideo(String name, TimeCode start) {
        this(start);
        this.name = name;
    }

    public PartOfVideo(String name, TimeCode start, TimeCode end) {
        this(name, start);
        this.end = end;
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
            previousPart.setEnd(current.getStart());
            previousPart = current;
        }

        return allParts;
    }


    private void setDefaultName() {
        name = "Part_" + id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnd(TimeCode end) {
        this.end = end;
    }

    public TimeCode getStart() {
        return start;
    }

    public TimeCode getEnd() {
        return end;
    }

}
