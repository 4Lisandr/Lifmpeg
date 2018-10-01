package net.asasha.lifmpeg.model.video.timeline;

import net.asasha.lifmpeg.view.Console;

import java.util.ArrayList;

public class PartOfVideo {
    private static int counter = 0;
    public final int id;

    private final static ArrayList<PartOfVideo> allParts = new ArrayList<PartOfVideo>();
//    private int lastTerminatorIndex = 0;

    private final TimeCode from;
    private TimeCode to;
    /*Link to next part for extract parameter @to*/
    private PartOfVideo next;


    private String name = "";
    private boolean isTerminator;

    //todo - private String description;
    // прочитать из текстового файла, если строка начинается с точки
    //.то начало части видео является границей для порезки файла
    //если содержит только точку - то это пауза
    // если имя не получено - назначить "NoName"+id
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
            setDefaultName();
        } else if (name.startsWith(".")) {
            isTerminator = true;
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
            previousPart = current;
        }
    }

    @Override
    public String toString() {
        if (to == null)
            return "";

        String length = length(true);

        String brief = from.toShortString() + "\t" + length;

        return id + "\t" + from.toString() + "\t" + to.toString() + "\t" +
                brief + "\t" + position() + "\t" + name;

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
