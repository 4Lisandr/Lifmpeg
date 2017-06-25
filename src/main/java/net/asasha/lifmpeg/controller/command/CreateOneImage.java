package net.asasha.lifmpeg.controller.command;

import java.io.IOException;

public class CreateOneImage implements Command {
    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {

    }

    private static boolean createOneImage(String input, String time, String output) throws IOException {
        //  ffmpeg -i input.flv -ss 00:00:14.435 -vframes 1 out.png
        String command = "cmd /c start /min ffmpeg -i \"%s\" -ss \"%s\" -vframes 1 \"%s\"";

        String fullCommand = String.format(command, input, time, output);
        System.out.println(fullCommand);
        try {
            Runtime.getRuntime().exec(fullCommand);
        }
        catch (Exception e){e.printStackTrace();}
        return true;
    }



}
