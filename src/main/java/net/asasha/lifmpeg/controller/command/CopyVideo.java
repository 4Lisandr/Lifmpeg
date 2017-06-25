package net.asasha.lifmpeg.controller.command;

public class CopyVideo implements Command {
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
    }
}
