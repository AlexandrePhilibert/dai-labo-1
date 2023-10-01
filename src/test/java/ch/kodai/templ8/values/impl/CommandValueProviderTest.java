package ch.kodai.templ8.values.impl;

import ch.kodai.templ8.values.exceptions.ValuesLoadException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CommandValueProviderTest {
    @Test
    void parseValue() throws ValuesLoadException {
        CommandValueProvider commandValueProvider = new CommandValueProvider();
        // Test with standard value
        commandValueProvider.parseValue("test_standard=standardValue");
        assertEquals(commandValueProvider.getValue("test_standard"), Optional.of("standardValue"));
        // Test with base64 encoded value
        commandValueProvider.parseValue("test_b64=WW91IHJlYWxseSB3ZW50IHRoaXMgZmFyPz8/Cg==");
        assertEquals(commandValueProvider.getValue("test_b64"), Optional.of("WW91IHJlYWxseSB3ZW50IHRoaXMgZmFyPz8/Cg=="));


        // Test invalid inputs
        assertThrows(ValuesLoadException.class, () -> commandValueProvider.parseValue("test_invalid_1"));
        assertThrows(ValuesLoadException.class, () -> commandValueProvider.parseValue("test_invalid_no_value="));
    }
}