package ch.kodai.templ8.values.impl;

import ch.kodai.templ8.values.ValuesProvider;
import ch.kodai.templ8.values.exceptions.ValuesLoadException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandValueProvider implements ValuesProvider {
    private final Map<String, String> values = new HashMap<>();

    @Override
    public @NotNull Optional<String> getValue(String key) {
        return Optional.ofNullable(values.getOrDefault(key, null));
    }

    /**
     * Parses a value in the <code>[key]=[value]</code> format.
     * For compatibility, and to facilitate base64 inputs, the first = is used.
     * In the future we should support quote inputs, to allow for more customization.
     */
    public void parseValue(@NotNull String input) throws ValuesLoadException {
        int indexOfEquals = input.indexOf('=');
        if (indexOfEquals == -1 || indexOfEquals == input.length() - 1) {
            throw new ValuesLoadException("Input is invalid, it does not follow the [key]=[value] format.");
        }
        String[] splittedInput = input.split("=", 2);
        values.put(splittedInput[0], splittedInput[1]);
    }

}
