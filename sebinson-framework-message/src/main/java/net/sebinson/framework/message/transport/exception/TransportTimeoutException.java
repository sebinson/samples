package net.sebinson.framework.message.transport.exception;

public class TransportTimeoutException extends TransportException {
    private static final long serialVersionUID = 6705486335992455241L;

    public TransportTimeoutException(String errorCode, String message) {
        super(errorCode, message);
    }

    public TransportTimeoutException(String errorCode, String add, long timeoutMills) {
        this(errorCode, add, timeoutMills, "", null);
    }

    public TransportTimeoutException(String errorCode, String add, long timeoutMills, String message, Throwable cause) {
        super(errorCode, "wait response to [" + add + "] timeout, [" + timeoutMills + "](ms), message is [" + message
                + "]", cause);
    }

}
