package net.sebinson.framework.message.transport.exception;

public class TransportCommandProtocolException extends TransportCommandException {
    private static final long serialVersionUID = -9012243536922003082L;

    public TransportCommandProtocolException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public TransportCommandProtocolException(String errorCode, String message) {
        super(errorCode, message);
    }

}
