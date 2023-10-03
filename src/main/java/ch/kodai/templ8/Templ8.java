package ch.kodai.templ8;


import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "templ8", mixinStandardHelpOptions = true, version = "0.0.1", description = "A Kubernetes templating utility")
public class Templ8 implements Callable<Integer> {
    @Override
    public Integer call() {
        return 0;
    }
}