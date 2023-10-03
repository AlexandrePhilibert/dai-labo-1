package ch.kodai.templ8.templating;

import ch.kodai.templ8.values.exceptions.ValuesLoadException;
import ch.kodai.templ8.values.impl.CommandValueProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class TemplaterTest {
    public static CommandValueProvider TEST_PROVIDER;

    public static CommandValueProvider getTestProvider() {
        if (TEST_PROVIDER == null) {
            try {
                TEST_PROVIDER = new CommandValueProvider();
                TEST_PROVIDER.parseValue("test=true");
                TEST_PROVIDER.parseValue("nested.test=pourquoi pas");
            } catch (ValuesLoadException e) {
                throw new RuntimeException(e);
            }
        }
        return TEST_PROVIDER;
    }

    @Test
    void testNoParam() throws IOException {
        String input = """
                version: 1
                test: true""";
        StringWriter writer = new StringWriter();
        new Templater(getTestProvider()).template(new StringReader(input), writer);

        assertEquals(input, writer.toString());
    }

    @Test
    void testSingleParam() throws IOException {
        String input = """
                version: 1
                test: {{ value.test }}""";
        StringWriter writer = new StringWriter();
        new Templater(getTestProvider()).template(new StringReader(input), writer);

        assertEquals("""
                version: 1
                test: true""", writer.toString());
    }

    @Test
    void testNestedPath() throws IOException {
        String input = """
                version: 1
                test: {{ value.nested.test }}""";
        StringWriter writer = new StringWriter();
        new Templater(getTestProvider()).template(new StringReader(input), writer);

        assertEquals("""
                version: 1
                test: pourquoi pas""", writer.toString());
    }
}