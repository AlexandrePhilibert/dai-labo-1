package ch.kodai.templ8.templating;

import io.pebbletemplates.pebble.loader.Loader;

import java.io.Reader;
import java.util.Objects;

public class TemplateLoader implements Loader<String> {
    // for now, we only support a single template at a time. If we want to do more, rename this to another one to override
    // the cache.
    public static final String TEMPLATE_NAME = "template";
    private final Reader reader;

    public TemplateLoader(Reader reader) {

        this.reader = reader;
    }

    @Override
    public Reader getReader(String cacheKey) {
        return reader;
    }

    @Override
    public void setCharset(String charset) {

    }

    @Override
    public void setPrefix(String prefix) {

    }

    @Override
    public void setSuffix(String suffix) {

    }

    @Override
    public String resolveRelativePath(String relativePath, String anchorPath) {
        return null;
    }

    @Override
    public String createCacheKey(String templateName) {
        return templateName;
    }

    @Override
    public boolean resourceExists(String templateName) {
        return Objects.equals(templateName, "template");
    }
}
