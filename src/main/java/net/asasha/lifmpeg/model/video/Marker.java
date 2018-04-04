package net.asasha.lifmpeg.model.video;

/**
 * Marker on video stream (timeline)
 */
public class Marker {
    private TimeCode timeCode;
    // optional fields
    private String name;
    private String description;
    private boolean isTerminator;
    private boolean isPause;
    private boolean isEnd;


    public Marker(TimeCode timeCode) {
        this.timeCode = timeCode;
    }

    public TimeCode getTimeCode() {
        return timeCode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isTerminator() {
        return isTerminator;
    }

    public boolean isPause() {
        return isPause;
    }

    public boolean isEnd() {
        return isEnd;
    }


    public void setTimeCode(TimeCode timeCode) {
        this.timeCode = timeCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTerminator(boolean terminator) {
        isTerminator = terminator;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
