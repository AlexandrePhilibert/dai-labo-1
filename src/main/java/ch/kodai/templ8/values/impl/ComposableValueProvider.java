package ch.kodai.templ8.values.impl;

import ch.kodai.templ8.values.ValuesProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ComposableValueProvider implements ValuesProvider {
    private List<ValuesProvider> providers = new ArrayList<>();

    public void addProvider(ValuesProvider provider) {
        providers.add(provider);
    }
    @Override
    public @NotNull Optional<String> getValue(String key) {
        return providers.stream()
                .map(e -> e.getValue(key))
                .filter(Optional::isPresent)
                .findFirst().flatMap(Function.identity());
    }
}
