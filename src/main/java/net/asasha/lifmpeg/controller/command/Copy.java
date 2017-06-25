package net.asasha.lifmpeg.controller.command;

import net.asasha.lifmpeg.model.VideoManager;
import net.asasha.lifmpeg.view.View;

public class Copy implements Command {

    private VideoManager manager;
    private View view;


    public Copy(VideoManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.contains("|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        manager.concat(data[0], data[1]);
    }
}
