package net.sample.framework.message.transport.exception;


public class TransportCommandException extends TransportException {
    private static final long serialVersionUID = -3284112575953054986L;

    public TransportCommandException(String errorCode, String message) {
        super(errorCode, message);
    }

    public TransportCommandException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

}
