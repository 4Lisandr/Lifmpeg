package net.asasha.lifmpeg.model.video;


import java.util.ArrayList;

public class TimeLine {
    /**
     * @timebase - frame rate, default value is 30
     * @duration - length in number of frames
     */
    private int timebase = 30;
    private int duration;
    private ArrayList <TimeCode> timeCodes = new ArrayList<TimeCode>();


    public TimeLine(int duration, int timebase) {
        this.duration = duration;
        this.timebase = timebase;
    }

    public void addTimeCode(int frame) {
        timeCodes.add(new TimeCode(calcMSecOfFrames(frame)));
    }

    public int calcMSecOfFrames(int frame) {
        return Math.round ((1000* frame)/ this.timebase *1f);
    }

    public ArrayList<TimeCode> getTimeCodes() {
        return timeCodes;
    }

    public void printTimeCodes(){
        for (TimeCode t:timeCodes) {
            System.out.println(t+"\t \t"+t.toShortString());
        }

        System.out.println("=== Length of video ========");

        TimeCode length = new TimeCode(calcMSecOfFrames(duration));
        System.out.println(length.toString()+"\t \t"+length.toShortString());
    }
}
