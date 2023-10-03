package ch.kodai.templ8;


import ch.kodai.templ8.templating.Templater;
import ch.kodai.templ8.values.impl.YamlValueParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "templ8", mixinStandardHelpOptions = true, version = "0.0.1", description = "A Kubernetes templating utility")
public class Templ8 implements Callable<Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Templ8.class);

    @Option(names = {"-f", "--filename"}, paramLabel = "FILE", required = true, description = "The template file to transform")
    File templateFile;

    @Option(names = {"-v", "--values"}, paramLabel = "FILE", required = true, description = "The values files that will be used in the template file")
    File valuesFile;

    @Option(names = {"-o", "--output"}, paramLabel = "FILE", required = true, description = "The transformed file location")
    File outputFile;

    @Option(names = {"-ie", "--input-encoding"},
            paramLabel = "ENCODING",
            description = """
                    The input file encoding: ${COMPLETION-CANDIDATES}.
                    Defaults to UTF8.""")
    SupportedCharset inputCharset = SupportedCharset.UTF8;

    @Option(names = {"-oe", "--output-encoding"},
            paramLabel = "ENCODING",
            description = """
                    The output file encoding: ${COMPLETION-CANDIDATES}.
                    Defaults to UTF8.""")
    SupportedCharset outputCharset = SupportedCharset.UTF8;

    @Override
    public Integer call() throws IOException {
        YamlValueParser parser = new YamlValueParser(this.valuesFile);
        Templater templater = new Templater(parser);

        try (
                FileReader fileReader = new FileReader(this.templateFile, inputCharset.toStandardCharset());
                FileWriter fileWriter = new FileWriter(this.outputFile, outputCharset.toStandardCharset())
        ) {
            templater.template(fileReader, fileWriter);
        } catch (IOException e) {
            LOGGER.error("Error when templating", e);
            return 1;
        }

        return 0;
    }
}