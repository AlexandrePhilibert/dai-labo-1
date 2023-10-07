package ch.kodai.templ8;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import picocli.CommandLine;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Utility class, as a nicer logback alternative
 */
public class Dialoguer {

    public static void showProgress(String message) {
        // This is meant for nerdfont terminals
        System.out.println(colorize("💠 ", Attribute.BLUE_TEXT(), Attribute.BOLD()) + colorize(message, Attribute.WHITE_TEXT()));
    }

    public static void showSuccess(String message) {
        System.out.println(colorize("✅ ", Attribute.BRIGHT_GREEN_TEXT(), Attribute.BOLD()) + colorize(message, Attribute.WHITE_TEXT()));
    }

    public static void showError(String error) {
        System.out.println(colorize("❌ ", Attribute.BRIGHT_RED_TEXT(), Attribute.BOLD()) + colorize(error, Attribute.RED_TEXT()));
    }

    public static void showInfo(String message) {
        System.out.println(colorize("⭕ ", Attribute.BRIGHT_BLUE_TEXT(), Attribute.BOLD()) + colorize(message, Attribute.WHITE_TEXT()));
    }
}
