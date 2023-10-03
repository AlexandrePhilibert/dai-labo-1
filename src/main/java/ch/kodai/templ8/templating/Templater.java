package ch.kodai.templ8.templating;

import ch.kodai.templ8.templating.extensions.ValueRoot;
import ch.kodai.templ8.templating.extensions.ValuesProviderExtension;
import ch.kodai.templ8.values.ValuesProvider;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.Loader;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

public class Templater {

    private final ValuesProvider provider;

    public Templater(ValuesProvider provider) {

        this.provider = provider;
    }

    public void template(Reader reader, Writer writer) throws IOException {
        PebbleEngine pebbleEngine = new PebbleEngine.Builder()
                .extension(new ValuesProviderExtension())
                .loader(new TemplateLoader(reader))
                .build();
        PebbleTemplate template = pebbleEngine.getTemplate(TemplateLoader.TEMPLATE_NAME);

        template.evaluate(writer, Map.of("value", new ValueRoot(provider, null)));
    }
}
