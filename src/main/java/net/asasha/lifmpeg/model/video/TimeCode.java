package net.asasha.lifmpeg.model.video;

public class TimeCode implements Comparable {

    private static final String FULL_FORMAT = "%02d:%02d:%02d.%03d";
    private static final String SHORT_FORMAT = "%02d:%02d:%02d";

    private static final String COLON = ":";
    private static final String DASH = "-";
    private static final String SPACE = " ";

    private final int milliSeconds;


    public TimeCode(int milliSeconds) {
        this.milliSeconds = milliSeconds;
    }
    /**
     * Any string, contains hh:mm:ss.ms with any separator like hh-mm-ss.ms or hh mm ss.ms
     */
    public TimeCode(String timeCode) {
        milliSeconds = timeCode.isEmpty()
                ? 0
                : parseCode(timeCode);
    }

    /*
    * Use cases:
    * 1-9-28.367 -->  01:09:28.367
    * 9:28.367 -->  00:09:28.367
    * 28.367 --> 00:00:28.367
    * .367 --> 00:00:00.367
    * */
    private int parseCode(String timeCode) {
        int milliSeconds = 0;

        String code = timeCode.trim();
        String separator = "";

        if (code.contains(COLON)) {
            separator = COLON;
        } else if (code.contains(DASH)) {
            separator = DASH;
        } else if (code.contains(" ")) {
            separator = SPACE;
        }

        if (!separator.isEmpty()) {
            String[] all = code.split(separator);
            milliSeconds = (int)(1000 * Double.parseDouble(all[all.length - 1]));

            for (int i = all.length - 2, shift = 60; i >= 0; i--, shift *= 60) {
                if (!all[i].isEmpty()) {
                    milliSeconds += 1000 * Integer.parseInt(all[i].trim()) * shift;
                }
            }
        } else {
            milliSeconds = code.isEmpty() ? 0 : (int) (1000* Double.parseDouble(code));
        }

        return milliSeconds;

    }


    public static TimeCode difference(TimeCode to, TimeCode from){
        int diff = to.milliSeconds - from.milliSeconds;
        return new TimeCode(diff);
    }

    //00:09:28.567
    @Override
    public String toString() {
        return String.format(FULL_FORMAT,
                hours(false),
                minutes(false) % 60,
                seconds(false) % 60,
                milliSeconds % 1000);
    }

    // 00:09:29
    public String toShortString() {
        return String.format(SHORT_FORMAT,
                hours(true),
                minutes(true) % 60,
                seconds(true) % 60);
    }

    private int hours(boolean round) {
        return minutes(round) / 60;
    }

    private int minutes(boolean round) {
        return seconds(round) / 60;
    }

    private int seconds(boolean round) {
        return round
                ? Math.round(milliSeconds / 1000f)
                : milliSeconds / 1000;
    }

    @Override
    public int compareTo(Object o) {
        if (! (o instanceof TimeCode))
            return 0;

        TimeCode timeCode = (TimeCode) o;

        return milliSeconds - timeCode.milliSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeCode timeCode = (TimeCode) o;

        return milliSeconds == timeCode.milliSeconds;
    }

    @Override
    public int hashCode() {
        return milliSeconds;
    }

    /**
     * todo - calculate relative length from all parts of video,
     * shortest and longest
     */

    public boolean isShortLength() {
        return milliSeconds < 30000;
    }

    public boolean isLongLength() {
        return milliSeconds > 600000;
    }

}