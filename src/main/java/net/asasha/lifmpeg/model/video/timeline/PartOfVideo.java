package net.asasha.lifmpeg.model.video.timeline;

import net.asasha.lifmpeg.controller.command.CopyVideo;
import net.asasha.lifmpeg.view.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PartOfVideo {
    private static int counter = 0;
    public final int id;

    private final static ArrayList<PartOfVideo> allParts = new ArrayList<PartOfVideo>();
//    private int lastTerminatorIndex = 0;

    private final TimeCode from;
    private TimeCode to;
    /*Link to next part for extract parameter @to*/
    private PartOfVideo next = null;


    private String name = "";
    private boolean isTerminator;

    //todo - isConcatToNext

    /**
     * @param from //todo создавать только недублирующийся объект
     *             проверить TimeCodeImpl from на наличие isContents во всей цепочке начиная с head
     */
    public PartOfVideo(Marker from) {
        id = counter++;
        name = from.getDescription();
        this.from = from;
        if (name == null || name.isEmpty()) {
            // если имя не получено - назначить "NoName"+id
            setDefaultName();
        } else if (name.startsWith(".")) {
            //начало части видео является границей для порезки файла
            isTerminator = true;
            //если содержит только точку - то это пауза
            name = name.substring(1);
//            lastTerminatorIndex = allParts.size();
        }
        allParts.add(this);
    }

    public boolean isConcatToNext() {
        return !(next == null || next.isTerminator);
    }


    public PartOfVideo(String name, Marker from) {
        this(from);
        this.name = name;
    }

    public PartOfVideo(String name, Marker from, Marker to) {
        this(name, from);
        this.to = to;
    }

    public static boolean deletePart(int number) {
        if (number >= allParts.size())
            return false;
        allParts.remove(number);
        buildChain();
        return true;
    }

    /*
    * get all parts and set borders for render
    * */
    public static ArrayList<PartOfVideo> getAllParts() {
        if (allParts.size() == 0)
            return allParts;

        buildChain();

        return allParts;
    }

    private static void buildChain() {
        PartOfVideo previousPart = allParts.get(0);
        for (int i = 1; i < allParts.size(); i++) {
            PartOfVideo current = allParts.get(i);
            previousPart.setTo(current.getFrom());
            previousPart.next = current;
            previousPart = current;
        }
    }

    public static void setTerminators(int... id) {
        IntStream.of(id).filter(x -> x >= 0 && x < allParts.size())
                .forEach(x -> allParts.get(x).isTerminator = true);
    }

    public static void setTerminators(Integer[] id) {
        Arrays.stream(id).filter(x -> x != null && x >= 0 && x < allParts.size())
                .forEach(x -> allParts.get(x).isTerminator = true);
    }

    public static void printTerminators() {
        allParts.stream()
                .filter(p -> p.isTerminator)
                .forEach(System.out::println);
    }

    public static List<PartOfVideo> getTerminators() {
        allParts.get(allParts.size() - 1).isTerminator = true;
        return allParts.stream()
                .filter(p -> p.isTerminator)
                .collect(Collectors.toList());
    }

    public PartOfVideo nextTerminator() {
        PartOfVideo result = next;
        while (result != null && !result.isTerminator) {
            result = result.next;
        }
        return result == null ? allParts.get(allParts.size() - 1) : result;
    }

    public static void printFfmpegCommands() {
        //Multithreading.mp4 	00:00:02.333	00:08:46.000	11.mp4
        //CopyVideo.copyVideo();
        ffmpegCommands()
                .stream()
                .filter(c-> !c[2].isEmpty())
                .forEach(c-> System.out.printf("%s %s %s%n", c[0], c[1], c[2]));
    }

    /**
     * @param pathFile - full path to file
     * @param pathOut - with leading slash
     * @param type - start with . - for instance .mp4
     */
    public static void renderFFmpegCommands(String pathFile, String pathOut, String type) {
        ffmpegCommands()
                .stream()
                .filter(c -> !c[2].isEmpty())
                .forEach(c -> {
                    CopyVideo.copyVideo
                            (pathFile, c[0], c[1], pathOut + c[2] + type);
                });
    }

    /**
     *
     * @return List of <String[]>{from, to, name}
     */
    private static ArrayList<String[]> ffmpegCommands() {
        ArrayList<String[]> result = new ArrayList<>();
        List<PartOfVideo> terminators = getTerminators();

        int j = 1;
        for (int i = 0; i < terminators.size() - 1; i++) {
            PartOfVideo from = terminators.get(i);
            PartOfVideo to = terminators.get(i + 1);
            String name = from.name;
            if (!name.isEmpty()){
                name = String.format("%02d", j) +" "+ name;
                j++;
            }
            result.add(new String[]{from.from.toString(), to.from.toString(), name});
        }


        return result;
    }

    public static void printAllParts() {
        final String LINE = "----------------------------------------------------------------------------";
        final String TITLE = "Id\t    From    \t     To     \t Round  \t Length  \t Position\t Description";
        final String HEADER = LINE + "\n" + TITLE + "\n" + LINE;

        System.out.println(HEADER);
        getAllParts().forEach(System.out::println);
        System.out.println(LINE);
    }

    @Override
    public String toString() {
        if (to == null)
            return "";

        String length = length(true);
        String brief = from.toShortString() + "\t" + length;

        return String.format("%d\t%s\t%s\t%s\t%s\t%s",
                id, from.toString(), to.toString(), brief, position(), name);

    }

    private String length(boolean color) {
        Marker diff = Marker.difference(to, from);
        String result = diff.toShortString();
        if (!color)
            return result;

        if (diff.isShortLength())
            result = Console.colorizeString("red", result);
        if (diff.isLongLength())
            result = Console.colorizeString("purple", result);
        return result;
    }

    private String position() {
        return Marker.difference(from, getLastTimeCode()).toShortString();
    }

    private TimeCode getLastTimeCode() {
        return isTerminator ?
                from : allParts.get(lastTerminatorIndex()).from;
    }

    private int lastTerminatorIndex() {
        for (int i = allParts.size() - 1; i >= 0; i--) {
            PartOfVideo part = allParts.get(i);
            //todo test of bounds id<=i
            if (id > i && part.isTerminator) {
                return i;
            }
        }
        return 0;
    }

    private void setDefaultName() {
        name = "Part_" + id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTo(TimeCode to) {
        this.to = (Marker) to;
    }

    public TimeCode getFrom() {
        return from;
    }

    public TimeCode getTo() {
        return to;
    }

    public static void main(String[] args) {
        String test = ".test";
        System.out.println(test.startsWith("."));
        System.out.println(test.startsWith("\\."));

    }


}
