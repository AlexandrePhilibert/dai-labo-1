package ch.kodai.templ8;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Lists the supported file encoding for reading template files and writing YAML files
 */
public enum SupportedCharset {
    ASCII,
    UTF8,
    UTF16;

    public Charset toStandardCharset() {
        return switch (this) {
            case UTF8 -> StandardCharsets.UTF_8;
            case ASCII -> StandardCharsets.US_ASCII;
            case UTF16 -> StandardCharsets.UTF_16;
        };
    }
}
