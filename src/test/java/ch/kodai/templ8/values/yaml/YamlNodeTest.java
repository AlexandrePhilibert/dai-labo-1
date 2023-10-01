package ch.kodai.templ8.values.yaml;

import ch.kodai.templ8.values.impl.YamlValueParser;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class YamlNodeTest {
    @Language("yaml")
    private static final String TEST_YAML = """
            test: value1
            nested:
              nested1: haha
              nested2: nerd
            booleans: true
            # This is a comment, that should not be parsed, but a fun fact: "NO" is a boolean in the 1.1 YAML Spec,
            # but not in the version 1.2.
            booleans_but_weird: NO # Serializes to Boolean(false)
            numbeeeers: 11111110
            numbers_as_octal: 07711 # Another fun one, this is octal, due to the 0 prefix... You have to thank the designers for that one >_>
            """;

    @Test
    void testYaml() throws IOException {
        // try to get simple values
        YamlValueParser yamlValueParser = new YamlValueParser(TEST_YAML);
        assertEquals(Optional.of("value1"), yamlValueParser.getValue("test"));
        assertEquals(Optional.of("true"), yamlValueParser.getValue("booleans"));

        assertEquals(Optional.of("haha"), yamlValueParser.getValue("nested.nested1"));
        assertEquals(Optional.of("nerd"), yamlValueParser.getValue("nested.nested2"));

        assertEquals(Optional.of("11111110"), yamlValueParser.getValue("numbeeeers"));

        assertEquals(Optional.empty(), yamlValueParser.getValue("this_value_does_not_exist"));
        assertEquals(Optional.empty(), yamlValueParser.getValue("this.nested.value_does_not_exists"));

        // Told you it was weird !
        assertEquals(Optional.of("false"), yamlValueParser.getValue("booleans_but_weird"));
        assertEquals(Optional.of("4041"), yamlValueParser.getValue("numbers_as_octal"));
        // Let's end with the weirdness for now :P
    }
}