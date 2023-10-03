package ch.kodai.templ8;


import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "templ8", mixinStandardHelpOptions = true, version = "0.0.1", description = "A Kubernetes templating utility")
public class Templ8 implements Callable<Integer> {

    @Option(names = {"-f", "--filename"}, paramLabel = "FILE", required = true, description = "The template file to transform")
    File inputFile;

    @Option(names = {"-o", "--output"}, paramLabel = "FILE", required = true, description = "The transformed file location")
    File outputFile;

    @Option(names = {"-ie", "--input-encoding"},
            paramLabel = "ENCODING",
            description = """
                The input file encoding: ${COMPLETION-CANDIDATES}.
                Defaults to UTF8.""")
    Encoding inputEncoding = Encoding.UTF8;

    @Option(names = {"-oe", "--output-encoding"},
            paramLabel = "ENCODING",
            description = """
                The output file encoding: ${COMPLETION-CANDIDATES}.
                Defaults to UTF8.""")
    Encoding outputEncoding = Encoding.UTF8;

    @Override
    public Integer call() {
        return 0;
    }
}