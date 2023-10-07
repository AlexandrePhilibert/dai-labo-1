package ch.kodai.templ8.values.exceptions;

public class ValuesLoadException extends RuntimeException {

    private final String message;

    public ValuesLoadException(String message) {
        super("An error occurred while parsing the values file: " + message);
        this.message = message;
    }


    public String getInnerMessage() {
        return message;
    }
}
