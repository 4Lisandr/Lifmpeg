package net.asasha.lifmpeg.model;

/**
 *
 */
public class CmdEditor extends FfmpegManager implements AdvancedVideoManager {
    @Override
    public void split() {

    }

    class PartOfVideo {
        int number;
        String Name;
        TimeCode begin, end;

    }
}
