package net.sebinson.framework.message.transport.exception;

public class TransportSendRequestException extends TransportException {

    private static final long serialVersionUID = -7052073596054961937L;

    public TransportSendRequestException(String errorCode, String add) {
        this(errorCode, add, "", null);
    }

    public TransportSendRequestException(String errorCode, String add, String message, Throwable cause) {
        super(errorCode, "Send Request to [" + add + "] failed. message is [" + message + "]", cause);
    }
}
