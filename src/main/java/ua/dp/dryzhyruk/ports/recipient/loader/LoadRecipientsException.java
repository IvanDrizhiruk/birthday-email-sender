package ua.dp.dryzhyruk.ports.recipient.loader;

public class LoadRecipientsException extends Exception {
    public LoadRecipientsException(String message, Exception e) {
        super(message, e);
    }
}
