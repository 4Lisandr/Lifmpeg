package net.asasha.lifmpeg.model.video;

import net.asasha.lifmpeg.model.TimeCode;

/**
 * Marker on video stream (timeline)
 */
public class Marker {
    private TimeCode timeCode;
    private String description;
    private boolean isTerminator;


    public Marker(TimeCode timeCode) {
        this.timeCode = timeCode;
    }

    public TimeCode getTimeCode() {
        return timeCode;
    }

    public void setTimeCode(TimeCode timeCode) {
        this.timeCode = timeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTerminator() {
        return isTerminator;
    }

    public void setTerminator(boolean terminator) {
        isTerminator = terminator;
    }
}
