package net.asasha.lifmpeg.model.video.timeline;

/**
 * Marker on video stream (timeline)
 */
public class Marker extends TimeCode {
    // optional fields
    private String description;
    private boolean isTerminator;
    private boolean isPause;
    private boolean isEnd;

    public Marker(int milliSeconds) {
        super(milliSeconds);
    }

    public Marker(String s) {
        super(s);
    }

    public TimeCode getTimeCode() {
        return (TimeCode) this;
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

    public static Marker difference(TimeCode to, TimeCode from) {
//        System.out.println("Calc: "+to.toShortString() +" "+ from.toShortString());
        return new Marker(to.milliSeconds - from.milliSeconds);
    }
}
