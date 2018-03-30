package net.asasha.lifmpeg.model.video;

import net.asasha.lifmpeg.model.TimeCode;

import java.util.ArrayList;

/**
 * Part of video in current video file
 */
public class PartOfVideo {
    private static int counter = 0;
    private static ArrayList <PartOfVideo> allParts = new ArrayList<>();

    private final int id;

    private String name = "";
    private TimeCode start;
    private TimeCode end;
    private boolean isRemain = true;

    //todo - private ArrayList <String> descriptionLines;

    public PartOfVideo(TimeCode start) {
        id = counter++;
        setDefaultName();
        this.start = start;
    }

    private void setDefaultName() {
        name = "Part_"+id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(TimeCode start) {
        this.start = start;
    }

    public void setEnd(TimeCode end) {
        this.end = end;
    }


}