package ch.kodai.templ8.values;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * A ValuesParser is an interface that provides templated values for a give key (or function)
 */
public interface ValuesProvider {
    /**
     * Gets a value for a given key
     * @param key The key (for hierarchy, use . to advance)
     * @return The value (Not null! throw an exception instead)
     */
    @NotNull Optional<String> getValue(String key);
}
