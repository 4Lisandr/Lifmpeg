package net.asasha.lifmpeg.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class FfmpegManager implements VideoManager {

    @Override
    public void concat(String in, String out) {

        String command = "cmd /c start /min ffmpeg -y -f concat -i \"%s\" -c copy \"%s\"";
        String fullCommand = String.format(command, in, out);

        try {
            Runtime.getRuntime().exec(fullCommand);
        }
        catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void createImage(String in, String time, String out) {
        //  ffmpeg -i input.flv -ss 00:00:14.435 -vframes 1 out.png

        String command = "cmd /c start /min ffmpeg -i \"%s\" -ss \"%s\" -vframes 1 \"%s\"";
        String fullCommand = String.format(command, in, time, out);

        try {
            Runtime.getRuntime().exec(fullCommand);
        }
        catch (Exception e){e.printStackTrace();}
    }

    private void createImage(String input, double sec, String output) throws IOException {
        createImage(input, timeFormatter(sec), output);
    }

    private static String timeFormatter(double durable){
        SimpleDateFormat sdf;
        if (durable<3600){
            sdf = new SimpleDateFormat("mm:ss");
        }
        else
            sdf = new SimpleDateFormat("hh:mm:ss");
        return sdf.format(new Date((long) (durable*1000)));
    }
}
