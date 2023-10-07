package ch.kodai.templ8;


import ch.kodai.templ8.templating.Templater;
import ch.kodai.templ8.values.exceptions.ValuesLoadException;
import ch.kodai.templ8.values.impl.CommandValueProvider;
import ch.kodai.templ8.values.impl.ComposableValueProvider;
import ch.kodai.templ8.values.impl.YamlValueParser;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "templ8", mixinStandardHelpOptions = true, version = "0.0.1", description = "A Kubernetes templating utility")
public class Templ8 implements Callable<Integer> {

    @Option(names = {"-f", "--filename"}, paramLabel = "FILE", required = true, description = "The template file to transform")
    File templateFile;

    @Option(names = {"-v", "--values"}, paramLabel = "FILE", description = "The values files that will be used in the template file")
    File valuesFile;

    @Option(names = {"-p", "--parameter"}, paramLabel = "KEY=VALUE", split = ",", description = "The values files that will be used in the template file")
    List<String> parameters = new ArrayList<>();


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
    public Integer call() {
        ComposableValueProvider composableValueProvider = new ComposableValueProvider();
        // Put the parameters first, as they are called in the order they are added, and we want to put cli params
        // in front.

        if (outputFile.exists()) {
            Dialoguer.showInfo("Output path already exists, replacing...");
        }

        Dialoguer.showProgress("Templating in progress...");

        if (!parameters.isEmpty()) {
            CommandValueProvider commandValueProvider = new CommandValueProvider();
            for (String parameter : parameters) {
                try {
                    commandValueProvider.parseValue(parameter);
                } catch (ValuesLoadException e) {
                    Dialoguer.showError("Invalid format for input '%s': %s".formatted(parameter, e.getInnerMessage()));
                }
            }

            composableValueProvider.addProvider(commandValueProvider);
        }


        if (valuesFile != null) {

            try {
                YamlValueParser parser = new YamlValueParser(this.valuesFile);
                composableValueProvider.addProvider(parser);
            } catch (FileNotFoundException e) {
                Dialoguer.showError("The values file '%s' was not found.".formatted(this.valuesFile.getPath()));
                return 1;
            } catch (Exception e) {
                Dialoguer.showError("Invalid values file: " + e.getMessage() + " (" + e.getClass().getName() + ")");
                return 1;
            }
        }

        Templater templater = new Templater(composableValueProvider);

        if (!this.templateFile.exists()) {
            Dialoguer.showError("The template file '%s' was not found.".formatted(this.templateFile.getPath()));
            return 1;
        }

        try (
                FileReader fileReader = new FileReader(this.templateFile, inputCharset.toStandardCharset());
                FileWriter fileWriter = new FileWriter(this.outputFile, outputCharset.toStandardCharset())
        ) {
            templater.template(fileReader, fileWriter);

            LOGGER.info("Templated file successfully !");
        } catch (IOException e) {
            Dialoguer.showError("An error occurred while templating: " + e.getMessage());
            return 1;
        }

        Dialoguer.showSuccess("Templated %s successfully at %s".formatted(
                templateFile.getName(),
                outputFile.getName()
        ));

        return 0;
    }
}