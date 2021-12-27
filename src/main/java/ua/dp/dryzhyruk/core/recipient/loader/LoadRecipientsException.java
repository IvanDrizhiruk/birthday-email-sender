package ua.dp.dryzhyruk.core.recipient.loader;

public class LoadRecipientsException extends Exception {
    public LoadRecipientsException(String message, Exception e) {
        super(message, e);
    }
}
