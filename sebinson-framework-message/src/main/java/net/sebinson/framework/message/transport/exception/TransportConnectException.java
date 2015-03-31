package net.sebinson.framework.message.transport.exception;

public class TransportConnectException extends TransportException {
    private static final long serialVersionUID = 1731208775047676984L;

    public TransportConnectException(String errorCode, String add) {
        this(errorCode, add, null);
    }

    public TransportConnectException(String errorCode, String add, Throwable cause) {
        super(errorCode, "Connect to [" + add + "] failed.", cause);
    }
}
