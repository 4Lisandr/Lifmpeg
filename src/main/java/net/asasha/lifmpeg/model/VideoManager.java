package net.asasha.lifmpeg.model;

public interface VideoManager {

    void concat(String in, String out);

    void copy(String source, String from, String to, String destination);

    void createAnImage(String in, String time, String out);

}
