package net.asasha.lifmpeg.controller.command;

public class CopyVideo implements Command {
    private static boolean inProgress = false;

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process(String command) {
        // Example of command: copy 1
        // ffmpeg -ss [start] -i in.mp4 -t [duration] -c copy out.mp4
        // ffmpeg -ss [start] -i in.mp4 -t [duration] -c:v libx264 -c:a aac -strict experimental -b:a 128k out.mp4
        // ffmpeg -i [input_file] -vcodec copy -an [output_file]

        // ffmpeg -i in.mp4 -ss 00:00:03 -to 00:00:09 -c:v copy -c:a copy part1.mp4
        //  ffmpeg -i input.flv -ss 00:00:14.435 -vframes 1 out.png
    }

    //pair - io, pair - borders
    public static boolean copyVideo(String input, String start, String end, String output){
        //Multithreading.mp4 	00:00:02.333	00:08:46.000	11.mp4
        //ffmpeg -i %PREFIX% -ss %FROM% -to %TO% -c:v copy -c:a copy OUT\%OUT%

        String fullCommand = String.format(
                "cmd /c start /min ffmpeg -i \"%s\" -ss \"%s\" -to \"%s\" -c:v copy -c:a copy \"%s\"",
                input, start, end, output);

        System.out.println(fullCommand);
        int pause = inProgress ? 4000 : 1000;
        inProgress = true;
        try {
            Thread.sleep(pause);
            Runtime.getRuntime().exec(fullCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        inProgress = false;
        return true;
    }
}
