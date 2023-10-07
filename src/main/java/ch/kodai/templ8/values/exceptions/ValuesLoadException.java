package ch.kodai.templ8.values.exceptions;

public class ValuesLoadException extends RuntimeException {

    public ValuesLoadException(String message) {
        super("An error occurred while parsing the values file: " + message);
    }
}
