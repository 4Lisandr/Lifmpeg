package net.asasha.lifmpeg.model;

public class TimeCode {

    public static final String START = "00:00:00.000";

    // 00:09:28.567
    private String exactCode;
    // 568.567
    private int milliSeconds;
    // 00:09:29
    private String userCode;

    public static final String COLON = ":";
    public static final String DASH = "-";
    public static final String SPACE = " ";

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
    public int parseCode(String timeCode) {
        int milliSeconds = 0;

        String code = timeCode.trim();
        String separator = "";

        if (code.contains(COLON)) {
            separator = COLON;
        } else if (code.contains("-")) {
            separator = DASH;
        } else if (code.contains(" ")) {
            separator = SPACE;
        }

        if (!separator.isEmpty()) {
            String[] all = code.split(separator);
            milliSeconds = 1000 * (int)Float.parseFloat(all[all.length - 1]);

            for (int i = all.length - 2, shift = 60; i >= 0; i--, shift *= 60) {
                if (!all[i].isEmpty()) {
                    milliSeconds += 1000* Integer.parseInt(all[i].trim()) * shift;
                }
            }
        } else {
            milliSeconds = code.isEmpty() ? 0 : 1000 * (int)Float.parseFloat(code);
        }
        return milliSeconds;

    }

    public String calcUserCode() {
        return String.format("%d:%d:%d", hours(true),minutes(true)%60,seconds(true)%60);
    }

    public String calcExactCode() {
        return String.format("%d:%d:%d.%d", hours(false),minutes(false)%60,
                seconds(false)%60, seconds(false)%1000);
    }

    private int hours(boolean round) {
        return minutes(round)/60;
    }

    private int minutes(boolean round) {
        return seconds(round)/60;
    }

    private int seconds(boolean round) {
        return round
                ? Math.round(milliSeconds / 1000)
                : milliSeconds/1000;
    }



}
