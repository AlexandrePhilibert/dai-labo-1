package ch.kodai.templ8.templating.extensions;

import ch.kodai.templ8.values.ValuesProvider;

/**
 * A utility class, used for translating pebble's access path to the one in use in the CLI.
 * We cannot use children directly, as the console implementation is based on a single map.
 * <p>
 * A future interesting way to do it would be to parse and merge YAML elements, but that is for the future™
 */
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
