package ua.dp.dryzhyruk.ports.email.sender;

public class SendMailException extends  RuntimeException{

    public SendMailException(Throwable cause) {
        super(cause);
    }
}
