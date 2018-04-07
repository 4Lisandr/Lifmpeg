package net.asasha.lifmpeg.view;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

public class Console implements View {

    //RED
    // System.out.println((char) 27 + "[31mWarning! " + (char)27 + "[0m");
    //GREEN
    //System.out.println((char) 27 + "[32mWarning! " + (char)27 + "[0m");
    public void write(String sColor, String message) {
        Ansi.Color color;
        try {
         color = Ansi.Color.valueOf(sColor);
        } catch (Exception e){
            write(message);
        }
        AnsiConsole.systemInstall();
        System.out.println(ansi().fg(RED).a(message).reset());
        AnsiConsole.systemUninstall();
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
}
