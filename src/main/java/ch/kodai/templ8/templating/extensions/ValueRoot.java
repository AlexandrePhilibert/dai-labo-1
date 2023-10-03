package ch.kodai.templ8.templating.extensions;

import ch.kodai.templ8.values.ValuesProvider;

public class ValueRoot {
    private final ValuesProvider provider;
    private final String path;

    public ValueRoot(ValuesProvider provider, String path) {
        this.provider = provider;
        this.path = path;
    }

    public ValueRoot get(String child) {
        return new ValueRoot(provider, path == null ? child : path + "." + child);
    }

    @Override
    public String toString() {
        return provider.getValue(path).orElseThrow();
    }
}
