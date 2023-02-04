package utilities;

public class ErrorHandlingUtil {
    String message;

    public ErrorHandlingUtil(String error) {
        message = error;
    }

    /**
     * If any error comes up, show on console and exit
     * @param message
     */
    public static void errorOccurred(String message) {
        System.out.println(message);
        System.exit(1);
    }
}
