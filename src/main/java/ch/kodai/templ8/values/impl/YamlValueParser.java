package ch.kodai.templ8.values.impl;

import ch.kodai.templ8.values.ValuesProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

public class YamlValueParser implements ValuesProvider {
    JsonNode contents;

    public YamlValueParser(File fileToParse) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        // NOTE: This is still YAML, but we have to parse it as a JsonNode, in order to make it easier to work with.
        contents = mapper.readValue(fileToParse, JsonNode.class);
    }

    public YamlValueParser(String contents) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        // NOTE: This is still YAML, but we have to parse it as a JsonNode, in order to make it easier to work with.
        this.contents = mapper.readValue(contents, JsonNode.class);
    }

    @Override
    public @NotNull Optional<String> getValue(String key) {
        // Split by points, and return Optional.empty if not found.
        String[] targets = key.split("\\.");
        Optional<JsonNode> node = Optional.of(contents);
        for (String target: targets) {
            node = node.flatMap(getChildren(target));
        }

        return node.map(JsonNode::asText);
    }

    public Function<JsonNode, Optional<JsonNode>> getChildren(String name) {
        return elem -> {
            if (elem.has(name)) {
                return Optional.of(elem.get(name));
            } else {
                return Optional.empty();
            }
        };
    }
}
