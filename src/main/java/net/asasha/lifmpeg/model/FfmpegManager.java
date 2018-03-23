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
        execute(command, in, out);
    }


    @Override
    public void createAnImage(String in, String time, String out) {
        //  ffmpeg -i input.flv -ss 00:00:14.435 -vframes 1 out.png

        String command = "cmd /c start /min ffmpeg -i \"%s\" -ss \"%s\" -vframes 1 \"%s\"";
        execute(command, in, time, out);
    }

    private void createImage(String input, double sec, String output) throws IOException {
        createAnImage(input, timeFormatter(sec), output);
    }

    private static void execute(String... commands) {
        String fullCommand = String.format(String.valueOf(commands));

        try {
            Runtime.getRuntime().exec(fullCommand);
        }
        catch (Exception e){e.printStackTrace();}
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
