package ch.kodai.templ8;

import picocli.CommandLine;

public class Main {
    public static void main(String... args) {
        int exitCode = new CommandLine(new Templ8()).execute(args);
        System.exit(exitCode);
    }
}