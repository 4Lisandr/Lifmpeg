package net.asasha.lifmpeg.view;

import java.util.Scanner;

public class Console implements View {


    public static String colorizeString(String sColor, String message) {
        // ANSI_PURPLE + STRING + ANSI_RESET;
        String reset = AnsiColor.RESET.getColor();
        String colorValue = AnsiColor.RESET.getColor();
        try {
            colorValue = AnsiColor.valueOf(sColor.toUpperCase()).getColor();
        } catch (Exception e) {
            /*NOP*/
        }
        return colorValue + message + reset;
    }

    public static enum AnsiColor {
        RESET("\u001B[0m"),
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");

        private final String color;

        public String getColor() {
            return color;
        }

        AnsiColor(String s) {
            this.color = s;
        }
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        new Console().write(colorizeString("green", "text"));
    }
}
