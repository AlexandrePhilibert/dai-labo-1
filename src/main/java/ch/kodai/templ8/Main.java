package ch.kodai.templ8;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "templ8", mixinStandardHelpOptions = true, version = "0.0.1", description = "A Kubernetes templating utility")
public class Main implements Callable<Integer> {
    @Override
    public Integer call() {
        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}